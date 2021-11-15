package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    //Button
    @FXML
    private Button signIn;
    @FXML
    private Button home;
    @FXML
    private Button submitSignUp;
    @FXML
    private Button skipSignUp;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;

    //CheckBox
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;

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
    protected void submitActionSignUp(ActionEvent event) throws Exception {

        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUp)) {
            HashMap<String, String> userAccount = new HashMap<>();              //initialize list of string 'userAccount'
            String userValue = username.getText();                              //get user entered username
            String passValue = password.getText();                              //get user entered password
            String nameValue = firstName.getText();                             //get user entered first name
            String lastNameValue = lastName.getText();                          //get user entered last name
            userAccount.put("username", userValue);                             //put userValue in userAccount
            userAccount.put("password", passValue);                             //put user password in userAccount
            userAccount.put("firstName", nameValue);                            //put user firstName in userAccount
            userAccount.put("lastName", lastNameValue);                         //put user lastName in userAccount

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!userValue.equals("") && !passValue.equals("") && !nameValue.equals("") && !lastNameValue.equals("")) {    //if authentic, navigate user to a new page
                if (userTick.isSelected()) {
                    Submit.signUp(userValue, userAccount);
                    JOptionPane.showMessageDialog(jFrame, "You're registered!");
                    signLoginAction();
                } else if (gymTick.isSelected()) {
                    Submit.signUp("gymTick", userAccount);
                    signSignUpGymAction();
                }
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    @FXML
    private void signSignUpGymAction() throws Exception {
        CreateWindow.signSignUpGymAction(submitSignUp);
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.signLoginAction(signIn);
    }

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.homeAction(home);
    }
    @FXML
    protected void skipAction() throws Exception {
        CreateWindow.loginAction(skipSignUp);
    }
    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            HighlightButton.lightColor(signIn);
        } else if (event.getSource() == home) {
            HighlightButton.lightColor(home);
        } else if (event.getSource() == submitSignUp) {
            HighlightButton.lightColor(submitSignUp);
        } else if (event.getSource() == skipSignUp) {
            HighlightButton.lightColor(skipSignUp);
        } else if (event.getSource() == sportQuiz) {
            HighlightButton.lightColor(sportQuiz);
        } else if (event.getSource() == findGym) {
            HighlightButton.lightColor(findGym);
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            HighlightButton.darkColor(signIn);
        } else if (event.getSource() == home) {
            HighlightButton.darkColor(home);
        } else if (event.getSource() == submitSignUp) {
            HighlightButton.darkColor(submitSignUp);
        } else if (event.getSource() == skipSignUp) {
            HighlightButton.darkColor(skipSignUp);
        } else if (event.getSource() == sportQuiz) {
            HighlightButton.darkColor(sportQuiz);
        } else if (event.getSource() == findGym) {
            HighlightButton.darkColor(findGym);
        }
    }

    @FXML
    private void tickAction(javafx.event.ActionEvent event) {
        if (event.getSource() == gymTick && userTick.isSelected()) {
            userTick.setSelected(false);
        } else if (event.getSource() == userTick && gymTick.isSelected()) {
            gymTick.setSelected(false);
        } else if (event.getSource() == gymTick && !userTick.isSelected()) {
            userTick.setSelected(true);
        } else if (event.getSource() == userTick && !gymTick.isSelected()) {
            gymTick.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

