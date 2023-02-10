package sportify.auth;

/**
 * The OAuthGoogleAuthenticator class extends the OAuthAuthenticator class and provides
 * functionality specific to Google's OAuth 2.0 authentication.
 * <p>
 * This class provides the necessary URLs, access parameters, and tokens for authentication
 * with Google.
 *
 * @see OAuthAuthenticator
 */
public class OAuthGoogleAuthenticator extends OAuthAuthenticator{

    /**
     * The googleApiScope variable holds the
     * Google API scope to be used for authentication.
     */
    private final String GOOGLE_API_SCOPE;

    /**
     * The constructor for OAuthGoogleAuthenticator takes the following parameters:
     *
     * @param clientID       the client ID for the application
     * @param redirectUri    the redirect URI for the application
     * @param clientSecret   the client secret for the application
     * @param apiScope       the Google API scope to be used for authentication
     * <p>
     * The constructor initializes the super class with the client ID, redirect URI, and client secret.
     * The googleApiScope is also initialized with the provided apiScope.
     */
    public OAuthGoogleAuthenticator(String clientID, String redirectUri, String clientSecret, String apiScope) {
        super(clientID, redirectUri, clientSecret);
        GOOGLE_API_SCOPE = apiScope;
    }

    /**
     * The getWebUrl method returns the URL to be used for the user to grant permission to the application.
     * <p>
     * The URL is constructed using the googleApiScope, redirectUri, clientID, and other parameters required by Google.
     *
     * @return the URL to be used for the user to grant permission to the application
     */
    @Override
    String getWebUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?scope=" + GOOGLE_API_SCOPE + "&access_type=offline&redirect_uri=" + getRedirectUri() + "&response_type=code&client_id=" + getClientID();
    }

    /**
     * The getApiTokenUrl method returns the URL to be used to request the user's information.
     * <p>
     * The URL is constructed using the access token obtained during authentication.
     *
     * @return the URL to be used to request the user's information
     */
    @Override
    String getApiTokenUrl() {
        return "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + getAccessToken();
    }

    /**
     * The getApiAccessUrl method returns the URL to be used to request an access token from Google.
     *
     * @return the URL to be used to request an access token from Google
     */
    @Override
    String getApiAccessUrl() {
        return "https://www.googleapis.com/oauth2/v4/token";
    }

    /**
     * The getApiAccessParams method returns the parameters to be used when requesting an access token from Google.
     * <p>
     * The parameters are constructed using the client ID, redirect URI, client secret, and access code obtained during authentication.
     *
     * @return the parameters to be used when requesting an access token from Google
     */
    @Override
    String getApiAccessParams() {
        return "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&grant_type=authorization_code&code=" + getAccessCode();
    }

    /**
     * Starts the Google login process.
     *
     */
    public void startLogin(){
        super.start("Google Login", OAuthType.GOOGLE);
    }
}
