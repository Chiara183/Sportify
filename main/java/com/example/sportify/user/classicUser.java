package com.example.sportify.user;

import javafx.beans.property.SimpleStringProperty;

public class classicUser extends User {

    /** The constructor.*/
    public classicUser() {
        this(null, null);
    }
    public classicUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("user");
       }
}
