package com.example.sportify.controller;

import com.example.sportify.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SportQuizController implements Initializable {

    @FXML
    private Button backQuiz;
    @FXML
    private Button backQuizEnv;
    @FXML
    private Button nextQuiz;
    @FXML
    private Button backQuizType;
    @FXML
    private Button nextQuizEnv;
    @FXML
    private Button nextQuizType;
    @FXML
    private Button age1;
    @FXML
    private Button age2;
    @FXML
    private Button age3;
    @FXML
    private Button age4;
    @FXML
    private Button indoor;
    @FXML
    private Button outdoor;
    @FXML
    private Button group;
    @FXML
    private Button single;

    private boolean buttonAge1;
    private boolean buttonAge2;
    private boolean buttonAge3;
    private boolean buttonAge4;
    private boolean buttonIndoor;
    private boolean buttonOutdoor;
    private boolean buttonGroup;
    private boolean buttonSingle;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SportQuizController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void home(){
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

    private void sportQuiz(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SportQuiz.fxml"));
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizController controller = loaderSport.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sportQuizEnv(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SportQuizEnv.fxml"));
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizController controller = loaderSport.getController();
            controller.setMainApp(this.mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sportQuizType(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SportQuizType.fxml"));
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizController controller = loaderSport.getController();
            controller.setMainApp(this.mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backQuizAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        if(b == backQuiz){
            home();
        }else if(b == backQuizEnv){
            sportQuiz();
        }else if(b == backQuizType){
            sportQuizEnv();
        }
    }

    @FXML
    public void nextQuizAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        if(b == nextQuiz){
            sportQuizEnv();
        }else if(b == nextQuizEnv){
            sportQuizType();
        }
    }

    @FXML
    public void takeQuiz(ActionEvent event) {
        //TODO : takeQuiz on each button on fxml files
        //TODO : create interfaces for quiz answers
        //TODO : create function to go to answer's interfaces

        Button b = (Button) event.getSource();
        if (b == age1) {
            buttonAge1 = true;
        }
        if (b == age2) {
            buttonAge2 = true;
        }
        if (b == age3) {
            buttonAge3 = true;
        }
        if (b == age4) {
            buttonAge4 = true;
        }
        if (b == indoor) {
            buttonIndoor = true;
        }
        if (b == outdoor) {
            buttonOutdoor = true;
        }
        if (b == group) {
            buttonGroup = true;
        }
        if (b == single) {
            buttonSingle = true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

