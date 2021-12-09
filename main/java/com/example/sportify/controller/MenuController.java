package com.example.sportify.controller;

import com.example.sportify.MainApp;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MenuController implements Initializable {

    //Button
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;
    @FXML
    private Button signIn;
    @FXML
    private Button signUp;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public MenuController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void homeAction() {
        this.mainApp.showHomeOverview();
    }

    @FXML
    private void sportQuizAction() {
        findGym.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        sportQuiz.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showSportQuizOverview();
    }

    @FXML
    private void findGymAction() {
        this.mainApp.getPrimaryStage().setTitle("Sportify - Find Gym");
        sportQuiz.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        findGym.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        try {
            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(MainApp.class.getResource("FindGym.fxml"));
            Pane pane = loaderGym.load();

            // Set find gym overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            FindGymController controller = loaderGym.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void signLoginAction() {
        findGym.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("");
        signIn.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showLoginOverview();
    }

    @FXML
    private void signUpAction() {
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        findGym.setStyle("");
        signIn.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        try {
            // Load sign up overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("SignUp.fxml"));
            Pane pane = loaderSignUp.load();

            // Set sign up overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpController controller = loaderSignUp.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

    public void setSportQuiz() {
        findGym.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        sportQuiz.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void setFindGym() {
        sportQuiz.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        findGym.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void setLogin() {
        findGym.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("");
        signIn.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }
}

