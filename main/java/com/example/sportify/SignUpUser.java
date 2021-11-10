package com.example.sportify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SignUpUser extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));

		//SignUp scene
		Scene sceneSignUp = new Scene(root, 814, 456);

		//set stage
		stage.setTitle("SIGN UP FORM");
		stage.setScene(sceneSignUp);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}