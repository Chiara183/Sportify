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

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField username, password, firstName, lastName;

    @FXML
    private Button signUp, submitLogin, submitSignUp, skipLogin, skipSignUp, signIn, signIn1;

    @FXML
    protected void submitAction() {

        String userValue = username.getText(); 								//get user entered username from the textField1
        String passValue = password.getText(); 								//get user entered password from the textField2

        //check whether the credentials are authentic or not
        if (userValue.equals("test1@gmail.com") && passValue.equals("test")){	//if authentic, navigate user to a new page

            System.out.println("Correct!");
        }
        else{
            //show error message
            System.out.println("Please enter valid username and password");
        }
    }

    @FXML
    private void signAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        if(event.getSource()==signUp) {
            stage = (Stage) signUp.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Scene sceneSignUp = new Scene(root, 814, 456);
            stage.setTitle("SIGN UP FORM");
            stage.setScene(sceneSignUp);
            stage.show();
        } else if(event.getSource()==signIn){
            stage = (Stage) signIn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene sceneSignUp = new Scene(root, 780, 438);
            stage.setTitle("LOGIN FORM");
            stage.setScene(sceneSignUp);
            stage.setResizable(false);
            stage.show();
            stage.setResizable(false);
        } else if(event.getSource()==signIn1){
            stage = (Stage) signIn1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene sceneSignUp = new Scene(root, 780, 438);
            stage.setTitle("LOGIN FORM");
            stage.setScene(sceneSignUp);
            stage.setResizable(false);
            stage.show();
            stage.setResizable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
