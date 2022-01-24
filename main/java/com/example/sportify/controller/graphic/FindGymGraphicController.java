package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.FindGymController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class FindGymGraphicController extends GraphicController {

    /** Reference to controller*/
    private FindGymController controller;

    /** Reference to the map pane*/
    @FXML
    private Pane Map;

    /** The action of back button*/
    @FXML
    private void skipAction() {
        this.controller.getMainApp().setUser(this.controller.getUser());
        this.controller.getMainApp().showHomeOverview();
    }

    /** Is called to get map pane*/
    public Pane getMap() {
        return this.Map;
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (FindGymController) controller;
    }
}