package com.example.sportify;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.HashMap;

public class User {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> birthday;
    private readWriteFile file = new readWriteFile();
    private Submit submit = new Submit();

    /**
     * Default constructor.
     */
    public User() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param userName
     * @param password
     */
    public User(String userName, String password) {
        this.firstName = new SimpleStringProperty(null);
        this.lastName = new SimpleStringProperty(null);

        // Some initial dummy data, just for convenient testing.
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(null);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        account.get(this.getUserName()).put("firstName", firstName);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        account.get(this.getUserName()).put("lastName", lastName);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        account.get(this.getUserName()).put("email", email);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public StringProperty emailProperty() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        account.get(this.getUserName()).put("birthday", birthday.toString());
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
}
