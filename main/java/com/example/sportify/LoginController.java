package com.example.sportify;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class LoginController extends ButtonAction implements Initializable {

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    //Button
    @FXML
    private Button skipLogin;

    @FXML
    protected void submitActionLogin() throws IOException {
        String userValue = username.getText();      //get user entered username from the textField1
        String passValue = password.getText();      //get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (Submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Correct!");
        } else {
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter valid username and password or Signup");
        }
    }

    @FXML
    private void skipAction() throws Exception {
        CreateWindow.home(skipLogin);
    }
}

