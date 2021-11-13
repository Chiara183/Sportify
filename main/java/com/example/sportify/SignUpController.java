package com.example.sportify;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class SignUpController implements Initializable {

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

    //Button
    @FXML
    private Button signIn;
    @FXML
    private Button home;
    @FXML
    private Button submitSignUp;
    @FXML
    private Button skipSignUp;

    //CheckBox
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;

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
        if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUp) {
            submitSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUp) {
            skipSignUp.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUp) {
            submitSignUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUp) {
            skipSignUp.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
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

