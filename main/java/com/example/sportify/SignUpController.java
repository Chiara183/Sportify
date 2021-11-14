package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

import javax.swing.*;
import java.net.URL;
import java.util.*;

public class SignUpController extends HomeController implements Initializable {

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
    private Button login;
    @FXML
    private Button home;
    @FXML
    private Button submitSignUp;
    @FXML
    private Button skipSignUp;

    //CheckBox
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;

    @FXML
    protected void submitActionSignUp(ActionEvent event) throws Exception {

        // Create HashMap
        HashMap<String, String[]> account = new HashMap<>();

        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUp)) {
            String[] userAccount = new String[4];                               //initialize list of string 'userAccount'
            String userValue = username.getText();                              //get user entered username
            String passValue = password.getText();                              //get user entered password
            String nameValue = firstName.getText();                             //get user entered first name
            String lastNameValue = lastName.getText();                          //get user entered last name
            userAccount[0] = userValue;                                         //put userValue in userAccount
            userAccount[1] = passValue;                                         //put user password in userAccount
            userAccount[2] = nameValue;                                         //put user firstName in userAccount
            userAccount[3] = lastNameValue;                                     //put user lastName in userAccount
            account.put(userValue, userAccount);                                //add userAccount

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!userValue.equals("") && !passValue.equals("") && !nameValue.equals("") && !lastNameValue.equals("")) {    //if authentic, navigate user to a new page
                if (userTick.isSelected()) {
                    readWriteFile.saveOnFile(account);
                    JOptionPane.showMessageDialog(jFrame, "You're registered!");
                    signLoginAction();
                } else if (gymTick.isSelected()) {
                    readWriteFile.saveOnFile(account);
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

    //@FXML
   //private void signLoginAction() throws Exception {
    // CreateWindow.signLoginAction(signIn);
    //}

    @FXML
    protected void homeAction() throws Exception {
        CreateWindow.homeAction(home);
    }

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == login) {
            login.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUp) {
            submitSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUp) {
            skipSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == login) {
            HighlightButton.darkColor(login);
        } else if (event.getSource() == home) {
            HighlightButton.darkColor(home);
        } else if (event.getSource() == submitSignUp) {
            HighlightButton.darkColor(submitSignUp);
        } else if (event.getSource() == skipSignUp) {
            HighlightButton.darkColor(skipSignUp);
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

