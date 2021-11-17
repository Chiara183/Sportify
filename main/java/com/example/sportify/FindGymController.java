package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FindGymController implements Initializable {

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
    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signInGym) {
            HighlightButton.lightColor(signInGym);
        } else if (event.getSource() == home) {
            HighlightButton.lightColor(home);
        } else if (event.getSource() == sportQuiz) {
            HighlightButton.lightColor(sportQuiz);
        } else if (event.getSource() == findGym) {
            HighlightButton.lightColor(findGym);
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signInGym) {
            HighlightButton.darkColor(signInGym);
        } else if (event.getSource() == home) {
            HighlightButton.darkColor(home);
        } else if (event.getSource() == sportQuiz) {
            HighlightButton.darkColor(sportQuiz);
        } else if (event.getSource() == findGym) {
            HighlightButton.darkColor(findGym);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
