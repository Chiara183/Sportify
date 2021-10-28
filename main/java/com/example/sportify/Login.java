package main.java;

//import required classes and packages
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Login extends Application{
	@Override
	public void start(Stage stage) throws IOException {
		//initialize button, panel, label, and text field
		FXMLLoader fxmlLoader = new FXMLLoader(com.example.sportify.Login.class.getResource("Login.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600, 338);
		stage.setTitle("LOGIN FORM");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	private TextField username, password;

	
	//define abstract method actionPerformed() which will be called on button click
	protected void submitAction() {
		
		String userValue = username.getText(); 								//get user entered username from the textField1
		String passValue = password.getText(); 								//get user entered password from the textField2
		
		//check whether the credentials are authentic or not
		if (userValue.equals("test1@gmail.com") && passValue.equals("test")){	//if authentic, navigate user to a new page
			
			//create instance of the NewPage
			NewPage page = new NewPage();
			
			//make page visible to the user
			page.setVisible(true);
			
			//create a welcome label and set it to the new page
			JLabel wel_label = new JLabel("Welcome " + userValue);
			page.getContentPane().add(wel_label);
		}
		else{
			//show error message
			System.out.println("Please enter valid username and password");
		}
	}

	public static void main(String[] args) {
		launch();
	}
}
