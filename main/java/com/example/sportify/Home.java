package com.example.sportify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Home extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        Pane home = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main/HomeGUI.fxml")));
        root.getChildren().add(home);

        //HomeScene
        Scene sceneHome = new Scene(root, 780, 437);

        //set stage
        stage.setTitle("HOME FORM");
        stage.setScene(sceneHome);
        stage.setResizable(false);
        stage.show();
    }
}
