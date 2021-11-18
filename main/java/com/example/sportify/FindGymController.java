package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FindGymController extends ButtonAction implements Initializable{

    //Button
    @FXML
    private Button signInGym;
    @FXML
    private Button skipGymFinder;
    @FXML
    private Button home;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;
    @FXML
    private Button signUp;

    @FXML
    protected void sportQuizAction() throws Exception {
        CreateWindow.sportQuiz(sportQuiz);
    }

    @FXML
    protected void findGymAction() throws Exception {
        CreateWindow.findGym(findGym);
    }

    @FXML
    private void skipAction() throws Exception {
        CreateWindow.home(skipGymFinder);
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.login(signInGym);
    }

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.home(home);
    }

    @FXML
    protected void signUpAction() throws Exception {
        CreateWindow.signUp(signUp);
    }
}
