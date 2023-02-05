package sportify.bean;

/**
 * Bean class for handling login operations.
 */
public class LoginBean {

    /**
     * Checks if the input username string is empty.
     *
     * @param username The username string to be checked.
     *
     * @return True if the string is not empty, false otherwise.
     */
    public boolean userCheck(String username){
        return !username.equals("");
    }

    /**
     * Checks if the input password string is empty.
     *
     * @param password The password string to be checked.
     *
     * @return True if the string is not empty, false otherwise.
     */
    public boolean passCheck(String password){
        return !password.isEmpty();
    }
}
