package com.example.sportify.controller;

import com.example.sportify.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    //CheckBox
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to submit.
    private final Submit submit;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public SignUpController() {
        this.submit = new Submit();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    protected void submitActionSignUp() {
        HashMap<String, String> userAccount = new HashMap<>();  //initialize list of string 'userAccount'
        String userValue = username.getText();                  //get user entered username
        String passValue = password.getText();                  //get user entered password
        String nameValue = firstName.getText();                 //get user entered first name
        String lastNameValue = lastName.getText();              //get user entered last name
        userAccount.put("username", userValue);                 //put userValue in userAccount
        userAccount.put("password", passValue);                 //put user password in userAccount
        userAccount.put("firstName", nameValue);                //put user firstName in userAccount
        userAccount.put("lastName", lastNameValue);             //put user lastName in userAccount

        //check whether the credentials are authentic or not
        if (!userValue.equals("") && !passValue.equals("") && !nameValue.equals("") && !lastNameValue.equals("")) {    //if authentic, navigate user to a new page
            if (userTick.isSelected()) {
                this.submit.signUp(userValue, userAccount);
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                login();
            } else if (gymTick.isSelected()) {
                this.submit.signUp("gymTick", userAccount);
                signUpGymAction();
            }
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
    private void signUpGymAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("SignUpGym.fxml"));
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGymController controller = loaderSignUp.getController();
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void skipAction(){
        login();
    }

    @FXML
    private void tickAction(ActionEvent event) {
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


