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

public class SignUpGymController implements Initializable {

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
    private Button signUp;
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
    protected void sportQuizAction() throws Exception {
        CreateWindow.sportQuiz(sportQuiz);
    }

    @FXML
    protected void findGymAction() throws Exception {
        CreateWindow.findGym(findGym);
    }

    @FXML
    protected void submitActionSignUpGym(ActionEvent event) throws Exception {
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUpGym)) {
            String userValue = "gymTick";
            HashMap<String, HashMap<String, String>> account= readWriteFile.readFile();
            HashMap<String, String> userGymAccount = account.get(userValue);
            String gymValue = gymName.getText();                                    //get user entered gym name
            String addressValue = gymAddress.getText();                             //get user entered gym address
            String cityValue = gymCity.getText();                                   //get user entered gym city
            userGymAccount.put("gymName", gymValue);                                //put userValue in userAccount
            userGymAccount.put("gymAddress", addressValue);                         //put user password in userAccount
            userGymAccount.put("gymCity", cityValue);                               //put user firstName in userAccount

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("")) {    //if authentic, navigate user to a new page
                Submit.signUp(userGymAccount.get("username"), userGymAccount, "gymTick");
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
        CreateWindow.login(signInGym);
    }
    @FXML
    protected void signUpAction() throws Exception {
        CreateWindow.signUp(signUp);
    }
    @FXML
    private void homeAction() throws Exception {
        CreateWindow.home(home);
    }

    @FXML
    private void skipAction() throws Exception {
        CreateWindow.login(skipSignUpGym);
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

