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

public class SignUpGymController implements Initializable {

    Stage stage;
    Parent root;

    //TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    //Button
    @FXML
    private Button signIn;
    @FXML
    private Button home;
    @FXML
    private Button submitSignUpGym;
    @FXML
    private Button skipSignUpGym;

    @FXML
    protected void submitActionSignUpGym(ActionEvent event) throws Exception {
        if (event.getSource().equals(KeyCode.ENTER) || event.getSource().equals(submitSignUpGym)) {
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
                saveOnFile(account);
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
                signLoginAction();
            } else {
                //show error message
                JOptionPane.showMessageDialog(jFrame, "Please enter all value.");
            }
        }
    }

    protected void saveOnFile(HashMap<String, String[]> map) {
        String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login";
        Path path = Path.of(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login");
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
        } else if (event.getSource() == submitSignUpGym) {
            submitSignUpGym.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUpGym) {
            skipSignUpGym.setStyle("-fx-background-color: #53a5ff; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @FXML
    private void darkColor(MouseEvent event) {
        if (event.getSource() == signIn) {
            signIn.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == home) {
            home.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == submitSignUpGym) {
            submitSignUpGym.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        } else if (event.getSource() == skipSignUpGym) {
            skipSignUpGym.setStyle("-fx-background-color:  #2571b9; -fx-border-radius: 8px; -fx-background-radius: 12px; -fx-border-width: 2px; -fx-border-color: #000000; -fx-background-size: 2px;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

