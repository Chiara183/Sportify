package com.example.sportify.user;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public abstract class User {

    // StringProperty
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

    // Main-app
    protected MainApp mainApp;

    /**
     * Is called to give a reference back to main app.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public User() {
        this(null, null);
    }

    public User(String userName, String password) {
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

    public String getRole() {
        return this.role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `first_name` = '"
                        + firstName + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `last_name` = '"
                        + lastName + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        Submit submit = new Submit(this.mainApp);
        if(!submit.exist(userName)) {
            DAO obj_DAO = mainApp.getDAO();
            obj_DAO.updateDB(
                    "UPDATE `user` SET `username` = '"
                            + userName + "' WHERE `user`.`username` = '"
                            + this.userName.getValue() + "'");
            if (Objects.equals(this.role.get(), "gym")) {
                obj_DAO.updateDB(
                        "UPDATE `gym` SET `owner` = '"
                                + userName + "' WHERE `gym`.`owner` = '"
                                + this.userName.getValue() + "'");
            }
            this.userName.set(userName);
        } else if (this.userName.getValue() == null){
            assert false;
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

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        if (this.password.getValue() != null) {
            this.password.set(password);
            DAO obj_DAO = mainApp.getDAO();
            obj_DAO.updateDB(
                    "UPDATE `user` SET `password` = '"
                            + password + "' WHERE `user`.`username` = '"
                            + this.userName.getValue() + "'");
        } else {
            assert false;
            this.password.set(password);
        }
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `email` = '"
                        + email + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `birthday` = '"
                        + birthday.toString() + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public String getGymName() {
        return gymName.get();
    }

    protected abstract void setGymName(String name);

    public String getAddress() {
        return address.get();
    }

    protected abstract void setAddress(String address);

    protected abstract void setLatitude(String latitude);

    protected abstract void setLongitude(String longitude);

    public String getPhone() {
        return phone.get();
    }

    protected abstract void setPhone(String phone);
}
