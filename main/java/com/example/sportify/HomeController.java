package com.example.sportify;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {

    //Button
    @FXML
    private Button signIn;
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
        CreateWindow.signLoginAction(signIn);
    }

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == sportQuiz) {
            sportQuiz.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == findGym) {
            findGym.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == sportQuiz) {
            sportQuiz.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == findGym) {
            findGym.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
