package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UserController {

    // User
    private User user;

    // Reference to the main application.
    private MainApp mainApp;

    @FXML
    private Label username;

    @FXML
    private Pane pane;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public UserController() {
    }

    public void setUser(User user) {
        this.user = user;
        if (this.user!=null) {
            pane.setVisible(true);
            username.setText(user.getUserName());
        } else {
            pane.setVisible(false);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
