package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.util.HashMap;

public class SignUpController extends ButtonAction implements Initializable {

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
    protected void sportQuizAction() throws Exception {
        CreateWindow.sportQuiz(sportQuiz);
    }

    @FXML
    protected void findGymAction() throws Exception {
        CreateWindow.findGym(findGym);
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
                    signUpGymAction();
                }
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    @FXML
    private void signUpGymAction() throws Exception {
        CreateWindow.signUpGym(submitSignUp);
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.login(signIn);
    }

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.home(home);
    }

    @FXML
    protected void skipAction() throws Exception {
        CreateWindow.login(skipSignUp);
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
}

