package com.example.sportify.controller;

import com.example.sportify.ButtonAction;
import com.example.sportify.CreateWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SportQuizController extends ButtonAction implements Initializable {

    @FXML
    private Button backQuiz;
    @FXML
    private Button backQuizEnv;
    @FXML
    private Button nextQuiz;
    @FXML
    private Button backQuizType;
    @FXML
    private Button nextQuizEnv;
    @FXML
    private Button nextQuizType;
    @FXML
    private Button age1;
    @FXML
    private Button age2;
    @FXML
    private Button age3;
    @FXML
    private Button age4;
    @FXML
    private Button indoor;
    @FXML
    private Button outdoor;
    @FXML
    private Button group;
    @FXML
    private Button single;

    private boolean buttonAge1;
    private boolean buttonAge2;
    private boolean buttonAge3;
    private boolean buttonAge4;
    private boolean buttonIndoor;
    private boolean buttonOutdoor;
    private boolean buttonGroup;
    private boolean buttonSingle;




    @FXML
    public void backQuizAction(ActionEvent event) throws Exception {
        Button b = (Button) event.getSource();
        if(b == backQuiz){
            CreateWindow.home(b);
        }
        else if(b == backQuizEnv){
            CreateWindow.sportQuiz(b);
        }
        else if(b == backQuizType){
            CreateWindow.sportQuizEnv(b);
        }
    }

    @FXML
    public void nextQuizAction(ActionEvent event) throws Exception {
        Button b = (Button) event.getSource();
        if(b == nextQuiz){
            CreateWindow.sportQuizEnv(b);
        }
        else if(b == nextQuizEnv){
            CreateWindow.sportQuizType(b);
        }
    }

    @FXML
    public void takeQuiz(ActionEvent event) throws Exception {
        //TODO : takeQuiz on each button on fxml files
        //TODO : create interfaces for quiz answers
        //TODO : create function to go to answer's interfaces

        Button b = (Button) event.getSource();
        if (b == age1) {
             buttonAge1 = true;
        }
        if (b == age2) {
             buttonAge2 = true;
        }
        if (b == age3) {
             buttonAge3 = true;
        }
        if (b == age4) {
             buttonAge4 = true;
        }
        if (b == indoor) {
             buttonIndoor = true;
        }
        if (b == outdoor) {
             buttonOutdoor = true;
        }
        if (b == group) {
             buttonGroup = true;
        }
        if (b == single) {
             buttonSingle = true;
        }
    }
}
