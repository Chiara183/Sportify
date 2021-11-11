package com.example.sportify;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Stage stage;
    Parent root;
    HashMap<String, String[]> account = new HashMap<>();

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    //Button
    @FXML
    private Button signUp;
    @FXML
    private Button signIn;
    @FXML
    private Button home;
    /*@FXML
    private Button submitLogin;
    @FXML
    private Button submitSignUp;
    @FXML
    private Button skipLogin;
    @FXML
    private Button skipSignUp;
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;*/

    @FXML
    protected void submitActionLogin() {

        String userValue = username.getText(); 								//get user entered username from the textField1
        String passValue = password.getText(); 								//get user entered password from the textField2

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (!account.isEmpty() && account.containsKey(userValue) && userValue.equals(account.get(userValue)[0]) && passValue.equals(account.get(userValue)[1])) {    //if authentic, navigate user to a new page
            JOptionPane.showMessageDialog(jFrame, "Correct!");
        } else {
            if (!account.isEmpty()) {
                System.out.println("HashMap is Empty");
            } else {
                System.out.println("HashMap not find a key\n" + Arrays.toString(account.values().toArray()));
            }
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter valid username and password or Signup");
        }
    }

    @FXML
    protected void submitActionSignUp() throws Exception {

        String userValue = username.getText();                                //get user entered username from the textField1
        String passValue = password.getText();                                //get user entered password from the textField2
        String nameValue = firstName.getText();                            //get user entered firstName from the textField3
        String lastNameValue = lastName.getText();                            //get user entered lastName from the textField4

        //check whether the credentials are authentic or not
        JFrame jFrame = new JFrame();
        if (!userValue.equals("") && !passValue.equals("") && !nameValue.equals("") && !lastNameValue.equals("")) {    //if authentic, navigate user to a new page
            String[] userAccount = new String[4];
            userAccount[0] = userValue;
            userAccount[1] = passValue;
            userAccount[2] = nameValue;
            userAccount[3] = lastNameValue;
            account.put(userValue, userAccount);
            System.out.println("HashMap not find a key\n" + Arrays.toString(account.values().toArray()));
            JOptionPane.showMessageDialog(jFrame,
                    "You're registered with:\n" +
                            "\nFirstname: " + nameValue +
                            "\nLastname: " + lastNameValue +
                            "\n Username: " + userValue +
                            "\nPassword: " + passValue +
                            "\n\nThank you for your registration!");
            signLoginAction();
        }
        else{
            //show error message
            JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
        }
    }

    @FXML
    protected void sportQuizAction() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You're in sport quiz form!");
    }

    @FXML
    protected void findGymAction() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You try to find a Gym!");
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
    private void signLoginAction() throws Exception {
        stage = (Stage) signIn.getScene().getWindow();

        //LoginScene
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene sceneSignIn = new Scene(root, 780, 438);

        //set stage
        stage.setTitle("LOGIN FORM");
        stage.setScene(sceneSignIn);
        stage.setResizable(false);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    private void homeAction() throws Exception {
        stage = (Stage) home.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));

        //HomeScene
        Scene sceneHome = new Scene(root, 882, 464);

        //set stage
        stage.setTitle("HOME FORM");
        stage.setScene(sceneHome);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
