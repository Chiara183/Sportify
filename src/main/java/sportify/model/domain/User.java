package sportify.model.domain;

import sportify.model.dao.DAO;
import sportify.MainApp;
import sportify.model.dao.Submit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import sportify.errorlogic.DAOException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The abstract class that
 * represents the user
 */
public abstract class User {

    private static final String WHERECLAUSE = "' WHERE `user`.`username` = '";
    private static final String UPDATE_USER = "UPDATE `user` ";

    /* All parameter of user*/
    /**
     * Property representing the first name of the user.
     */
    protected final StringProperty firstName;

    /**
     * Property representing the last name of the user.
     */
    protected final StringProperty lastName;

    /**
     * Property representing the username of the user.
     */
    protected final StringProperty userName;

    /**
     * Property representing the password of the user.
     */
    protected final StringProperty password;

    /**
     * Property representing the email of the user.
     */
    protected final StringProperty email;

    /**
     * Property representing the birthday of the user.
     */
    protected final ObjectProperty<LocalDate> birthday;

    /**
     * Property representing the role of the user.
     */
    protected StringProperty role = new SimpleStringProperty(null);

    /**
     * Property representing the gym name of the user.
     */
    protected StringProperty gymName;

    /**
     * Property representing the address of the user.
     */
    protected StringProperty address;

    /**
     * Property representing the latitude of the user's location.
     */
    protected StringProperty latitude;

    /**
     * Property representing the longitude of the user's location.
     */
    protected StringProperty longitude;

    /**
     * Property representing the phone number of the user.
     */
    protected StringProperty phone;

    /**
     * A reference to the main
     * application class.
     */
    protected MainApp mainApp;

    /**
     * Sets the reference to the main application class.
     *
     * @param mainApp the main application class
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Constructs a new User instance.
     */
    protected User() {
        this(null, null);
    }

    /**
     * Constructs a new User instance
     * with the given username and password.
     *
     * @param userName the username of the user
     * @param password the password of the user
     */
    protected User(String userName, String password) {
        this.firstName = new SimpleStringProperty(null);
        this.lastName = new SimpleStringProperty(null);
        this.email = new SimpleStringProperty(null);

        // Some initial dummy data, just for convenient testing.
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        String[] dateValue = date.split("-");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(Integer.parseInt(dateValue[0]), Integer.parseInt(dateValue[1]), Integer.parseInt(dateValue[2])));
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return this.role.get();
    }

    /**
     * The get action of first name
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * The get action of last name
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * The get action of username
     *
     * @return the username of the user
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * The get action of password
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password.get();
    }

    /**
     * The get action of email
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * The get action of birthday
     *
     * @return the birthday of the user
     */
    public LocalDate getBirthday() {
        return birthday.get();
    }

    /**
     * The get action of gym name
     *
     * @return the gym name of the user
     */
    public String getGymName() {
        return gymName.get();
    }

    /**
     * The get action of address
     *
     * @return the address of the user
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * The get action of phone
     *
     * @return the phone of the user
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     *  The set action of role
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role.set(role);
    }

    /**
     * The set action of first name
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        String query = UPDATE_USER +
                "SET `first_name` = '"
                + firstName + WHERECLAUSE
                + getUserName() + "'";
        DAO objDAO = this.mainApp.getDAO();
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(User.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * The set action of last name
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        DAO objDAO = mainApp.getDAO();
        String query = UPDATE_USER +
                "SET `last_name` = '"
                + lastName + WHERECLAUSE
                + getUserName() + "'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(User.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * The set action of username
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        Submit submit = new Submit(this.mainApp);
        String query = UPDATE_USER +
                "SET `username` = '"
                + userName + WHERECLAUSE
                + getUserName() + "'";
        if(!submit.exist(userName)) {
            DAO objDAO = mainApp.getDAO();
            try {
                objDAO.updateAndGetDB(query);
            }
            catch (DAOException e){
                Logger logger = Logger.getLogger(User.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
            setUserName(userName);
        }
        else if (this.userName.getValue() == null){
            this.userName.set(userName);
        }
        else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("User already exists");
            alert.setHeaderText("The user already exists");
            alert.setContentText("Please enter a different username.");
            alert.showAndWait();
        }
    }

    /**
     * The set action of password
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        String query = UPDATE_USER +
                "SET `password` = '"
                + password + WHERECLAUSE
                + getUserName() + "'";
        if (getPassword() != null) {
            this.password.setValue(password);
            DAO objDAO = mainApp.getDAO();
            try {
                objDAO.updateAndGetDB(query);
            }
            catch (DAOException e){
                Logger logger = Logger.getLogger(User.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        else {
            this.password.setValue(password);
        }
    }

    /**
     * The set action of email
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email.set(email);
        DAO objDAO = mainApp.getDAO();
        String query = UPDATE_USER +
                "SET `email` = '"
                + email + WHERECLAUSE
                + getUserName() + "'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(User.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * The set action of birthday
     *
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        DAO objDAO = mainApp.getDAO();
        String query = UPDATE_USER +
                "SET `birthday` = '"
                + birthday.toString() + WHERECLAUSE
                + getUserName() + "'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(User.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
