package com.example.sportify;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class SportQuizController implements Initializable {

    Button button;

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == button) {
            HighlightButton.lightColor(button);
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == button) {
            HighlightButton.darkColor(button);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
