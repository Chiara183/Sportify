package com.example.sportify.controller;

import com.example.sportify.DateUtil;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserEditController {

    // Label
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label email;
    @FXML
    private Label birthday;

    // User
    private User user;

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        username.setText(user.getUserName());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        birthday.setText(DateUtil.format(user.getBirthday()));
    }
}
