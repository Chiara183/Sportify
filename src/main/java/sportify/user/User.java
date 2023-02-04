package sportify.user;

import sportify.DAO;
import sportify.MainApp;
import sportify.Submit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.LocalDate;

public abstract class User {

    private static final String WHERECLAUSE = "' WHERE `user`.`username` = '";

    /* All parameter of user*/
    protected final StringProperty firstName;
    protected final StringProperty lastName;
    protected final StringProperty userName;
    protected final StringProperty password;
    protected final StringProperty email;
    protected final ObjectProperty<LocalDate> birthday;
    protected StringProperty role = new SimpleStringProperty(null);
    protected StringProperty gymName;
    protected StringProperty address;
    protected StringProperty latitude;
    protected StringProperty longitude;
    protected StringProperty phone;

    /** Reference to the MainApp*/
    protected MainApp mainApp;

    /** Is called to give a reference back to main app.*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /** The constructor.*/
    protected User() {
        this(null, null);
    }
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

    /** The get action*/
    public String getRole() {
        return this.role.get();
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getUserName() {
        return userName.get();
    }
    public String getPassword() {
        return password.get();
    }
    public String getEmail() {
        return email.get();
    }
    public LocalDate getBirthday() {
        return birthday.get();
    }
    public String getGymName() {
        return gymName.get();
    }
    public String getAddress() {
        return address.get();
    }
    public String getPhone() {
        return phone.get();
    }

    /** The set action*/
    public void setRole(String role) {
        this.role.set(role);
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        DAO objDAO = this.mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `user` SET `first_name` = '"
                        + firstName + WHERECLAUSE
                        + this.userName.getValue() +"'");
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `user` SET `last_name` = '"
                        + lastName + WHERECLAUSE
                        + this.userName.getValue() +"'");
    }
    public void setUserName(String userName) {
        Submit submit = new Submit(this.mainApp);
        if(!submit.exist(userName)) {
            DAO objDAO = mainApp.getDAO();
            objDAO.updateDB(
                    "UPDATE `user` SET `username` = '"
                            + userName + WHERECLAUSE
                            + this.userName.getValue() + "'");
            this.userName.set(userName);
        } else if (this.userName.getValue() == null){
            this.userName.set(userName);
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("User already exists");
            alert.setHeaderText("The user already exists");
            alert.setContentText("Please enter a different username.");
            alert.showAndWait();
        }
    }
    public void setPassword(String password) {
        if (this.password.getValue() != null) {
            this.password.set(password);
            DAO objDAO = mainApp.getDAO();
            objDAO.updateDB(
                    "UPDATE `user` SET `password` = '"
                            + password + WHERECLAUSE
                            + this.userName.getValue() + "'");
        } else {
            this.password.set(password);
        }
    }
    public void setEmail(String email) {
        this.email.set(email);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `user` SET `email` = '"
                        + email + WHERECLAUSE
                        + this.userName.getValue() +"'");
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `user` SET `birthday` = '"
                        + birthday.toString() + WHERECLAUSE
                        + this.userName.getValue() +"'");
    }
}
