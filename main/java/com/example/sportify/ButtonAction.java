package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonAction implements Initializable {
    @FXML
    private void lightColor(MouseEvent event) {
        HighlightButton.lightColor((Button) event.getSource());
    }

    @FXML
    private void darkColor(MouseEvent event) {
        HighlightButton.darkColor((Button) event.getSource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
