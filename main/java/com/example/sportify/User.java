package com.example.sportify;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
        obj_DAO.Check_Data(
                "UPDATE `user` SET `first_name` = '"
                        + this.firstName + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `user` SET `last_name` = '"
                        + this.lastName + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `user` SET `username` = '"
                        + userName + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
        if (this.gymName != null){
            obj_DAO.Check_Data(
                    "UPDATE `gym` SET `owner` = '"
                            + userName + "' WHERE `gym`.`owner` = '"
                            + this.userName +"'");
        }
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `user` SET `password` = '"
                        + this.password + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `user` SET `email` = '"
                        + this.email + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `user` SET `birthday` = '"
                        + this.birthday + "' WHERE `user`.`username` = '"
                        + this.userName +"'");
    }

    public String getGymName() {
        return gymName.get();
    }

    public void setGymName(String name) {
        this.gymName.set(name);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `gym` SET `name` = '"
                        + this.gymName + "' WHERE `gym`.`owner` = '"
                        + this.userName +"'");
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `gym` SET `address` = '"
                        + this.address + "' WHERE `gym`.`owner` = '"
                        + this.userName +"'");
    }

    public String getLatitude() {
        return latitude.get();
    }

    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `gym` SET `latitude` = '"
                        + this.latitude + "' WHERE `gym`.`owner` = '"
                        + this.userName +"'");
    }

    public String getLongitude() {
        return longitude.get();
    }

    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `gym` SET `longitude` = '"
                        + this.longitude + "' WHERE `gym`.`owner` = '"
                        + this.userName +"'");
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
        DAO obj_DAO = new DAO();
        obj_DAO.Check_Data(
                "UPDATE `gym` SET `phone` = '"
                        + this.phone + "' WHERE `gym`.`owner` = '"
                        + this.userName +"'");
    }
}
