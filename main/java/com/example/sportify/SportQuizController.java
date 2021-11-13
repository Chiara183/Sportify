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

    Stage stage;
    Parent root;

    Button button;

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == button) {
            button.setStyle("-fx-background-color:  #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == button) {
            button.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
