package com.example.sportify;

//import required classes and packages

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login extends Application{

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));

		//LoginScene
		Scene sceneLogin = new Scene(root, 780, 438);

		//set stage
		stage.setTitle("LOGIN FORM");
		stage.setScene(sceneLogin);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
