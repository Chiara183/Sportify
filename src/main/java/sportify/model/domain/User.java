package sportify.model.domain;

import javafx.scene.control.Alert;
import sportify.MainApp;
import sportify.model.dao.Submit;
import sportify.model.dao.UserDAO;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * The abstract class that
 * represents the user
 */
public abstract class User {
    /**
     * Representing the first name of the user.
     */
    protected String firstName;

    /**
     * Representing the last name of the user.
     */
    protected String lastName;

    /**
     * Representing the username of the user.
     */
    protected String userName;

    /**
     * Representing the password of the user.
     */
    protected String password;

    /**
     * Representing the email of the user.
     */
    protected String email;

    /**
     * Representing the birthday of the user.
     */
    protected LocalDate birthday;

    /**
     * Representing the role of the user.
     */
    protected String role;

    /**
     * Representing the gym name of the user.
     */
    protected String gymName;

    /**
     * Representing the address of the user.
     */
    protected String address;

    /**
     * Representing the latitude of the user's location.
     */
    protected String latitude;

    /**
     * Representing the longitude of the user's location.
     */
    protected String longitude;

    /**
     * Representing the phone number of the user.
     */
    protected String phone;

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
        // Some initial dummy data, just for convenient testing.
        this.userName = userName;
        this.password = password;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        String[] dateValue = date.split("-");
        this.birthday = LocalDate.of(Integer.parseInt(dateValue[0]), Integer.parseInt(dateValue[1]), Integer.parseInt(dateValue[2]));
    }

    /**
     * Update user in the DB.
     */
    protected void update(){
        UserDAO.updateUser(this);
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return this.role;
    }

    /**
     * The get action of first name
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The get action of last name
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The get action of username
     *
     * @return the username of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The get action of password
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * The get action of email
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * The get action of birthday
     *
     * @return the birthday of the user
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * The get action of gym name
     *
     * @return the gym name of the user
     */
    public String getGymName() {
        return gymName;
    }

    /**
     * The get action of address
     *
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * The get action of phone
     *
     * @return the phone of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the latitude of the gym.
     *
     * @return the latitude of the gym
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * Gets the longitude of the gym.
     *
     * @return the longitude of the gym
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     *  The set action of role
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * The set action of first name
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName =firstName;
        update();
    }

    /**
     * The set action of last name
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        update();
    }

    /**
     * The set action of username
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        if(!Submit.exist(userName)) {
            this.userName = userName;
            update();
        }
        else if (this.userName == null){
            this.userName = userName;
        }
        else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
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
        if (getPassword() != null) {
            this.password = password;
            UserDAO.updateUser(this);
        }
        else {
            this.password = password;
        }
    }

    /**
     * The set action of email
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        if(!Submit.existEmail(email)) {
            this.email = email;
            update();
        }
        else if (this.email == null){
            this.email = email;
        }
        else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Email already registered");
            alert.setHeaderText("The email already has register");
            alert.setContentText("Please enter a different email.");
            alert.showAndWait();
        }
    }

    /**
     * The set action of birthday
     *
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        update();
    }
}
