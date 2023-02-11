package sportify.auth;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.JSONObject;
import sportify.MainApp;
import sportify.controller.ControllerType;
import sportify.model.dao.DAOAuthAuthenticator;
import sportify.model.dao.Submit;
import sportify.model.domain.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class for OAuth authentication.
 *
 * @see OAuthCompletedCallback
 */
public abstract class OAuthAuthenticator implements OAuthCompletedCallback{

    /**
     * String to store access token cache.
     */
    private String tokenCache;

    /**
     * JSON object to store accessed data.
     */
    private JSONObject accessedJsonData;

    /**
     * Flag to track whether login has been attempted.
     */
    private boolean loginAttempted = false;

    /**
     * String to store access token.
     */
    private String accessToken;

    /**
     * String to store access code.
     */
    private String accessCode;

    /**
     * Flag to track whether an attempt has been received.
     */
    private boolean attemptReceived = false;

    /**
     * Flag to track whether data has been received.
     */
    private boolean gotData = false;

    /**
     * String to store the client ID.
     */
    private final String clientId;

    /**
     * String to store the redirect URI.
     */
    private final String redirectUri;

    /**
     * String to store the client secret.
     */
    private final String clientSecret;

    /**
     * Constructor for OAuthAuthenticator.
     *
     * @param clientID the client ID.
     * @param redirectUri the redirect URI.
     * @param clientSecret the client secret.
     */
    protected OAuthAuthenticator (String clientID, String redirectUri, String clientSecret) {
        this.clientId = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    /**
     * setTokenCache: sets the token cache.
     *
     * @param token the value to be set
     */
    public void setTokenCache(String token) {
        this.tokenCache = token;
    }

    /**
     * Returns the value of the `gotData` field.
     *
     * @return The value of the `gotData` field.
     */
    private boolean getGotData(){
        return this.gotData;
    }

    /**
     * Returns the value of the `attemptReceived` field.
     *
     * @return The value of the `attemptReceived` field.
     */
    private boolean getAttemptReceived(){
        return attemptReceived;
    }

    /**
     * Returns the client ID.
     *
     * @return The client ID.
     */
    public String getClientID() {
        return clientId;
    }

    /**
     * Returns the client secret.
     *
     * @return The client secret.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Returns the redirect URI.
     *
     * @return The redirect URI.
     */
    public String getRedirectUri(){
        return redirectUri;
    }

    /**
     * Starts the OAuth authentication process.
     *
     * @param name the name of the authentication.
     * @param type the type of OAuth authentication.
     */
    public void start(String name, OAuthType type) {

        if(loginAttempted) {
            return;
        }
        loginAttempted = true;
        WebView root = new WebView();
        WebEngine engine = root.getEngine();

        engine.setOnStatusChanged(event ->
        {
            if (getGotData() ||
                    getAttemptReceived()) {
                return;
            }
            if (event.getSource() instanceof WebEngine we) {
                String location = we.getLocation();
                if (location.contains("code") &&
                        location.startsWith(getRedirectUri())) {
                    helpMethod(type, location);
                }
            }
        }
        );

        engine.load(getWebUrl());

        MainApp.showOAuthAuthenticator(root, name);
    }

    /**
     * Helper method for OAuth authentication.
     *
     * @param type the type of OAuth authentication.
     * @param location the location for the authentication.
     */
    public void helpMethod(OAuthType type, String location){
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
            googleAuth();
        }
        MainApp.showOverview(ControllerType.HOME);
        if(!getGotData()){
            this.gotData = true;
        }
    }

    /**
     * Method for Google OAuth authentication.
     */
    public void googleAuth(){
        String email = accessedJsonData.getString("email");
        String[] s = email.split("@");
        String username = s[0];
        accessedJsonData.getString("picture");
        User user;
        String rs = DAOAuthAuthenticator.getUsernameByEmail(email);
        if (!Submit.exist(rs)) {
            String firstName = accessedJsonData.getString("given_name");
            String lastName = accessedJsonData.getString("family_name");
            String password = Submit.generateStrongPassword(32);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String date = timestamp.toString();
            date = date.substring(0,10);
            Map<String, String> account = MainApp.createAccount(username, password, firstName, lastName, email, date);
            account.put("ruolo", "user");
            Submit.signUp(account);
        }
        user = Submit.setUser(rs);
        if(Objects.equals(user.getFirstName(), "")){
            String firstName = accessedJsonData.getString("given_name");
            user.setFirstName(firstName);
        }
        if(Objects.equals(user.getLastName(), "")){
            String lastName = accessedJsonData.getString("family_name");
            user.setLastName(lastName);
        }
        MainApp.setUser(user);
    }

    /**
     * getTokenCache: returns the token cache.
     *
     * @return the return value
     */
    public String getTokenCache() {
        return this.tokenCache;
    }

    /**
     * Abstract method to get the web URL.
     *
     * @return the web URL.
     */
    abstract String getWebUrl();

    /**
     * Abstract method to get the API token URL.
     *
     * @return the API token URL.
     */
    abstract String getApiTokenUrl();

    /**
     * Abstract method to get the API access URL.
     *
     * @return the API access URL.
     */
    abstract String getApiAccessUrl();

    /**
     * Abstract method to get the API access parameters.
     *
     * @return the API access parameters.
     */
    abstract String getApiAccessParams();

    /**
     * Method to get the access token.
     *
     * @return the access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Method to get the access code.
     *
     * @return the access code.
     */
    public String getAccessCode() {
        return accessCode;
    }

    /**
     * Private method to get the account information.
     *
     * @return the account information
     */
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
            }
            else {
                Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
                logger.log(Level.SEVERE, "Error retrieving api data!: {}", responseCode2);
            }
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, "####### ERROR GETTING ACCOUNT INFO ##############");
        }
        return null;
    }

    /**
     * Returns the result of the access token request.
     *
     * @return The result of the access token request.
     *
     * @throws JSONException If there is a problem with the JSON data.
     */
    private String doGetAccessTokenRequest() throws JSONException {
        String result;
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
            result = json.getString("access_token");
            return result;
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    /**
     * Returns the response from the connection.
     *
     * @param connection The HTTP URL connection.
     *
     * @return The response from the connection.
     */
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
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(OAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return response;
    }

    /**
     * Callback method that is called when the OAuth
     * authentication process is completed.
     *
     * @param authenticator The OAuth authenticator that
     *                      completed the authentication process.
     */
    @Override
    public void oAuthCallback(OAuthAuthenticator authenticator) {
        String tok = authenticator.getAccessToken();
        this.setTokenCache(tok);
    }
}

