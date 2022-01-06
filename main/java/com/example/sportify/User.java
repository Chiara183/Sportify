package com.example.sportify;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.sql.Timestamp;
import java.time.LocalDate;

public class User {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty email;
    private final StringProperty gymName;
    private final StringProperty address;
    private final StringProperty latitude;
    private final StringProperty longitude;
    private final StringProperty phone;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Default constructor.
     */
    public User() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     */
    public User(String userName, String password) {
        this.firstName = new SimpleStringProperty(null);
        this.lastName = new SimpleStringProperty(null);
        this.gymName = new SimpleStringProperty(null);
        this.address = new SimpleStringProperty(null);
        this.latitude = new SimpleStringProperty(null);
        this.longitude = new SimpleStringProperty(null);
        this.phone = new SimpleStringProperty(null);

        // Some initial dummy data, just for convenient testing.
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(null);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        String[] dateValue = date.split("-");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(Integer.parseInt(dateValue[0]), Integer.parseInt(dateValue[1]), Integer.parseInt(dateValue[2])));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        DAO obj_DAO = new DAO();
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
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `last_name` = '"
                        + lastName + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        Submit submit = new Submit();
        if(!submit.exist(userName)) {
            DAO obj_DAO = new DAO();
            obj_DAO.updateDB(
                    "UPDATE `user` SET `username` = '"
                            + userName + "' WHERE `user`.`username` = '"
                            + this.userName.getValue() + "'");
            if (this.gymName != null) {
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
            MainApp mainApp = new MainApp();
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
            DAO obj_DAO = new DAO();
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
        DAO obj_DAO = new DAO();
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
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `user` SET `birthday` = '"
                        + birthday.toString() + "' WHERE `user`.`username` = '"
                        + this.userName.getValue() +"'");
    }

    public String getGymName() {
        return gymName.get();
    }

    public void setGymName(String name) {
        this.gymName.set(name);
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `name` = '"
                        + name + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `address` = '"
                        + address + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    public String getLatitude() {
        return latitude.get();
    }

    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `latitude` = '"
                        + latitude + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    public String getLongitude() {
        return longitude.get();
    }

    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `longitude` = '"
                        + longitude + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
        DAO obj_DAO = new DAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `phone` = '"
                        + phone + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }
}
