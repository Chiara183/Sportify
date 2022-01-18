package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.SignUpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.Timestamp;

public class SignUpGraphicController extends RegisterGraphicController{

    /** Reference to controller*/
    private SignUpController controller;

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
        String email = this.email.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        controller.submitActionSignUp(userValue, passValue, nameValue, lastNameValue, email, date);
    }

    /** Is called to understand if it's a user*/
    public boolean isUser(){
        return userTick.isSelected();
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (SignUpController) controller;
        super.setController(controller);
    }
}
