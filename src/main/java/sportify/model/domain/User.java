package sportify.model.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    protected StringProperty role;

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
        this.role = new SimpleStringProperty(null);
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
     * Gets the latitude of the gym.
     *
     * @return the latitude of the gym
     */
    public String getLatitude() {
        return this.latitude.get();
    }

    /**
     * Gets the longitude of the gym.
     *
     * @return the longitude of the gym
     */
    public String getLongitude() {
        return this.longitude.get();
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
        update();
    }

    /**
     * The set action of last name
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        update();
    }

    /**
     * The set action of username
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        if(!Submit.exist(userName)) {
            this.userName.set(userName);
            update();
        }
        else if (this.userName.getValue() == null){
            this.userName.set(userName);
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
            this.password.set(password);
            UserDAO.updateUser(this);
        }
        else {
            this.password.set(password);
        }
    }

    /**
     * The set action of email
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        if(!Submit.existEmail(email)) {
            this.email.set(email);
            update();
        }
        else if (this.email.getValue() == null){
            this.email.set(email);
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
        this.birthday.set(birthday);
        update();
    }
}
