package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    //Button
    @FXML
    private Button login;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;


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
        CreateWindow.login(login) ;
    }

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == login) {
            HighlightButton.lightColor(login);
        } else if (event.getSource() == sportQuiz) {
            HighlightButton.lightColor(sportQuiz);
        } else if (event.getSource() == findGym) {
            HighlightButton.lightColor(findGym);
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == login) {
            HighlightButton.darkColor(login);
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
