package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.MenuGraphicController;
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
        } else if (Objects.equals(b, "alone")) {
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
        sport.setQuiz(this);
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
            loaderSport.setLocation(MainApp.class.getResource("SmartphoneView/SportQuizEnvPhone1.fxml"));
            FXMLLoader loaderTopScreen = new FXMLLoader();
            loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen1.fxml"));
            setTopMenu(loaderTopScreen);
            menu.setView(ControllerType.SPORT_QUIZ_ENV);
            MenuGraphicController graphicMenuController = loaderTopScreen.getController();
            graphicMenuController.setController(menu);
            this.createController(loaderSport);
        }
    }

    /** It's called to load sport quiz type overview*/
    public void sportQuizType(){
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        // Load sport quiz overview.
        if(getMainApp().isNotMobile()) {
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuizType.fxml"));
            this.createController(loaderSport);
        }else{
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SmartphoneView/SportQuizTypePhone4.fxml"));
            FXMLLoader loaderTopScreen = new FXMLLoader();
            loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen4.fxml"));
            setTopMenu(loaderTopScreen);
            menu.setView(ControllerType.SPORT_QUIZ_TYPE);
            SportQuizPhoneGraphicController graphicMenuController = loaderTopScreen.getController();
            graphicMenuController.setController(this);
            SportQuizPhoneGraphicController graphicQuizController = this.createController(loaderSport);
            graphicMenuController.setQuiz(graphicQuizController);
        }
    }

    /** Is called to create controllers*/
    private SportQuizPhoneGraphicController createController(FXMLLoader loaderSport){
        SportQuizPhoneGraphicController graphicPhoneController = null;
        try {
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            if(getMainApp().isNotMobile()) {
                SportQuizGraphicController graphicController = loaderSport.getController();
                this.setGraphicController(graphicController);
                graphicController.setController(this);
            }else{
                graphicPhoneController = loaderSport.getController();
                this.setGraphicController(graphicPhoneController);
                graphicPhoneController.setController(this);
                menu.setInstance(graphicPhoneController);
            }
            this.setUser(this.user);
            this.setMainApp(this.mainApp);
            this.setMenu(this.menu);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(SportQuizController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return graphicPhoneController;
    }

    /** Is called to set top menu*/
    private void setTopMenu(FXMLLoader loaderTopScreen){
        Pane paneTopScreen = null;
        try {
            paneTopScreen = loaderTopScreen.load();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        this.mainApp.getPrimaryPane().setTop(paneTopScreen);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        //Do nothing, but it has to exist given that this class extends Controller
    }
}

