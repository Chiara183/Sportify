package com.example.sportify;

//import required classes and packages
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Login extends Application {
	private Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		//initialize button, panel, label, and text field
		stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(com.example.sportify.Login.class.getResource("Login.fxml"));
		Scene sceneLogin = new Scene(fxmlLoader.load(), 780, 438);
		stage.setTitle("LOGIN FORM");
		stage.setScene(sceneLogin);
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	private TextField username, password;

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
	protected void signUpAction(){
		try{
			stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(com.example.sportify.Login.class.getResource("SignUp.fxml"));
			Scene sceneSignUp = new Scene(fxmlLoader.load(), 780, 438);
			//setScene(sceneSignUp);
			stage.setTitle("SIGN UP FORM");
			stage.setScene(sceneSignUp);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch();
	}
}
