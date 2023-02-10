package sportify.auth;

/**
 * OAuthFacebookAuthenticator is a class that extends
 * {@link OAuthAuthenticator} for authentication with Facebook
 * This class provides a way to authenticate with Facebook
 * through the OAuth protocol.
 * <p>
 * Not yet implemented
 *
 * @see OAuthAuthenticator
 */
public class OAuthFacebookAuthenticator extends OAuthAuthenticator{

    /**
     * The facebookApiScope variable holds the
     * Facebook API scope to be used for authentication.
     */
    private final String facebookFieldString;

    /**
     * Constructor that initializes basic information for authentication with Facebook.
     *
     * @param clientID client ID provided by Facebook
     * @param redirectUri URI of redirect specified by the application
     * @param clientSecret Secret client key provided by Facebook
     * @param apiFields String representing the API fields to be requested
     */
    public OAuthFacebookAuthenticator(String clientID, String redirectUri, String clientSecret, String apiFields) {
        super(clientID, redirectUri, clientSecret);
        facebookFieldString = apiFields;
    }

    /**
     * This method returns the URL of the authentication dialog with Facebook.
     *
     * @return URL of the authentication dialogue with Facebook
     */
    @Override
    String getWebUrl() {
        return "https://www.facebook.com/dialog/oauth?client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri();
    }

    /**
     * This method returns the API URL to retrieve user data with an access token.
     *
     * @return URL of the API to retrieve user data
     */
    @Override
    String getApiTokenUrl() {
        return "https://graph.facebook.com/me?fields=" + facebookFieldString + "&access_token=" + getAccessToken();
    }

    /**
     * This method returns the API URL to obtain an access token.
     *
     * @return URL of the API to obtain an access token
     */
    @Override
    String getApiAccessUrl() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    /**
     * This method returns the parameters for the access token request.
     *
     * @return Parameters for requesting the access token
     */
    @Override
    String getApiAccessParams() {
        return  "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&code=" + getAccessCode();
    }
}
