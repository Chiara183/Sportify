package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonAction implements Initializable {

    //Button
    @FXML
    private Button home;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;
    @FXML
    private Button signIn;
    @FXML
    private Button signUp;

    //CheckBox
    /*@FXML
    private CheckBox Logged;*/

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.home(home);
    }

    @FXML
    protected void sportQuizAction() throws Exception {
        CreateWindow.sportQuiz(sportQuiz);
    }

    @FXML
    protected void findGymAction() throws Exception {
        CreateWindow.findGym(findGym);
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.login(signIn);
    }

    @FXML
    private void signUpAction() throws Exception {
        CreateWindow.signUp(signUp);
    }

    @FXML
    private void lightColor(MouseEvent event) {
        HighlightButton.lightColor((Button) event.getSource());
    }

    @FXML
    private void darkColor(MouseEvent event) {
        HighlightButton.darkColor((Button) event.getSource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
