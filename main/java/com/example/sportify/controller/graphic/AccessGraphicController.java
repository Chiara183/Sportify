package com.example.sportify.controller.graphic;

import com.example.sportify.controller.AccessController;
import com.example.sportify.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

abstract class AccessGraphicController extends GraphicController{

    /** Reference to controller*/
    protected AccessController controller;

    /** Text field of the interface*/
    @FXML
    protected TextField pass_text;
    @FXML
    protected TextField password;

    //Label
    @FXML
    protected Label eye;

    /** All the checkbox of the interface*/
    @FXML
    protected CheckBox pass_toggle;

    /** Controls the visibility of the password*/
    @FXML
    protected void set_toggle_pass(){
        if(!this.pass_toggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #06B7C5;");
            pass_toggle.setSelected(true);
            controller.getMainApp().togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            pass_toggle.setSelected(false);
            controller.getMainApp().togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (AccessController) controller;
    }
}
