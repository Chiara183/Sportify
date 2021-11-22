package com.example.sportify.controller;

import com.example.sportify.ButtonAction;
import com.example.sportify.CreateWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class SportQuizController extends ButtonAction implements Initializable {

    @FXML
    private Button backQuiz;
    @FXML
    private Button backQuizEnv;
    @FXML
    private Button nextQuiz;

    @FXML
    public void backQuizAction(ActionEvent event) throws Exception {
        Button b = (Button) event.getSource();
        if(b == backQuiz){
            CreateWindow.home(b);
        }
        else if(b == backQuizEnv){
            CreateWindow.sportQuiz(b);
        }
    }

    @FXML
    public void nextQuizAction(ActionEvent event) throws Exception {
        Button b = (Button) event.getSource();
        if(b == nextQuiz){
            CreateWindow.sportQuizEnv(b);
        }
    }
}
