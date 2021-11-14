package com.example.sportify;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class LoginController extends HomeController implements Initializable {

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    //Button
    @FXML
    private Button signUp;
    @FXML
    private Button home;
    @FXML
    private Button submitLogin;
    @FXML
    private Button skipLogin;


    @FXML
    protected void submitActionLogin() throws IOException {
        HashMap<String, String[]> account;
        account = readWriteFile.readFile();

        String userValue = username.getText();                                //get user entered username from the textField1
        String passValue = password.getText();                                //get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (!account.isEmpty() && account.containsKey(userValue) && userValue.equals(account.get(userValue)[0]) && passValue.equals(account.get(userValue)[1])) {    //if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Correct!");
        } else {
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter valid username and password or Signup");
        }
    }

    @FXML
    private void signSignUpAction() throws Exception {
        CreateWindow.signSignUpAction(signUp);
    }

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.homeAction(home);
    }

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signUp) {
            HighlightButton.lightColor(signUp);
        } else if (event.getSource() == home) {
            HighlightButton.lightColor(home);
        } else if (event.getSource() == submitLogin) {
            HighlightButton.lightColor(submitLogin);
        } else if (event.getSource() == skipLogin) {
            HighlightButton.lightColor(skipLogin);
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signUp) {
            HighlightButton.darkColor(signUp);
        } else if (event.getSource() == home) {
            HighlightButton.darkColor(home);
        } else if (event.getSource() == submitLogin) {
            HighlightButton.darkColor(submitLogin);
        } else if (event.getSource() == skipLogin) {
            HighlightButton.darkColor(skipLogin);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

