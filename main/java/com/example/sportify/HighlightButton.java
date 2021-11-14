package com.example.sportify;

import javafx.fxml.*;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.*;

public class HighlightButton implements Initializable {

    public static void lightColor(Button button) {
        button.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
    }

    public static void darkColor(Button button) {
        button.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
