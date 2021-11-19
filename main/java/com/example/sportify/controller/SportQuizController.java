package com.example.sportify.controller;

import com.example.sportify.ButtonAction;
import com.example.sportify.CreateWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SportQuizController extends ButtonAction implements Initializable {
    //Button
    @FXML
    private Button backQuiz;
    @FXML
    private Button next;
    @FXML
    private Button age1;
    @FXML
    private Button age2;
    @FXML
    private Button age3;
    @FXML
    private Button age4;

//TODO correct highlighting button ages

    @FXML
    private void backQuizAction() throws Exception {
        CreateWindow.home(backQuiz);
    }
}
