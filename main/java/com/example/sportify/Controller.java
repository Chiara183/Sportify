package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Stage stage;
    Parent root;

    @FXML
    private TextField username, password, firstName, lastName;

    @FXML
    private Button signUp, submitLogin, submitSignUp, skipLogin, skipSignUp, signIn, home;

    @FXML
    protected void submitActionLogin() {

        String userValue = username.getText(); 								//get user entered username from the textField1
        String passValue = password.getText(); 								//get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (userValue.equals("test1@gmail.com") && passValue.equals("test")){	//if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Correct!");
        }
        else{
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter valid username and password");
        }
    }

    @FXML
    protected void submitActionSignUp() {

        String userValue = username.getText(); 								//get user entered username from the textField1
        String passValue = password.getText(); 								//get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if ( !userValue.equals("") && !passValue.equals("") && !firstName.equals("") && !lastName.equals("")){	//if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Thank you for your registration!");
        }
        else{
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
        }
    }

    @FXML
    private void signAction(ActionEvent event) throws Exception{
        if(event.getSource()==signUp) {
            stage = (Stage) signUp.getScene().getWindow();

            //SignUpScene
            root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Scene sceneSignUp = new Scene(root, 814, 456);

            //set stage
            stage.setTitle("SIGN UP FORM");
            stage.setScene(sceneSignUp);
            stage.show();
        } else if(event.getSource()==signIn){
            stage = (Stage) signIn.getScene().getWindow();

            //LoginScene
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene sceneSignUp = new Scene(root, 780, 438);

            //set stage
            stage.setTitle("LOGIN FORM");
            stage.setScene(sceneSignUp);
            stage.setResizable(false);
            stage.show();
            stage.setResizable(false);
        }
    }

    @FXML
    private void homeAction(ActionEvent event) throws Exception{
        if(event.getSource()==home) {
            stage = (Stage) home.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Home.fxml"));

            //HomeScene
            Scene sceneHome = new Scene(root, 757, 536);

            //set stage
            stage.setTitle("HOME FORM");
            stage.setScene(sceneHome);
            stage.setResizable(false);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
