package com.example.sportify.controller;

import com.example.sportify.Adapter;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGraphicController;
import javafx.scene.control.Alert;

import java.util.Map;

public class SignUpController extends AccessController {

    /** Reference to graphic controller*/
    private SignUpGraphicController graphicController;
    private final Submit submit;

    /** The constructor.*/
    public SignUpController() {
        this.type = ControllerType.SIGN_UP;
        this.submit = new Submit(null);
    }


    /** Is called to sign up*/
    public void submitActionSignUp(String userValue, String passValue, String nameValue, String lastNameValue, String email, String date) {
        Map<String, String> userAccount =
                mainApp.createAccount(userValue, passValue, nameValue, lastNameValue, email, date);
        if (!email.equals("") && !userValue.equals("") && !passValue.equals("") &&
                !submit.exist(userValue) &&
                !submit.existEmail(email)) { //if authentic, navigate user to a new page
              Adapter adapter = new Adapter(this, this.graphicController, this.submit);
              adapter.userKind(userAccount);
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

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGraphicController) graphicController;
    }
}


