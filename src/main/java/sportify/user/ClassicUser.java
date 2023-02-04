package sportify.user;

import javafx.beans.property.SimpleStringProperty;

/**
 * The class that represents
 * the classic user
 */
public class ClassicUser extends User {

    /**
     * The constructor.
     */
    public ClassicUser() {
        this(null, null);
    }

    /**
     * The constructor.
     *
     * @param userName the username of the user
     * @param password the password of the user
     */
    public ClassicUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("user");
       }
}
