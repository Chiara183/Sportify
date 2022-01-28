package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.SportController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SportGraphicController implements GraphicController{

    /** Reference to controller*/
    private SportController controller;

    /** All the label of the interface*/
    @FXML
    private Label sport;
    @FXML
    private Label sportDescription;
    @FXML
    private Label sportName;

    /** The action of the buttons*/
    @FXML
    private void home(){
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showHomeOverview();
    }

    @FXML
    private void getInfo(){
        controller.loadDescriptionFromDB(this.sportName.getText());
    }

    /** Is called to set sport name*/
    public void setSportName(String name){
        if(sportName != null) {
            sportName.setText(name);
        } else {
            sport.setText(name);
        }
    }

    /** Is called to set sport description*/
    public void setSportDescription(String description){
        sportDescription.setText(description);
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (SportController) controller;
    }
}
