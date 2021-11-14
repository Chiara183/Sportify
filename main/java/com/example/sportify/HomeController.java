package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {

    //Button
    @FXML
    private Button login;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;


    @FXML
    protected void sportQuizAction() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You're in sport quiz form!");
    }

    @FXML
    protected void findGymAction() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You try to find a Gym!");
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.signLoginAction(login) ;
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
