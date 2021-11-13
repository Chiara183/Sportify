package com.example.sportify;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    Stage stage;
    Parent root;

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    //Button
    @FXML
    private Button signUp;
    @FXML
    private Button home;
    @FXML
    private Button submitLogin;
    @FXML
    private Button skipLogin;


    @FXML
    protected void submitActionLogin() throws IOException {
        HashMap<String, String[]> account;
        account = readFile("login");

        String userValue = username.getText();                                //get user entered username from the textField1
        String passValue = password.getText();                                //get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (!account.isEmpty() && account.containsKey(userValue) && userValue.equals(account.get(userValue)[0]) && passValue.equals(account.get(userValue)[1])) {    //if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Correct!");
        } else {
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter valid username and password or Signup");
        }
    }

    protected HashMap<String, String[]> readFile(String str) throws IOException {
        // File path is passed as parameter
        File file = new File(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + str);

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st;
        // Declaring a HashMap variable
        HashMap<String, String[]> hashMap = new HashMap<>();
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null){
            // Remove first and last char from st
            st = st.substring(1, st.length() - 1);
            // split the String by a comma
            String[] parts = st.split("=");
            // Create HashMap key
            String key = parts[0].trim();
            // Create a list string
            String list = parts[1].substring(1, parts[1].length() - 1);
            // split the String by a comma
            String[] listParts = list.split(", ");
            // Add to map
            hashMap.put(key, listParts);
        }
        return (hashMap);
    }

    @FXML
    private void signSignUpAction() throws Exception {
        stage = (Stage) signUp.getScene().getWindow();

        //SignUpScene
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        Scene sceneSignUp = new Scene(root, 814, 456);

        //set stage
        stage.setTitle("SIGN UP FORM");
        stage.setScene(sceneSignUp);
        stage.show();
    }

    @FXML
    private void homeAction() throws Exception {
        stage = (Stage) home.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));

        //HomeScene
        Scene sceneHome = new Scene(root, 780, 437);

        //set stage
        stage.setTitle("HOME FORM");
        stage.setScene(sceneHome);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void lightColor(MouseEvent event) {
        if (event.getSource() == signUp) {
            signUp.setStyle("-fx-background-color:  #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitLogin) {
            submitLogin.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipLogin) {
            skipLogin.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signUp) {
            signUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitLogin) {
            submitLogin.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipLogin) {
            skipLogin.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

