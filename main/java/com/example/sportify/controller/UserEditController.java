package com.example.sportify.controller;

import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditController {

    // Label
    @FXML
    protected Label username;
    @FXML
    protected Label password;
    @FXML
    protected Label birthday;

    // TextField
    @FXML
    protected TextField firstName;
    @FXML
    protected TextField lastName;
    @FXML
    protected TextField email;

    // Button
    @FXML
    protected Button ok;
    @FXML
    protected Button cancel;

    // DatePicker
    @FXML
    protected DatePicker date;

    // User
    protected User user;

    // MenuController
    protected MenuController menu;

    /**
     * Sets the menu.
     */
    public void setMenuController(MenuController menu){
        this.menu = menu;
    }

    /**
     * Sets the user to be edited in the dialog.
     */
    public void setUser(User user) {
        this.user = user;

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        username.setText(user.getUserName());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        date.setValue(user.getBirthday());
    }

    @FXML
    protected void okAction() {
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setEmail(email.getText());
        user.setBirthday(date.getValue());
        menu.setUser(user);
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void cancelAction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
