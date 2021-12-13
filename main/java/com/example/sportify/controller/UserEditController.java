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
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label birthday;

    // TextField
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;

    // Button
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    // DatePicker
    @FXML
    private DatePicker date;

    // User
    private User user;

    // MenuController
    private MenuController menu;

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
    private void okAction() {
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setEmail(email.getText());
        user.setBirthday(date.getValue());
        menu.setUser(user);
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelAction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
