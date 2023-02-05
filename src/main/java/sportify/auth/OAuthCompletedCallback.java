package sportify.auth;

/**
 * The `OAuthCompletedCallback` interface defines
 * the methods that must be implemented by a class
 * to handle the completion of an OAuth authentication process.
 */
public interface OAuthCompletedCallback {

    /**
     * This method is called when the OAuth authentication
     * process is completed.
     *
     * @param authenticator the OAuthAuthenticator instance
     *                      that performed the authentication process
     */
    void oAuthCallback(OAuthAuthenticator authenticator);
}
