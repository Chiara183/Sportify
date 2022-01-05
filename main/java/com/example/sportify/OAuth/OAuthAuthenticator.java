package com.example.sportify.OAuth;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    private Stage stage;


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

    public void start(MainApp mainApp, String name) {

        if(loginAttempted) {
            return;
        }
        loginAttempted = true;
        stage = new Stage();
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
                    closeStage();
                    accessCode = location.substring(location.indexOf("code=") + 5);
                    accessToken = doGetAccessTokenRequest();
                    String returnedJson = doGetAccountInfo();
                    assert returnedJson != null;
                    this.accessedJsonData = new JSONObject(returnedJson);
                    //System.out.println(returnedJson);
                    String username = accessedJsonData.getString("name");
                    String first_name = accessedJsonData.getString("given_name");
                    String last_name = accessedJsonData.getString("family_name");
                    accessedJsonData.getString("picture");
                    User user = new User();
                    user.setUserName(username);
                    user.setFirstName(first_name);
                    user.setLastName(last_name);
                    mainApp.setUser(user);
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "Correct!");
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

    public boolean hasFinishedSuccessfully() {
        return gotData;
    }

    public JSONObject getJsonData() {
        if(gotData) {
            return accessedJsonData;
        } else {
            return null;
        }
    }

    private void closeStage() {
        stage.close();
    }

    private String doGetAccountInfo() {
        try {
            HttpURLConnection connection2;
            URL url2 = new URL(getApiTokenUrl());
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection2.setDoInput(true);
            connection2.setDoOutput(true);

            //System.out.println("URL: " + getApiTokenUrl());

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

            //System.out.println("URL: " + getApiAccessUrl());
            //*System.out.println("PARAMS: " + urlParams);

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

            //System.out.println(responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) { // success
                System.err.println("Error getting access token for OAuth Login!");
            }
            StringBuilder response = getResponse(connection);
            String fullResponse = response.toString();

            JSONObject json = new JSONObject(fullResponse);

            //*System.out.println(fullResponse);

            //System.out.println("ACCESS TOKEN: " + accessToken);

            return json.getString("access_token");
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
