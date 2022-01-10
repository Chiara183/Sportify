package com.example.sportify.user;

import javafx.beans.property.SimpleStringProperty;

public class classicUser extends User {
    /**
     * Default constructor.
     */
    public classicUser() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     */
    public classicUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("user");
       }

    @Override
    protected void setGymName(String name) {
        //TODO
    }

    @Override
    protected void setAddress(String address) {
        //TODO
    }

    @Override
    protected void setLatitude(String latitude) {
        //TODO
    }

    @Override
    protected void setLongitude(String longitude) {
        //TODO
    }

    @Override
    protected void setPhone(String phone) {
        //TODO
    }
}
