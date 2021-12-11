package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {

    // User
    private User user;

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private Label username;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public UserController() {
    }

    public void setUser(User user) {
        this.user = user;
        username.setText(user.getUserName());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
