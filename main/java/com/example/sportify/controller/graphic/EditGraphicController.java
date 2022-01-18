package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.EditController;
import com.example.sportify.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class EditGraphicController extends GraphicController{
    /** All the Label of interface */
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

    /** All the TextField of interface*/
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

    /** All the CheckBox of interface*/
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

    /** All the Button of the interface*/
    @FXML
    protected Button ok;
    @FXML
    protected Button cancel;

    // DatePicker
    @FXML
    protected DatePicker date;

    /** Is called to set user*/
    public void setUser(User user){
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        usernameLabel.setText(user.getUserName());
        passwordLabel.setText(user.getPassword());
        emailLabel.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        username.setText(user.getUserName());
        password.setText(user.getPassword());
        email.setText(user.getEmail());
        date.setValue(user.getBirthday());
    }

    /** The action of the button.*/
    @FXML
    abstract void okAction();
    @FXML
    protected void cancelAction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /** Reference to controller*/
    protected EditController controller;

    /** Controls the modifiability of all field*/
    @FXML
    protected void set_toggle_pass(MouseEvent modify) {
        if (modify.getSource() == modify_username) {
            if (!toggle_username.isSelected()) {
                modify_username.setStyle("-fx-text-fill: #06B7C5;");
                toggle_username.setSelected(true);
                controller.togglevisible(this.toggle_username, this.usernameLabel, this.username);
            } else {
                modify_username.setStyle("-fx-text-fill: black;");
                toggle_username.setSelected(false);
                controller.togglevisible(this.toggle_username, this.usernameLabel, this.username);
            }
        } else if (modify.getSource() == modify_email) {
            if (!toggle_email.isSelected()) {
                modify_email.setStyle("-fx-text-fill: #06B7C5;");
                toggle_email.setSelected(true);
                controller.togglevisible(this.toggle_email, this.emailLabel, this.email);
            } else {
                modify_email.setStyle("-fx-text-fill: black;");
                toggle_email.setSelected(false);
                controller.togglevisible(this.toggle_email, this.emailLabel, this.email);
            }
        } else if (modify.getSource() == modify_firstName) {
            if (!toggle_firstName.isSelected()) {
                modify_firstName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_firstName.setSelected(true);
                controller.togglevisible(this.toggle_firstName, this.firstNameLabel, this.firstName);
            } else {
                modify_firstName.setStyle("-fx-text-fill: black;");
                toggle_firstName.setSelected(false);
                controller.togglevisible(this.toggle_firstName, this.firstNameLabel, this.firstName);
            }
        } else if (modify.getSource() == modify_password) {
            if (!toggle_password.isSelected()) {
                modify_password.setStyle("-fx-text-fill: #06B7C5;");
                toggle_password.setSelected(true);
                controller.togglevisible(this.toggle_password, this.passwordLabel, this.password);
            } else {
                modify_password.setStyle("-fx-text-fill: black;");
                toggle_password.setSelected(false);
                controller.togglevisible(this.toggle_password, this.passwordLabel, this.password);
            }
        } else if (modify.getSource() == modify_lastName) {
            if (!toggle_lastName.isSelected()) {
                modify_lastName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_lastName.setSelected(true);
                controller.togglevisible(this.toggle_lastName, this.lastNameLabel, this.lastName);
            } else {
                modify_lastName.setStyle("-fx-text-fill: black;");
                toggle_lastName.setSelected(false);
                controller.togglevisible(this.toggle_lastName, this.lastNameLabel, this.lastName);
            }
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (EditController) controller;
    }
}
