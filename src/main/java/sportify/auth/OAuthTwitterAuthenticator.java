package sportify.auth;

/**
 * The OAuthTwitterAuthenticator class extends the OAuthAuthenticator
 * class and provides specific implementation
 * for authentication with Twitter.
 *
 * @see OAuthAuthenticator
 */
public class OAuthTwitterAuthenticator extends OAuthAuthenticator {

    /**
     * Constructs a new OAuthTwitterAuthenticator object with the
     * specified clientID, redirectUri, and clientSecret.
     *
     * @param clientID the client id provided by Twitter.
     * @param redirectUri the URI to redirect the user after authentication.
     * @param clientSecret the client secret provided by Twitter.
     */
    public OAuthTwitterAuthenticator(String clientID, String redirectUri, String clientSecret) {
        super(clientID, redirectUri, clientSecret);
    }

    /**
     * Returns the URL to the web page where the user can authenticate.
     *
     * @return the URL to the web page where the user can authenticate.
     */
    @Override
    String getWebUrl() {
        return "https://api.twitter.com/oauth/request_token";
    }

    /**
     * Returns the URL to retrieve the API token.
     *
     * @return the URL to retrieve the API token.
     */
    @Override
    String getApiTokenUrl() {
        return null;
    }

    /**
     * Returns the URL to retrieve the API access.
     *
     * @return the URL to retrieve the API access.
     */
    @Override
    String getApiAccessUrl() {
        return null;
    }

    /**
     * Returns the parameters to pass to the API to retrieve the access.
     *
     * @return the parameters to pass to the API to retrieve the access.
     */
    @Override
    String getApiAccessParams() {
        return null;
    }
}
