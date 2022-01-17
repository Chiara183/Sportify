package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

public class SignUpController extends AccessController {

    /** All the text field of the interface*/
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField pass_text;

    //Label
    @FXML
    private Label eye;

    /** All the checkbox of the interface*/
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private CheckBox gymTick;
    @FXML
    private CheckBox userTick;

    /** The constructor.*/
    public SignUpController() {
        this.type = ControllerType.SIGN_UP;
        this.submit = new Submit(null);
    }

    /** Controls the visibility of the password*/
    @FXML
    private void set_toggle_pass(){
        if(!this.pass_toggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #06B7C5;");
            pass_toggle.setSelected(true);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            pass_toggle.setSelected(false);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        }
    }

    /** Controls the sign-up method (user, gym)*/
    @FXML
    private void tickAction(ActionEvent event) {
        if (event.getSource() == gymTick && userTick.isSelected()) {
            userTick.setSelected(false);
        } else if (event.getSource() == userTick && gymTick.isSelected()) {
            gymTick.setSelected(false);
        } else if (event.getSource() == gymTick && !userTick.isSelected()) {
            userTick.setSelected(true);
        } else if (event.getSource() == userTick && !gymTick.isSelected()) {
            gymTick.setSelected(true);
        }
    }

    /** The action of the buttons*/
    @FXML
    protected void submitActionSignUp() {
        HashMap<String, String> userAccount = new HashMap<>();  //initialize list of string 'userAccount'
        String userValue = username.getText();                  //get user entered username
        String passValue = password.getText();                  //get user entered password
        String nameValue = firstName.getText();                 //get user entered first name
        String lastNameValue = lastName.getText();              //get user entered last name
        String email = this.email.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        userAccount.put("username", userValue);                 //put userValue in userAccount
        userAccount.put("password", passValue);                 //put user password in userAccount
        userAccount.put("firstName", nameValue);                //put user firstName in userAccount
        userAccount.put("lastName", lastNameValue);             //put user lastName in userAccount
        userAccount.put("email", email);                           //put user email in userAccount
        userAccount.put("birthday", date);                      //put user birthday in userAccount

        //check whether the credentials are authentic or not
        if (!email.equals("") && !userValue.equals("") && !passValue.equals("") && !submit.exist(userValue) && !submit.existEmail(email)) {    //if authentic, navigate user to a new page
            if (userTick.isSelected()) {
                userAccount.put("ruolo", "user");
                this.submit.signUp(userAccount);
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                login();
            } else if (gymTick.isSelected()) {
                userAccount.put("ruolo", "gym");
                this.submit.signUp(userAccount);
                signUpGymAction();
            }
        } else {
            if (submit.exist(userValue)){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("User already exists");
                alert.setHeaderText("The user already exists");
                alert.setContentText("Please enter a different username or login.");
                alert.showAndWait();
            } else if (submit.existEmail(email)){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("User already exists");
                alert.setHeaderText("The email is already registered");
                alert.setContentText("Please enter a different email or login.");
                alert.showAndWait();
            } else {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Empty field");
                alert.setHeaderText("Some obligatory value are empty");
                alert.setContentText("Please enter all obligatory value.");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void skipAction(){
        login();
    }

    /** It's called to load login overview*/
    private void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
    }

    /** It's called to load sign up gym overview*/
    private void signUpGymAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("DesktopView/SignUpGym.fxml"));
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGymController controller = loaderSignUp.getController();
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        //TODO
    }
}


