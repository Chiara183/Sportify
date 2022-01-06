package com.example.sportify.OAuth;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.User;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;

public abstract class OAuthAuthenticator {

    private JSONObject accessedJsonData;

    private boolean gotData = false;
    private boolean attemptReceived = false;
    private boolean loginAttempted = false;

    private String accessToken;
    private String accessCode;

    private final String clientID;
    private final String redirectUri;
    private final String clientSecret;


    public OAuthAuthenticator (String clientID, String redirectUri, String clientSecret) {
        this.clientID = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
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
            if (gotData || attemptReceived) {
                return;
            }
            if (event.getSource() instanceof WebEngine we) {
                String location = we.getLocation();
                if (location.contains("code") && location.startsWith(getRedirectUri())) {
                    attemptReceived = true;
                    accessCode = location.substring(location.indexOf("code=") + 5);
                    accessToken = doGetAccessTokenRequest();
                    String returnedJson = doGetAccountInfo();
                    assert returnedJson != null;
                    accessedJsonData = new JSONObject(returnedJson);
                    System.out.println("Login Success!");
                    //System.out.println(returnedJson);
                    if(type == OAuthType.GOOGLE) {
                        String email = accessedJsonData.getString("email");
                        String[] s = email.split("@");
                        String username = s[0];
                        accessedJsonData.getString("picture");
                        Submit submit = new Submit();
                        User user;
                        DAO obj_DAO = new DAO();
                        ResultSet rs = obj_DAO.Check_Data(
                                "SELECT * " +
                                        "FROM user " +
                                        "WHERE user.email = \"" + email + "\"");
                        try {
                            if (rs.next()) {
                                username = rs.getString("username");
                            }
                        }catch (SQLException e){
                            System.out.println("SQLException: " + e.getMessage());
                        }
                        if (!submit.exist(username)) {
                            String first_name = accessedJsonData.getString("given_name");
                            String last_name = accessedJsonData.getString("family_name");
                            String password = submit.generateStrongPassword(32);
                            HashMap<String, String> account = new HashMap<>();
                            account.put("username", username);
                            account.put("password", password);
                            account.put("firstName", first_name);
                            account.put("lastName", last_name);
                            account.put("email", email);
                            account.put("ruolo", "user");
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            String date = timestamp.toString();
                            date = date.substring(0,10);
                            account.put("birthday", date);
                            submit.signUp(account);
                        }
                        user = submit.setUser(username);
                        if(Objects.equals(user.getFirstName(), "")){
                            String first_name = accessedJsonData.getString("given_name");
                            user.setFirstName(first_name);
                        }
                        if(Objects.equals(user.getLastName(), "")){
                            String last_name = accessedJsonData.getString("family_name");
                            user.setLastName(last_name);
                        }
                        mainApp.setUser(user);
                    }
                    /*else if (type == OAuthType.FACEBOOK){
                        //TODO
                    *}*/
                    mainApp.showHomeOverview();
                    this.gotData = true;
                }
            }
        });

        mainApp.showOAuthAuthenticator(root, name);
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
                return response2.toString();
            } else {
                System.out.println("Error retrieving api data!: " + responseCode2);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("####### ERROR GETTING ACCOUNT INFO ##############");
        }
        return null;
    }

    private String doGetAccessTokenRequest() {
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
                System.err.println("Error getting access token for OAuth Login!");
            }
            StringBuilder response = getResponse(connection);
            String fullResponse = response.toString();

            JSONObject json = null;
            try {
                json = new JSONObject(fullResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //System.out.println("ACCESS TOKEN: " + json.getString("access_token"));

            try {
                assert json != null;
                return json.getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return response;
    }
}
