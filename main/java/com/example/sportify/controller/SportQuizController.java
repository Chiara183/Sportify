package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SportQuizGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SportQuizController extends Controller {

    /** The variable that identify the user choice*/
    private  static boolean buttonAge1 = false;
    private  static boolean buttonAge2 = false;
    private  static boolean buttonAge3 = false;
    private  static boolean buttonAge4 = false;
    private  static boolean buttonIndoor = false;
    private  static boolean buttonOutdoor = false;
    private  static boolean buttonGroup = false;
    private  static boolean buttonSingle = false;

    private final Object lockObj = new Object();

    /** The constructor.*/
    public SportQuizController(){
        this.type = ControllerType.SPORT_QUIZ;
    }

    public void takeQuiz(String b) {
        if (Objects.equals(b, "age1")) {
            synchronized (lockObj) {
            buttonAge1 = true;}
            sportQuizEnv();
        } else if (Objects.equals(b, "age2")) {
            synchronized (lockObj) {
            buttonAge2 = true;}
            sportQuizEnv();
        } else if (Objects.equals(b, "age3")) {
            synchronized (lockObj) {
            buttonAge3 = true;}
            sportQuizEnv();
        } else if (Objects.equals(b, "age4")) {
            synchronized (lockObj) {
            buttonAge4 = true;}
            sportQuizEnv();
        } else if (Objects.equals(b, "indoor")) {
            synchronized (lockObj) {
            buttonIndoor = true;}
            sportQuizType();
        } else if (Objects.equals(b, "outdoor")) {
            synchronized (lockObj) {
            buttonOutdoor = true;}
           sportQuizType();
        } else if (Objects.equals(b, "group")) {
            synchronized (lockObj) {
            buttonGroup = true;}
            quizLogic();
        } else if (Objects.equals(b, "single")) {
            synchronized (lockObj) {
            buttonSingle = true;}
            quizLogic();
        } else if (Objects.equals(b, "endQuiz") || Objects.equals(b, "nextQuiz") || Objects.equals(b, "nextQuizEnv")) {
            warning();
        }
    }

    /** The logic of the quiz*/
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

    /** It's called to give the warning to user*/
    private void warning(){
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "You need to make a choice!");
    }

    /** It's called to load home overview*/
    public void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    /** It's called to load sport quiz overview*/
    public void sportQuiz(){
        this.mainApp.setUser(this.user);
        this.mainApp.showSportQuizOverview(this.menu);
    }

    /** It's called to load sport quiz env overview*/
    public void sportQuizEnv(){
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        // Load sport quiz overview.
        FXMLLoader loaderSport = new FXMLLoader();
        loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuizEnv.fxml"));
        this.createController(loaderSport);
    }

    /** It's called to load sport quiz type overview*/
    private void sportQuizType(){
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        // Load sport quiz overview.
        FXMLLoader loaderSport = new FXMLLoader();
        loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuizType.fxml"));
        this.createController(loaderSport);
    }

    /** Is called to create controllers*/
    private void createController(FXMLLoader loaderSport){
        try {
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizGraphicController graphicController = loaderSport.getController();
            SportQuizController controller = new SportQuizController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setMenu(this.menu);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(SportQuizController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        //Do nothing, but it has to exist given that this class extends Controller
    }
}

