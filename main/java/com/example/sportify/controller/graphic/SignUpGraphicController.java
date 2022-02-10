package com.example.sportify.controller.graphic;

import com.example.sportify.MainApp;
import com.example.sportify.controller.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpGraphicController extends RegisterGraphicController{

    /** Reference to controller*/
    private SignUpController controller;

    /** All the button of the interface*/
    @FXML
    private Button gymUser;
    @FXML
    private Button normalUser;

    /** All the text field of the interface*/
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;

    /** All the checkbox of the interface*/
    @FXML
    private CheckBox gymTick;
    @FXML
    private CheckBox userTick;
    private boolean user = true;

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
        String userValue = username.getText();                  //get user entered username
        String passValue = password.getText();                  //get user entered password
        String nameValue = firstName.getText();                 //get user entered first name
        String lastNameValue = lastName.getText();              //get user entered last name
        String emailString = this.email.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        controller.submitActionSignUp(userValue, passValue, nameValue, lastNameValue, emailString, date);
    }
    public void submit(ControllerType type){
        if(type == ControllerType.SIGN_UP){
            submitActionSignUp();
        } else {
            user = false;
            submitActionSignUp();
        }
    }

    /** Is called to understand if it's a user*/
    public boolean isUser(){
        if(controller.getMainApp().isNotMobile()) {
            return userTick.isSelected();
        } else {
            return user;
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (SignUpController) controller;
        super.setController(controller);
    }

    public void setSignUp(MouseEvent event) {
        // Load sign up overview.
        FXMLLoader loaderSignUp = new FXMLLoader();
        Pane pane = null;
        Pane paneTopScreen = null;
        MenuGraphicController graphicMenuController = null;
        try {
            if (event.getSource() == gymUser) {
                loaderSignUp.setLocation(MainApp.class.getResource("SmartphoneView/SignUpGym0Phone.fxml"));
                FXMLLoader loaderTopScreen = new FXMLLoader();
                loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen2.fxml"));
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
                pane = loaderSignUp.load();
                this.controller.getMenu().setView(ControllerType.SIGN_UP_GYM);
            } else if (event.getSource() == normalUser) {
                loaderSignUp.setLocation(MainApp.class.getResource("SmartphoneView/SignUpPhone2.fxml"));
                FXMLLoader loaderTopScreen = new FXMLLoader();
                loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen2.fxml"));
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
                pane = loaderSignUp.load();
                this.controller.getMenu().setView(ControllerType.SIGN_UP);
            }

            // Set sign up overview into the center of root layout.
            SignUpGraphicController graphicController = loaderSignUp.getController();
            SignUpController controller = new SignUpController();
            controller.setMenu(this.controller.getMenu());
            this.controller.getMainApp().getPrimaryPane().setCenter(pane);
            this.controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
            this.controller.getMenu().setInstance(graphicController);
            assert graphicMenuController != null;
            graphicMenuController.setController(this.controller.getMenu());

            // Give the controller access to the main app.
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.controller.getMainApp());
        } catch(IOException e){
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
