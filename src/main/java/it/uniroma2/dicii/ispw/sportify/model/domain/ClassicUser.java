package it.uniroma2.dicii.ispw.sportify.model.domain;

/**
 * The class that represents
 * the classic user
 *
 * @see User
 */
public class ClassicUser extends User {

    /**
     * Constructs a new ClassicUser instance.
     */
    public ClassicUser() {
        this(null, null);
    }

    /**
     * Constructs a new ClassicUser instance
     * with the given username and password.
     *
     * @param userName the username of the user
     * @param password the password of the user
     */
    public ClassicUser(String userName, String password) {
        super(userName, password);
        role = "user";
       }
}
