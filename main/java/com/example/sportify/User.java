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
    private final StringProperty gymName;
    private final StringProperty address;
    private final ObjectProperty<LocalDate> birthday;
    private final readWriteFile file = new readWriteFile();
    private final Submit submit = new Submit();

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

        // Some initial dummy data, just for convenient testing.
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(null);
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(1999, 2, 21));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
        if (!account.containsKey(this.getUserName())) {
            account = this.file.readFile("gym.dat");
        }
        account.get(this.getUserName()).put("firstName", firstName);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
        if (!account.containsKey(this.getUserName())) {
            account = this.file.readFile("gym.dat");
        }
        account.get(this.getUserName()).put("lastName", lastName);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
        if (!account.containsKey(this.getUserName())) {
            account = this.file.readFile("gym.dat");
        }
        account.get(this.getUserName()).put("email", email);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
        if (!account.containsKey(this.getUserName())) {
            account = this.file.readFile("gym.dat");
        }
        account.get(this.getUserName()).put("birthday", birthday.toString());
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public String getGymName() {
        return gymName.get();
    }

    public void setGymName(String name) {
        this.gymName.set(name);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("gym.dat");
        account.get(this.getUserName()).put("gymName", name);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
        HashMap<String, HashMap<String, String>> account = this.file.readFile("gym.dat");
        account.get(this.getUserName()).put("gymAddress", address);
        submit.signUp(this.getUserName(), account.get(this.getUserName()));
    }
}
