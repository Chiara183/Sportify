package com.example.sportify;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateWindow implements Initializable {

    private static void create(Button button, Parent root, String string){
        //HomeScene
        Scene sceneBack = new Scene(root, 780, 437);
        Stage stage = (Stage) button.getScene().getWindow();

        //set stage
        stage.setTitle(string);
        stage.setScene(sceneBack);
        stage.setResizable(false);
        stage.show();
    }

    public static void signSignUpGymAction(Button submitSignUp) throws Exception {
        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUpGym.fxml")));
        create(submitSignUp, root, "SIGN UP");
    }

    public static void homeAction(Button home) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Home.fxml")));
        create(home, root, "HOME");
    }

    public static void loginAction(Button login) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Login.fxml")));
        create(login, root, "LOGIN FORM");
    }

    public static void signSignUpAction(Button signUp) throws Exception {
        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUp.fxml")));
        create(signUp, root, "SIGN UP");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
