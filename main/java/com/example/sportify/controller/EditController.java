package com.example.sportify.controller;

import com.example.sportify.user.User;
import com.example.sportify.user.gymUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class EditController {

    // Label
    @FXML
    protected Label usernameLabel;
    @FXML
    protected Label passwordLabel;
    @FXML
    protected Label firstNameLabel;
    @FXML
    protected Label lastNameLabel;
    @FXML
    protected Label emailLabel;
    @FXML
    protected Label birthday;
    @FXML
    protected Label modify_username;
    @FXML
    protected Label modify_password;
    @FXML
    protected Label modify_firstName;
    @FXML
    protected Label modify_lastName;
    @FXML
    protected Label modify_email;
    @FXML
    protected Label gymNameLabel;
    @FXML
    protected Label addressLabel;
    @FXML
    protected Label telephoneLabel;
    @FXML
    protected Label modify_gymName;
    @FXML
    protected Label modify_address;
    @FXML
    protected Label modify_telephone;

    // TextField
    @FXML
    protected TextField username;
    @FXML
    protected TextField password;
    @FXML
    protected TextField firstName;
    @FXML
    protected TextField lastName;
    @FXML
    protected TextField email;
    @FXML
    protected TextField gymName;
    @FXML
    protected TextField address;
    @FXML
    protected TextField telephone;

    // CheckBox
    @FXML
    protected CheckBox toggle_firstName;
    @FXML
    protected CheckBox toggle_lastName;
    @FXML
    protected CheckBox toggle_username;
    @FXML
    protected CheckBox toggle_password;
    @FXML
    protected CheckBox toggle_email;
    @FXML
    protected CheckBox toggle_gymName;
    @FXML
    protected CheckBox toggle_address;
    @FXML
    protected CheckBox toggle_telephone;

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
    abstract void setUser(User user);

    public abstract void setUser(gymUser user);

    @FXML
    abstract void okAction();

    @FXML
    protected void cancelAction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void set_toggle_pass(MouseEvent modify) {
        if (modify.getSource() == modify_username) {
            if (!toggle_username.isSelected()) {
                modify_username.setStyle("-fx-text-fill: #06B7C5;");
                toggle_username.setSelected(true);
                togglevisible(this.toggle_username, this.usernameLabel, this.username);
            } else {
                modify_username.setStyle("-fx-text-fill: black;");
                toggle_username.setSelected(false);
                togglevisible(this.toggle_username, this.usernameLabel, this.username);
            }
        } else if (modify.getSource() == modify_email) {
            if (!toggle_email.isSelected()) {
                modify_email.setStyle("-fx-text-fill: #06B7C5;");
                toggle_email.setSelected(true);
                togglevisible(this.toggle_email, this.emailLabel, this.email);
            } else {
                modify_email.setStyle("-fx-text-fill: black;");
                toggle_email.setSelected(false);
                togglevisible(this.toggle_email, this.emailLabel, this.email);
            }
        } else if (modify.getSource() == modify_firstName) {
            if (!toggle_firstName.isSelected()) {
                modify_firstName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_firstName.setSelected(true);
                togglevisible(this.toggle_firstName, this.firstNameLabel, this.firstName);
            } else {
                modify_firstName.setStyle("-fx-text-fill: black;");
                toggle_firstName.setSelected(false);
                togglevisible(this.toggle_firstName, this.firstNameLabel, this.firstName);
            }
        } else if (modify.getSource() == modify_password) {
            if (!toggle_password.isSelected()) {
                modify_password.setStyle("-fx-text-fill: #06B7C5;");
                toggle_password.setSelected(true);
                togglevisible(this.toggle_password, this.passwordLabel, this.password);
            } else {
                modify_password.setStyle("-fx-text-fill: black;");
                toggle_password.setSelected(false);
                togglevisible(this.toggle_password, this.passwordLabel, this.password);
            }
        } else if (modify.getSource() == modify_lastName) {
            if (!toggle_lastName.isSelected()) {
                modify_lastName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_lastName.setSelected(true);
                togglevisible(this.toggle_lastName, this.lastNameLabel, this.lastName);
            } else {
                modify_lastName.setStyle("-fx-text-fill: black;");
                toggle_lastName.setSelected(false);
                togglevisible(this.toggle_lastName, this.lastNameLabel, this.lastName);
            }
        }
    }

    /**
     * Controls the visibility of the Password field
     */
    protected void togglevisible(CheckBox pass_toggle, Label pass_text, TextField password) {
        if (!pass_toggle.isSelected()) {
            pass_text.setText(password.getText());
            pass_text.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(pass_text.getText());
        password.setVisible(true);
        pass_text.setVisible(false);
    }
}
