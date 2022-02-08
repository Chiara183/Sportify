package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SportQuizGraphicController;
import com.example.sportify.controller.graphicPhone.SportQuizPhoneGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SportQuizController extends Controller {

    /** The variable that identify the user choice*/
    private  static boolean buttonAge1 = false;
    private  static boolean buttonAge2 = false;
    private  static boolean buttonAge3 = false;
    private  static boolean buttonIndoor = false;

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
            sportQuizEnv();
        } else if (Objects.equals(b, "indoor")) {
            synchronized (lockObj) {
            buttonIndoor = true;}
            sportQuizType();
        } else if (Objects.equals(b, "outdoor")) {
           sportQuizType();
        } else if (Objects.equals(b, "group")) {
            groupAction();
        } else if (Objects.equals(b, "single")) {
            singleAction();
        } else if (Objects.equals(b, "endQuiz") || Objects.equals(b, "nextQuiz") || Objects.equals(b, "nextQuizEnv")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You need to make a choice first!\n Press a button or back");
            alert.showAndWait();
        }else if(Objects.equals(b, "invalid input")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input, please retry!");
            alert.showAndWait();
        }
    }

    private void groupAction(){
        if(buttonIndoor){
            groupIndoorAction();
        }else{
            groupOutdoorAction();
        }
    }

    private void singleAction(){
        if(buttonIndoor){
            singleIndoorAction();
        }else{
            singleOutdoorAction();
        }
    }
    private void groupIndoorAction(){
        if(buttonAge1 || buttonAge2){
            setSport("Volleyball");
        }else{
            setSport("Dance");
        }
    }

    private void singleIndoorAction(){
        setSport("Swimming");
    }

    private void groupOutdoorAction(){
        if(buttonAge1 || buttonAge2){
            setSport("Football");
        }
        else{
            setSport("Trekking");
        }
    }

    private void singleOutdoorAction(){
        if(buttonAge1){
            setSport("Athletics");
        }
        else if(buttonAge2 || buttonAge3){
            setSport("Tennis");
        }else{
            setSport("Golf");
        }
    }

    private void setSport(String nameOfSport){
        SportController sport = new SportController();
        sport.setMainApp(this.mainApp);
        sport.setUser(this.user);
        sport.setMenu(this.menu);
        sport.loadingSportName(nameOfSport);
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
        if(getMainApp().isNotMobile()) {
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuizEnv.fxml"));
            this.createController(loaderSport);
        }else{
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuizEnvPhone1.fxml"));
            this.createController(loaderSport);
        }
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
            SportQuizController controller = new SportQuizController();
            if(getMainApp().isNotMobile()) {
                SportQuizGraphicController graphicController = loaderSport.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
            }else{
                SportQuizPhoneGraphicController graphicController = loaderSport.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
            }
            controller.setUser(this.user);
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

