package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.EditController;
import com.example.sportify.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class EditGraphicController implements GraphicController{

    private static final String FILL = "-fx-text-fill: #06B7C5;";
    private static final String BLACK = "-fx-text-fill: black;";

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
    protected Label modifyUsername;
    @FXML
    protected Label modifyPassword;
    @FXML
    protected Label modifyFirstName;
    @FXML
    protected Label modifyLastName;
    @FXML
    protected Label modifyEmail;
    @FXML
    protected Label gymNameLabel;
    @FXML
    protected Label addressLabel;
    @FXML
    protected Label telephoneLabel;
    @FXML
    protected Label modifyGymName;
    @FXML
    protected Label modifyAddress;
    @FXML
    protected Label modifyTelephone;

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
    protected CheckBox toggleFirstName;
    @FXML
    protected CheckBox toggleLastName;
    @FXML
    protected CheckBox toggleUsername;
    @FXML
    protected CheckBox togglePassword;
    @FXML
    protected CheckBox toggleEmail;
    @FXML
    protected CheckBox toggleGymName;
    @FXML
    protected CheckBox toggleAddress;
    @FXML
    protected CheckBox toggleTelephone;

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
        if (modify.getSource() == modifyUsername) {
            modifyLabelUsername();
        } else if (modify.getSource() == modifyEmail) {
            modifyLabelEmail();
        } else if (modify.getSource() == modifyFirstName) {
            modifyLabelFirstName();
        } else if (modify.getSource() == modifyPassword) {
            modifyLabelPassword();
        } else if (modify.getSource() == modifyLastName) {
            modifyLabelLastName();
        }
    }
    protected void modifyLabelUsername(){
        if (!toggleUsername.isSelected()) {
            modifyUsername.setStyle(FILL);
            toggleUsername.setSelected(true);
            controller.togglevisible(this.toggleUsername, this.usernameLabel, this.username);
        } else {
            modifyUsername.setStyle(BLACK);
            toggleUsername.setSelected(false);
            controller.togglevisible(this.toggleUsername, this.usernameLabel, this.username);
        }
    }

    protected void modifyLabelEmail(){
        if (!toggleEmail.isSelected()) {
            modifyEmail.setStyle(FILL);
            toggleEmail.setSelected(true);
            controller.togglevisible(this.toggleEmail, this.emailLabel, this.email);
        } else {
            modifyEmail.setStyle(BLACK);
            toggleEmail.setSelected(false);
            controller.togglevisible(this.toggleEmail, this.emailLabel, this.email);
        }
    }

    protected void modifyLabelFirstName(){
        if (!toggleFirstName.isSelected()) {
            modifyFirstName.setStyle(FILL);
            toggleFirstName.setSelected(true);
            controller.togglevisible(this.toggleFirstName, this.firstNameLabel, this.firstName);
        } else {
            modifyFirstName.setStyle(BLACK);
            toggleFirstName.setSelected(false);
            controller.togglevisible(this.toggleFirstName, this.firstNameLabel, this.firstName);
        }
    }

    protected void modifyLabelPassword(){
        if (!togglePassword.isSelected()) {
            modifyPassword.setStyle(FILL);
            togglePassword.setSelected(true);
            controller.togglevisible(this.togglePassword, this.passwordLabel, this.password);
        } else {
            modifyPassword.setStyle(BLACK);
            togglePassword.setSelected(false);
            controller.togglevisible(this.togglePassword, this.passwordLabel, this.password);
        }
    }

    protected void modifyLabelLastName(){
        if (!toggleLastName.isSelected()) {
            modifyLastName.setStyle(FILL);
            toggleLastName.setSelected(true);
            controller.togglevisible(this.toggleLastName, this.lastNameLabel, this.lastName);
        } else {
            modifyLastName.setStyle(BLACK);
            toggleLastName.setSelected(false);
            controller.togglevisible(this.toggleLastName, this.lastNameLabel, this.lastName);
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (EditController) controller;
    }
}
