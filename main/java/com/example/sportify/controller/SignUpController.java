package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGraphicController;
import com.example.sportify.controller.graphic.SignUpGymGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class SignUpController extends AccessController {

    /** Reference to graphic controller*/
    private SignUpGraphicController graphicController;

    /** The constructor.*/
    public SignUpController() {
        this.type = ControllerType.SIGN_UP;
        this.submit = new Submit(null);
    }

    /** It's called to load sign up gym overview*/
    public void signUpGymAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("DesktopView/SignUpGym.fxml"));
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGymGraphicController graphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Is called to sign up*/
    public void submitActionSignUp(String userValue, String passValue, String nameValue, String lastNameValue, String email, String date) {
        HashMap<String, String> userAccount =
                mainApp.createAccount(userValue, passValue, nameValue, lastNameValue, email, date);
        if (!email.equals("") && !userValue.equals("") && !passValue.equals("") &&
                !submit.exist(userValue) &&
                !submit.existEmail(email)) {    //if authentic, navigate user to a new page
            if (graphicController.isUser()) {
                userAccount.put("ruolo", "user");
                submit.signUp(userAccount);
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                login();
            } else {
                userAccount.put("ruolo", "gym");
                submit.signUp(userAccount);
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

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGraphicController) graphicController;
    }
}


