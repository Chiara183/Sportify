package com.example.sportify.controller;

import com.example.sportify.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class SignUpController implements Initializable {

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
    private TextField email;
    @FXML
    private TextField pass_text;

    //Label
    @FXML
    private Label eye;

    //CheckBox
    @FXML
    private CheckBox pass_toggle;
    @FXML
    CheckBox gymTick;
    @FXML
    CheckBox userTick;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to submit.
    private final Submit submit;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public SignUpController() {
        this.submit = new Submit();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void set_toggle_pass(){
        if(!this.pass_toggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #06B7C5;");
            pass_toggle.setSelected(true);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            pass_toggle.setSelected(false);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        }
    }

    @FXML
    protected void submitActionSignUp() {
        HashMap<String, String> userAccount = new HashMap<>();  //initialize list of string 'userAccount'
        String userValue = username.getText();                  //get user entered username
        String passValue = password.getText();                  //get user entered password
        String nameValue = firstName.getText();                 //get user entered first name
        String lastNameValue = lastName.getText();              //get user entered last name
        String email = this.email.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        userAccount.put("username", userValue);                 //put userValue in userAccount
        userAccount.put("password", passValue);                 //put user password in userAccount
        userAccount.put("firstName", nameValue);                //put user firstName in userAccount
        userAccount.put("lastName", lastNameValue);             //put user lastName in userAccount
        userAccount.put("email", email);                           //put user email in userAccount
        userAccount.put("birthday", date);                      //put user birthday in userAccount
        DAO obj_DAO = new DAO();
        ResultSet rs = obj_DAO.Check_Data(
                "SELECT email " +
                        "FROM user " +
                        "WHERE user.email = \"" + email + "\"");

        //check whether the credentials are authentic or not
        try{
            if (!userValue.equals("") && !passValue.equals("") && !this.submit.exist(userValue) && rs.next()) {    //if authentic, navigate user to a new page
                if (userTick.isSelected()) {
                    userAccount.put("ruolo", "user");
                    this.submit.signUp(userAccount);
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "You're registered!");
                    login();
                } else if (gymTick.isSelected()) {
                    userAccount.put("ruolo", "gym");
                    this.submit.signUp(userAccount);
                    signUpGymAction();
                }
            } else {
                if (this.submit.exist(userValue)){
                    //show error message
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("User already exists");
                    alert.setHeaderText("The user already exists");
                    alert.setContentText("Please enter a different username or login.");
                    alert.showAndWait();
                } else if (rs.next()){
                    //show error message
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("User already exists");
                    alert.setHeaderText("The email is already registered");
                    alert.setContentText("Please enter a different email or login.");
                    alert.showAndWait();
                } else {
                    //show error message
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Empty field");
                    alert.setHeaderText("Some obligatory value are empty");
                    alert.setContentText("Please enter all obligatory value.");
                    alert.showAndWait();
                }
            }
        }catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    private void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
    }

    @FXML
    private void signUpGymAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("SignUpGym.fxml"));
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGymController controller = loaderSignUp.getController();
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void skipAction(){
        login();
    }

    @FXML
    private void tickAction(ActionEvent event) {
        if (event.getSource() == gymTick && userTick.isSelected()) {
            userTick.setSelected(false);
        } else if (event.getSource() == userTick && gymTick.isSelected()) {
            gymTick.setSelected(false);
        } else if (event.getSource() == gymTick && !userTick.isSelected()) {
            userTick.setSelected(true);
        } else if (event.getSource() == userTick && !gymTick.isSelected()) {
            gymTick.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}


