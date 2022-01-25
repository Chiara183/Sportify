package com.example.sportify.user;

import javafx.beans.property.SimpleStringProperty;

public class ClassicUser extends User {

    /** The constructor.*/
    public ClassicUser() {
        this(null, null);
    }
    public ClassicUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("user");
       }
}
