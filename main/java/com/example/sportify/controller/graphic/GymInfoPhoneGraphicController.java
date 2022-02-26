package com.example.sportify.controller.graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;

public class GymInfoPhoneGraphicController extends GymInfoGraphicController {

    @Override
    @FXML
    protected void shareReview(){
        String gym;
        gym = this.controller.getMenu().getGym();
        StringBuilder reviews = new StringBuilder(this.reviewArea.getText(0, this.reviewArea.getLength()));
        this.controller.shareReview(gym, reviews);
    }

    @Override
    @FXML
    protected void courseAction() {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            helpMethod(loader);

            // Give the controller access to the main app.
            GymInfoPhoneGraphicController gymInfoGraphicController = loader.getController();
            this.controller.setGraphicController(gymInfoGraphicController);
            this.controller.setUser(this.controller.getMenu().getUser());
            gymInfoGraphicController.setController(this.controller);

            // Set info course
            controller.settingPhoneCourse();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    @FXML
    protected void reviewAction(String gymName) {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            helpMethod1(loader);
            // Give the controller access to the main app.
            GymInfoPhoneGraphicController gymInfoGraphicController = loader.getController();
            this.controller.setGraphicController(gymInfoGraphicController);
            this.controller.setUser(this.controller.getMenu().getUser());
            gymInfoGraphicController.setController(this.controller);

            // Set info course
            controller.settingPhoneReview(gymName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

}
