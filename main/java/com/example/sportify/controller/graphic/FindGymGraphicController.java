package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.FindGymController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class FindGymGraphicController implements GraphicController {

    /** Reference to controller*/
    private FindGymController controller;

    /** Reference to the map pane*/
    @FXML
    private Pane map;

    /** The action of back button*/
    @FXML
    private void skipAction() {
        this.controller.getMainApp().setUser(this.controller.getUser());
        this.controller.getMainApp().showHomeOverview();
    }

    /** Is called to get map pane*/
    public Pane getMap() {
        return this.map;
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (FindGymController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
