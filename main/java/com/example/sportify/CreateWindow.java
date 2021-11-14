package com.example.sportify;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

public class CreateWindow implements Initializable {


    public static void signSignUpGymAction(Button submitSignUp) throws Exception {
        Stage stage = (Stage) submitSignUp.getScene().getWindow();

        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUpGym.fxml")));
        Scene sceneSignUpGym = new Scene(root, 814, 456);

        //set stage
        stage.setTitle("SIGN UP FORM");
        stage.setScene(sceneSignUpGym);
        stage.show();
    }

    public static void signLoginAction(Button signIn) throws Exception {
        Stage stage = (Stage) signIn.getScene().getWindow();

        //LoginScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Login.fxml")));
        Scene sceneSignIn = new Scene(root, 780, 437);

        //set stage
        stage.setTitle("LOGIN FORM");
        stage.setScene(sceneSignIn);
        stage.setResizable(false);
        stage.show();
        stage.setResizable(false);
    }

    public static void homeAction(Button home) throws Exception {
        Stage stage = (Stage) home.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Home.fxml")));

        //HomeScene
        Scene sceneHome = new Scene(root, 780, 437);

        //set stage
        stage.setTitle("HOME FORM");
        stage.setScene(sceneHome);
        stage.setResizable(false);
        stage.show();
    }

    public static void signSignUpAction(Button signUp) throws Exception {
        Stage stage = (Stage) signUp.getScene().getWindow();

        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUp.fxml")));
        Scene sceneSignUp = new Scene(root, 814, 456);

        //set stage
        stage.setTitle("SIGN UP FORM");
        stage.setScene(sceneSignUp);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
