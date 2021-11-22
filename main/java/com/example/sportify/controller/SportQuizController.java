package com.example.sportify.controller;

import com.example.sportify.ButtonAction;
import com.example.sportify.CreateWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;

public class SportQuizController extends ButtonAction implements Initializable {

    @FXML
    private Button backQuiz;
    @FXML
    private Button backQuizEnv;

    @FXML
    private void backQuizAction(MouseEvent event) throws Exception {
        Button b = (Button) event.getSource();
        if(b == backQuiz){
            CreateWindow.home(b);
        }
        else if(b == backQuizEnv){
            CreateWindow.sportQuiz(b);
        }
    }
}
