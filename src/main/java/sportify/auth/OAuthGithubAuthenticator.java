package sportify.auth;

/**
 * OAuthGithubAuthenticator extends the OAuthAuthenticator
 * class to provide authentication for GitHub.
 * It includes the implementation of methods to retrieve
 * the web URL for authentication, the API token URL,
 * the API access URL, and the API access parameters.
 *
 * @see OAuthAuthenticator
 */
public class OAuthGithubAuthenticator extends OAuthAuthenticator {

    /**
     * The githubApiScope variable holds the
     * GitHub API scope to be used for authentication.
     */
    private final String githubScope;

    /**
     * Constructor to initialize the OAuthGithubAuthenticator with client ID, redirect URI, client secret, and scope.
     *
     * @param clientID the client ID provided by GitHub for the application.
     * @param redirectUri the redirect URI provided by GitHub for the application.
     * @param clientSecret the client secret provided by GitHub for the application.
     * @param scope the permissions requested for accessing GitHub.
     */
    public OAuthGithubAuthenticator(String clientID, String redirectUri, String clientSecret, String scope) {
        super(clientID, redirectUri, clientSecret);
        githubScope = scope;
    }

    /**
     * Method to get the web URL for authentication.
     *
     * @return the URL for authentication in GitHub.
     */
    @Override
    String getWebUrl() {
        return "https://github.com/login/oauth/authorize?client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&scope=" + githubScope;
    }

    /**
     * Method to get the API token URL.
     *
     * @return the URL for retrieving the token from GitHub.
     */
    @Override
    String getApiTokenUrl() {
        return "https://api.github.com/user?access_token=" + getAccessToken();
    }

    /**
     * Method to get the API access URL.
     *
     * @return the URL for accessing the GitHub API.
     */
    @Override
    String getApiAccessUrl() {
        return "https://github.com/login/oauth/access_token";
    }

    /**
     * Method to get the API access parameters.
     *
     * @return the parameters required for accessing the GitHub API.
     */
    @Override
    String getApiAccessParams() {
        return "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&grant_type=authorization_code&code=" + getAccessCode();
    }
}
