package com.example.sportify.controller.graphic;

import com.example.sportify.controller.AccessController;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

abstract class AccessGraphicController implements GraphicController{

    /** Reference to controller*/
    protected AccessController controller;

    /** Text field of the interface*/
    @FXML
    protected TextField passText;
    @FXML
    protected TextField password;

    //Label
    @FXML
    protected Label eye;

    /** All the checkbox of the interface*/
    @FXML
    protected CheckBox passToggle;

    /** Controls the visibility of the password*/
    @FXML
    protected void set_toggle_pass(){
        if(!this.passToggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #6c6b6b;");
            passToggle.setSelected(true);
            controller.getMainApp().togglevisiblePassword(this.passToggle, this.passText, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            passToggle.setSelected(false);
            controller.getMainApp().togglevisiblePassword(this.passToggle, this.passText, this.password);
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (AccessController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
