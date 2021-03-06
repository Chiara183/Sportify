package com.example.sportify.controller.graphic;

import com.example.sportify.auth.OAuthGoogleAuthenticator;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.HomeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class HomePhoneGraphicController implements GraphicController{

    /** All the button of the interface*/
    @FXML
    protected Button signIn;
    @FXML
    protected Button gymInfo;


    public ComboBox<String> comboActivity;

    /** Reference to controller*/
    protected HomeController controller;

    /** The action of comboBox*/
    @FXML
    protected void comboAction(){
        Object selectedItem = comboActivity.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        switch (choice) {
            case "Take sport quiz" -> this.controller.getMenu().getGraphicController().sportQuizAction();
            case "Login" -> this.controller.getMenu().getGraphicController().signAction();
            case "Login with Google" -> {
                String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
                String gRedir = "https://localhost:8080/oauth2";
                String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
                String gSecret = "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";
                OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
                auth.startLogin(controller.getMainApp());
            }
            case "Find gym" -> this.controller.getMenu().getGraphicController().findGymAction();
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (HomeController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }

    /** Is called to get signIn button*/
    public Button getSignIn(){return this.signIn;}

    /** Is called to get gymInfo button*/
    public Button getGymInfo(){return this.gymInfo;}

}
