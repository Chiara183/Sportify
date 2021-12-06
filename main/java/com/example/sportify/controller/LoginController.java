package com.example.sportify.controller;

import com.example.sportify.*;
import javafx.fxml.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable{

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoginController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) throws IOException {
        this.mainApp = mainApp;
    }

    @FXML
    protected void submitActionLogin() throws Exception {
        String userValue = username.getText();      //get user entered username from the textField1
        String passValue = password.getText();      //get user entered password from the textField2

        //check whether the credentials are authentic or not
        if (Submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Correct!");
            home();
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Wrong Username or Password");
            alert.setHeaderText("You wrote wrong username or password");
            alert.setContentText("Please enter valid username and password or Signup");
            alert.showAndWait();
        }
    }

    private void home(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Home");
        try {
            // Load home overview.
            FXMLLoader loaderHome = new FXMLLoader();
            loaderHome.setLocation(MainApp.class.getResource("Home.fxml"));
            Pane pane = loaderHome.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);
            this.mainApp.getPrimaryPane().setTop(null);

            // Give the controller access to the main app.
            HomeController controller = loaderHome.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void skipAction() {
        this.mainApp.getPrimaryStage().setTitle("Sportify - Home");
        try {
            // Load person overview.
            FXMLLoader loaderHome = new FXMLLoader();
            loaderHome.setLocation(MainApp.class.getResource("Home.fxml"));
            Pane pane = loaderHome.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);
            this.mainApp.getPrimaryPane().setTop(null);

            // Give the controller access to the main app.
            HomeController controller = loaderHome.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}


