package com.example.sportify.controller;

import com.example.sportify.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;

public class SportQuizController extends Controller {

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
    private Button endQuiz;
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

    public static boolean buttonAge1 = false;
    public static boolean buttonAge2 = false;
    public static boolean buttonAge3 = false;
    public static boolean buttonAge4 = false;
    public static boolean buttonIndoor = false;
    public static boolean buttonOutdoor = false;
    public static boolean buttonGroup = false;
    public static boolean buttonSingle = false;

    public SportQuizController(){
        this.type = ControllerType.SPORT_QUIZ;
    }

    @FXML
    private void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    private void sportQuiz(){
        this.mainApp.setUser(this.user);
        this.mainApp.showSportQuizOverview(this.menu);
    }

    private void sportQuizEnv(){
        this.mainApp.setUser(this.user);
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
            controller.setMenu(this.menu);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sportQuizType(){
        this.mainApp.setUser(this.user);
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
            controller.setMenu(this.menu);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
    public void takeQuiz(ActionEvent event) {
        Button b = (Button) event.getSource();
        if (b == age1) {
            buttonAge1 = true;
            sportQuizEnv();
        } else if (b == age2) {
            buttonAge2 = true;
            sportQuizEnv();
        } else if (b == age3) {
            buttonAge3 = true;
            sportQuizEnv();
        } else if (b == age4) {
            buttonAge4 = true;
            sportQuizEnv();
        } else if (b == indoor) {
            buttonIndoor = true;
            sportQuizType();
        } else if (b == outdoor) {
            buttonOutdoor = true;
            sportQuizType();
        } else if (b == group) {
            buttonGroup = true;
            quizLogic();
        } else if (b == single) {
            buttonSingle = true;
            quizLogic();
        } else if (b == endQuiz || b == nextQuiz || b == nextQuizEnv) {
            warning();
        }
    }

    public void warning(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You need to make a choice!");
    }

    @FXML
    private void quizLogic(){
        if((buttonAge1 && buttonIndoor && buttonGroup) || (buttonAge2 && buttonIndoor && buttonGroup)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Volleyball");
        }

        if((buttonAge1 && buttonIndoor && buttonSingle) || (buttonAge2 && buttonIndoor && buttonSingle) || (buttonAge3 && buttonIndoor && buttonSingle) || (buttonAge4 && buttonIndoor && buttonSingle)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Swimming");
        }
        if((buttonAge1 && buttonOutdoor && buttonGroup) || (buttonAge2 && buttonOutdoor && buttonGroup)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Football");
        }
        if(buttonAge1 && buttonOutdoor && buttonSingle) {
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Athletics");
        }
        if((buttonAge2 && buttonOutdoor && buttonSingle) || (buttonAge3 && buttonOutdoor && buttonSingle)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Tennis");
        }

        if((buttonAge3 && buttonIndoor && buttonGroup) || (buttonAge4 && buttonIndoor && buttonGroup)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Dance");
        }
        if((buttonAge3 && buttonOutdoor && buttonGroup) || (buttonAge4 && buttonOutdoor && buttonGroup)){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Trekking");
        }
        if(buttonAge4 && buttonOutdoor && buttonSingle){
            SportController sport = new SportController();
            sport.setMainApp(this.mainApp);
            sport.setUser(this.user);
            sport.setMenu(this.menu);
            sport.loadingSportName("Golf");
        }
    }
}

