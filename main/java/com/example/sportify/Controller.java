package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    Stage stage;
    Parent root;

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    //Button
    @FXML
    private Button signUp;
    @FXML
    private Button signIn;
    @FXML
    private Button home;
    @FXML
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
    private Button findGym;

    //CheckBox
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;


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

    @FXML
    protected void submitActionSignUp(ActionEvent event) throws Exception {
        HashMap<String, String[]> account = new HashMap<>();
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUp)) {
            String[] userAccount = new String[4];                               //initialize list of string 'userAccount'
            String userValue = username.getText();                              //get user entered username
            String passValue = password.getText();                              //get user entered password
            String nameValue = firstName.getText();                             //get user entered first name
            String lastNameValue = lastName.getText();                          //get user entered last name
            userAccount[0] = userValue;                                         //put userValue in userAccount
            userAccount[1] = passValue;                                         //put user password in userAccount
            userAccount[2] = nameValue;                                         //put user firstName in userAccount
            userAccount[3] = lastNameValue;                                     //put user lastName in userAccount
            account.put(userValue, userAccount);                                //add userAccount

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!userValue.equals("") && !passValue.equals("") && !nameValue.equals("") && !lastNameValue.equals("")) {    //if authentic, navigate user to a new page
                if (userTick.isSelected()) {
                    saveOnFile(account, "login");
                    JOptionPane.showMessageDialog(jFrame, "You're registered!");
                    signLoginAction();
                } else if (gymTick.isSelected()) {
                    saveOnFile(account, "login");
                    signSignUpGymAction();
                }
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    @FXML
    protected void submitActionSignUpGym(ActionEvent event) throws Exception {
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUp)) {
            HashMap<String, String[]> account = new HashMap<>();
            String[] userGymAccount = new String[3];                                //initialize list of string 'userGymAccount'
            String gymValue = gymName.getText();                                    //get user entered gym name
            String addressValue = gymAddress.getText();                             //get user entered gym address
            String cityValue = gymCity.getText();                                   //get user entered gym city
            userGymAccount[0] = gymValue;                                           //put userValue in userAccount
            userGymAccount[1] = addressValue;                                       //put user password in userAccount
            userGymAccount[2] = cityValue;                                          //put user firstName in userAccount
            account.put(gymValue, userGymAccount);

            //check whether the credentials are authentic or not
            JFrame jFrame = new JFrame();
            if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("")) {    //if authentic, navigate user to a new page
                saveOnFile(account, "login");
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                signLoginAction();
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    protected void saveOnFile(HashMap<String, String[]> map, String str) {
        String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + str;
        Path path = Path.of(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + str);
        String mapAsString = map.keySet().stream().map(key -> key + "=" + Arrays.toString(map.get(key))).collect(Collectors.joining(", ", "{", "}"));
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.length() == 0) {
                    Files.writeString(path, mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    Files.writeString(path, "\n" + mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            } else if (file.createNewFile()) {
                Files.writeString(path, mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                System.out.println("The file cannot be created");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    private void signSignUpGymAction() throws Exception {
        stage = (Stage) submitSignUp.getScene().getWindow();

        //SignUpScene
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUpGym.fxml")));
        Scene sceneSignUpGym = new Scene(root, 814, 456);

        //set stage
        stage.setTitle("SIGN UP FORM");
        stage.setScene(sceneSignUpGym);
        stage.show();
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
        Scene sceneSignIn = new Scene(root, 780, 437);

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
        } else if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitLogin) {
            submitLogin.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUp) {
            submitSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUp) {
            skipSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipLogin) {
            skipLogin.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == sportQuiz) {
            sportQuiz.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == findGym) {
            findGym.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signUp) {
            signUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitLogin) {
            submitLogin.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUp) {
            submitSignUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUp) {
            skipSignUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipLogin) {
            skipLogin.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == sportQuiz) {
            sportQuiz.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == findGym) {
            findGym.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void tickAction(javafx.event.ActionEvent event) {
        if (event.getSource() == gymTick) {
            userTick.setSelected(false);
        } else if (event.getSource() == userTick) {
            gymTick.setSelected(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
