package sportify;

import java.util.Map;

/**
 * The SignUp class interface
 */
public interface SignUp {

    /**
     * The method that all SignUp
     * classes must have.
     *
     * @param userAccount The account
     *                   to be registered
     *                    on the application
     */
    void userKind(Map<String, String> userAccount);
}
