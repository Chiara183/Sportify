package com.example.sportify.controller.graphic.phone;

import com.example.sportify.controller.graphic.SignUpGraphicController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;

public class SignUpPhoneGraphicController extends SignUpGraphicController {

    protected MenuPhoneGraphicController graphicMenuController;

    @Override
    public boolean isUser(){
        return super.user;
    }

    @Override
    public void setSignUp(Event event) {
        // Load sign up overview.
        FXMLLoader loaderSignUp = new FXMLLoader();
        helpMethod(event, loaderSignUp);
        // Set sign up overview into the center of root layout.
        SignUpPhoneGraphicController graphicController = loaderSignUp.getController();
        helpMethod3(graphicController);
    }
}
