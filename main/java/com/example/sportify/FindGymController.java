package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FindGymController extends ButtonAction implements Initializable{

    //Button
    @FXML
    private Button skipGymFinder;

    @FXML
    private void skipAction() throws Exception {
        CreateWindow.home(skipGymFinder);
    }
}
