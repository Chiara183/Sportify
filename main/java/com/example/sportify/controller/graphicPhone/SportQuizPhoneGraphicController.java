package com.example.sportify.controller.graphicPhone;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.SportQuizController;
import com.example.sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SportQuizPhoneGraphicController implements GraphicController {

    /** Reference to controller*/
    private SportQuizController controller;

    @FXML
    private TextField age;
    @FXML
    private Button okButton;

    @Override
    public void setController(Controller controller) {
        this.controller = (SportQuizController) controller;
    }
}
