package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.readWriteFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to submit.
    private Submit submit;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public SignUpGymController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSubmit(Submit submit){
        this.submit = submit;
    }

    @FXML
    protected void submitActionSignUpGym() {
        String userValue = "gymTick";
        readWriteFile file = new readWriteFile();
        HashMap<String, HashMap<String, String>> account= file.readFile();
        HashMap<String, String> userGymAccount = account.get(userValue);
        String gymValue = gymName.getText();            //get user entered gym name
        String addressValue = gymAddress.getText();     //get user entered gym address
        String cityValue = gymCity.getText();           //get user entered gym city
        userGymAccount.put("gymName", gymValue);        //put userValue in userAccount
        userGymAccount.put("gymAddress", addressValue); //put user password in userAccount
        userGymAccount.put("gymCity", cityValue);       //put user firstName in userAccount

        //check whether the credentials are authentic or not
        if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("")) {
            //if authentic, navigate user to a new page
            this.submit.signUp(userGymAccount.get("username"), userGymAccount, "gymTick");
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You're registered!");
            login();
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Empty field");
            alert.setHeaderText("Some obligatory value are empty");
            alert.setContentText("Please enter all value.");
            alert.showAndWait();
        }
    }

    private void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
    }

    @FXML
    private void skipAction(){
        login();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}


