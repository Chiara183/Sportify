package com.example.sportify.controller;

import com.example.sportify.CreateWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class FindGymController implements Initializable{

    //Button
    @FXML
    private Button skipGymFinder;

    @FXML
    private void skipAction() throws Exception {
        CreateWindow.home(skipGymFinder);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
