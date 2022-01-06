package com.example.sportify.OAuth;

import com.example.sportify.MainApp;

public class OAuthFacebookAuthenticator extends OAuthAuthenticator{

    private final String FACEBOOK_fieldsString;

    public OAuthFacebookAuthenticator(String clientID, String redirectUri, String clientSecret, String apiFields) {
        super(clientID, redirectUri, clientSecret);
        FACEBOOK_fieldsString = apiFields;
    }

    @Override
    String getWebUrl() {
        return "https://www.facebook.com/dialog/oauth?client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri();
    }

    @Override
    String getApiTokenUrl() {
        return "https://graph.facebook.com/me?fields=" + FACEBOOK_fieldsString + "&access_token=" + getAccessToken();
    }

    @Override
    String getApiAccessUrl() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    @Override
    String getApiAccessParams() {
        return  "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&code=" + getAccessCode();
    }

    public void startLogin(MainApp mainApp){
        super.start(mainApp, "Facebook Login", OAuthType.FACEBOOK);
    }
}