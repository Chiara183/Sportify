package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.util.HashMap;

public class SignUpGymController extends ButtonAction implements Initializable {

    //TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    //Button
    @FXML
    private Button submitSignUpGym;
    @FXML
    private Button skipSignUpGym;

    @FXML
    protected void submitActionSignUpGym(ActionEvent event) throws Exception {
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUpGym)) {
            String userValue = "gymTick";
            HashMap<String, HashMap<String, String>> account= readWriteFile.readFile();
            HashMap<String, String> userGymAccount = account.get(userValue);
            String gymValue = gymName.getText();            //get user entered gym name
            String addressValue = gymAddress.getText();     //get user entered gym address
            String cityValue = gymCity.getText();           //get user entered gym city
            userGymAccount.put("gymName", gymValue);        //put userValue in userAccount
            userGymAccount.put("gymAddress", addressValue); //put user password in userAccount
            userGymAccount.put("gymCity", cityValue);       //put user firstName in userAccount

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("")) {
                //if authentic, navigate user to a new page
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
    private void skipAction() throws Exception {
        CreateWindow.login(skipSignUpGym);
    }
}

