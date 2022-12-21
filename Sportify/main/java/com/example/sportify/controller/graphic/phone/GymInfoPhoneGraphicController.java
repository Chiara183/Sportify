package com.example.sportify.controller.graphic.phone;

import com.example.sportify.controller.graphic.GymInfoGraphicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.logging.Level;

public class GymInfoPhoneGraphicController extends GymInfoGraphicController {

    @Override
    @FXML
    protected void shareReview(){
        String gym;
        gym = this.controller.getMenu().getGym();
        if(!this.bean.checkPhoneReview(gym, this.reviewArea)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill gym name field and review field");
            alert.showAndWait();
        }
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
