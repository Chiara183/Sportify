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
        if(this.sportName.getText().equals("Volleyball") ){
            controller.loadDescriptionFromDB("Volleyball");
        }
        if(this.sportName.getText().equals("Trekking")){
            controller.loadDescriptionFromDB("Trekking");
        }
        if(this.sportName.getText().equals("Tennis")){
            controller.loadDescriptionFromDB("Tennis");
        }
        if(this.sportName.getText().equals("Swimming")){
            controller.loadDescriptionFromDB("Swimming");
        }
        if(this.sportName.getText().equals("Golf")){
            controller.loadDescriptionFromDB("Golf");
        }
        if(this.sportName.getText().equals("Football")){
            controller.loadDescriptionFromDB("Football");
        }
        if(this.sportName.getText().equals("Dance")){
            controller.loadDescriptionFromDB("Dance");
        }
        if(this.sportName.getText().equals("Athletics")){
            controller.loadDescriptionFromDB("Athletics");
        }
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
