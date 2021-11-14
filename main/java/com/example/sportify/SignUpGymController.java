package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SignUpGymController extends HomeController implements Initializable {

    //TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    //Button
    @FXML
    private Button signInGym;
    @FXML
    private Button home;
    @FXML
    private Button submitSignUpGym;
    @FXML
    private Button skipSignUpGym;
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
    protected void submitActionSignUpGym(ActionEvent event) throws Exception {
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUpGym)) {
            HashMap<String, String[]> account = new HashMap<>();
            String[] userGymAccount = new String[3];                                //initialize list of string 'userGymAccount'
            String gymValue = gymName.getText();                                    //get user entered gym name
            String addressValue = gymAddress.getText();                             //get user entered gym address
            String cityValue = gymCity.getText();                                   //get user entered gym city
            userGymAccount[0] = gymValue;                                           //put userValue in userAccount
            userGymAccount[1] = addressValue;                                       //put user password in userAccount
            userGymAccount[2] = cityValue;                                          //put user firstName in userAccount
            account.put(gymValue, userGymAccount);

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("")) {    //if authentic, navigate user to a new page
                readWriteFile.saveOnFile(account);
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                signLoginAction();
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    @FXML
    protected void signLoginAction() throws Exception {
        CreateWindow.signLoginAction(signInGym);
    }

    @FXML
    private void homeAction() throws Exception {
        CreateWindow.homeAction(home);
    }
    @FXML
    private void skipAction() throws Exception {
        CreateWindow.loginAction(skipSignUpGym);
    }
    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signInGym) {
            HighlightButton.lightColor(signInGym);
        } else if (event.getSource() == home) {
            HighlightButton.lightColor(home);
        } else if (event.getSource() == submitSignUpGym) {
            HighlightButton.lightColor(submitSignUpGym);
        } else if (event.getSource() == skipSignUpGym) {
            HighlightButton.lightColor(skipSignUpGym);
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
        } else if (event.getSource() == submitSignUpGym) {
            HighlightButton.darkColor(submitSignUpGym);
        } else if (event.getSource() == skipSignUpGym) {
            HighlightButton.darkColor(skipSignUpGym);
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

