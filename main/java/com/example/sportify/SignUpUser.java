package com.example.sportify;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class SignUpUser extends Application {
	@Override
	//initialize button, panel, label, and text field
	public void start(Stage stage) throws IOException {
		//initialize button, panel, label, and text field
		FXMLLoader fxmlLoader = new FXMLLoader(com.example.sportify.Login.class.getResource("SignUp.fxml"));
		Scene sceneSignUp = new Scene(fxmlLoader.load(), 780, 438);
		stage.setTitle("SIGN UP FORM");
		stage.setScene(sceneSignUp);
		stage.setResizable(false);
		stage.show();
	}
	/*JButton b1;
	JPanel newPanel;
	JLabel userLabel, passLabel, firstNameLabel, lastNameLabel;
	final JTextField textField1, textField2, textField3, textField4;
	
	//calling constructor
	SignUpUser() {
		
		//create label for username
		userLabel = new JLabel();
		userLabel.setText("*Username");											//set label value for textField1
			
		//create text field to get username from the user
		textField1 = new JTextField(15);										//set length of the text
		
		//create label for password
		passLabel = new JLabel();
		passLabel.setText("*Password");											//set label value for textField2
		
		//create text field to get password from user
		textField2 = new JPasswordField(15);									//set length for the password
		
		//create label for first name
		firstNameLabel = new JLabel();
		firstNameLabel.setText("*First Name");									//set label value for textField3
		
		//create text field to get first name from user
		textField3 = new JTextField(15);										//set length for the first name
		
		//create label for last name
		lastNameLabel = new JLabel();
		lastNameLabel.setText("*Last Name");										//set label value for textField4
		
		//create text field to get last name from user
		textField4 = new JTextField(15);										//set length for the last name
		
		//create submit button
		b1 = new JButton("SIGN UP");											//set label to button
		
		//create panel to put form elements
		newPanel = new JPanel(new GridLayout(5, 1));
		newPanel.add(userLabel);												//set username label to panel
		newPanel.add(textField1);												//set text field to panel
		newPanel.add(passLabel);												//set password label to panel
		newPanel.add(textField2);												//set text field to panel
		newPanel.add(firstNameLabel);											//set first name label to panel
		newPanel.add(textField3);												//set text field to panel
		newPanel.add(lastNameLabel);											//set last name label to panel
		newPanel.add(textField4);												//set text field to panel
		newPanel.add(b1);														//set button to panel
		
		//set border to panel
		add(newPanel, BorderLayout.CENTER);
		
		//perform action on button click
		b1.addActionListener(this);												//add action listener to button
		setTitle("SIGN UP FORM");												//set title to sign up form
	}
	
	//define abstract method actionPerformed() which will be called on button click
	public void actionPerformed(ActionEvent ae) {								//pass action listener as a parameter
		
		String userValue = textField1.getText(); 								//get user entered username from the textField1
		String passValue = textField2.getText(); 								//get user entered password from the textField2
		String firstNameValue = textField3.getText(); 							//get user entered first name from the textField3
		String lastNameValue = textField4.getText(); 							//get user entered last name from the textField4
		
		//check whether the credentials are authentic or not
		if (!userValue.equals("") && !passValue.equals("") && !firstNameValue.equals("") && !lastNameValue.equals("")){	//if not empty fields, navigate user to a new page
			
			//create instance of the NewPage
			NewPage page = new NewPage();
			
			//make page visible to the user
			page.setVisible(true);
			
			//create a welcome label and set it to the new page
			JLabel wel_label = new JLabel("Thank you for signing up " + userValue);
			page.getContentPane().add(wel_label);
		}
		else{
			//show error message
			System.out.println("Please fill the requested fields");
		}
	}*/

	public static void main(String[] args) {
		launch();
	}
}