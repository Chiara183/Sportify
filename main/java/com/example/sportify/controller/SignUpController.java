package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SignUpController extends AccessController {

    /** Reference to graphic controller*/
    private SignUpGraphicController graphicController;

    /** The constructor.*/
    public SignUpController() {
        this.type = ControllerType.SIGN_UP;
        this.submit = new Submit(null);
    }

    /** It's called to load login overview*/
    public void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
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
            SignUpGymController controller = loaderSignUp.getController();
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGraphicController) graphicController;
    }
}


