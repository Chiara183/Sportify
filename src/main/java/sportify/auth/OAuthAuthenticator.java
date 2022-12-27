package sportify.auth;

import sportify.DAO;
import sportify.MainApp;
import sportify.Submit;
import sportify.user.User;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OAuthAuthenticator {

    private JSONObject accessedJsonData;

    private boolean loginAttempted = false;

    private String accessToken;
    private String accessCode;
    private boolean attemptReceived = false;
    private boolean gotData = false;
    private final String clientID;
    private final String redirectUri;
    private final String clientSecret;


    protected OAuthAuthenticator (String clientID, String redirectUri, String clientSecret) {
        this.clientID = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    private boolean getGotData(){
        return this.gotData;
    }

    private boolean getAttemptReceived(){
        return attemptReceived;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri(){
        return redirectUri;
    }

    public void start(MainApp mainApp, String name, OAuthType type) {

        if(loginAttempted) {
            return;
        }
        loginAttempted = true;
        WebView root = new WebView();
        WebEngine engine = root.getEngine();

        engine.load(getWebUrl());

        engine.setOnStatusChanged(event -> {
            if (getGotData() || getAttemptReceived()) {
                return;
            }
            if (event.getSource() instanceof WebEngine we) {
                String location = we.getLocation();
                if (location.contains("code") && location.startsWith(getRedirectUri())) {
                    helpMethod(mainApp, type, location);
                }
            }
        });

        mainApp.showOAuthAuthenticator(root, name);
    }

    public void helpMethod(MainApp mainApp, OAuthType type, String location){
        if(!getAttemptReceived()){
        attemptReceived = true;
        }
        accessCode = location.substring(location.indexOf("code=") + 5);
        accessToken = doGetAccessTokenRequest();
        String returnedJson = doGetAccountInfo();
        assert returnedJson != null;
        accessedJsonData = new JSONObject(returnedJson);
        Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
        logger.log(Level.INFO, "Login Success!");
        if(type == OAuthType.GOOGLE) {
            googleAuth(mainApp);
        }
        mainApp.showHomeOverview();
        if(!getGotData()){
            this.gotData = true;
        }
    }

    public void googleAuth(MainApp mainApp){
        String email = accessedJsonData.getString("email");
        String[] s = email.split("@");
        String username = s[0];
        accessedJsonData.getString("picture");
        Submit submit = new Submit(mainApp);
        User user;
        DAO objDAO = mainApp.getDAO();
        List<String> list = objDAO.checkData(
                "SELECT * " +
                        "FROM user " +
                        "WHERE user.email = \"" + email + "\"", "username");
        String rs = list.get(list.size() - 1);
        if (!submit.exist(rs)) {
            String firstName = accessedJsonData.getString("given_name");
            String lastName = accessedJsonData.getString("family_name");
            String password = submit.generateStrongPassword(32);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String date = timestamp.toString();
            date = date.substring(0,10);
            Map<String, String> account = mainApp.createAccount(username, password, firstName, lastName, email, date);
            account.put("ruolo", "user");
            submit.signUp(account);
        }
        user = submit.setUser(rs);
        if(Objects.equals(user.getFirstName(), "")){
            String firstName = accessedJsonData.getString("given_name");
            user.setFirstName(firstName);
        }
        if(Objects.equals(user.getLastName(), "")){
            String lastName = accessedJsonData.getString("family_name");
            user.setLastName(lastName);
        }
        mainApp.setUser(user);
    }
    abstract String getWebUrl();

    abstract String getApiTokenUrl();

    abstract String getApiAccessUrl();

    abstract String getApiAccessParams();

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    private String doGetAccountInfo() {
        try {
            HttpURLConnection connection2;
            URL url2 = new URL(getApiTokenUrl());
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection2.setDoInput(true);
            connection2.setDoOutput(true);

            int responseCode2 = connection2.getResponseCode();

            if (responseCode2 == HttpURLConnection.HTTP_OK) { // success
                StringBuilder response2 = getResponse(connection2);
                if(response2 != null) {
                    return response2.toString();
                }
            } else {
                Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
                logger.log(Level.SEVERE, "Error retrieving api data!: {}", responseCode2);
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, "####### ERROR GETTING ACCOUNT INFO ##############");
        }
        return null;
    }

    private String doGetAccessTokenRequest() throws JSONException {
        try {
            URL url = new URL(getApiAccessUrl());
            String urlParams = getApiAccessParams();

            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + postDataLength);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Referer", "https://accounts.google.com/o/oauth2/token");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);

            wr.flush();

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) { // success
                Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
                logger.log(Level.SEVERE,"Error getting access token for OAuth Login!");
            }
            StringBuilder response = getResponse(connection);
            String fullResponse = "";
            if(response != null) {
                fullResponse = response.toString();
            }

            JSONObject json = new JSONObject(fullResponse);
            return json.getString("access_token");
        } catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    private StringBuilder getResponse(HttpURLConnection connection) {
        StringBuilder response = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return response;
    }
}

