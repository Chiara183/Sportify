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

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
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

    @FXML
    private void home(){
        this.mainApp.showHomeOverview();
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

        Button b = (Button) event.getSource();
        if (b == age1) {
            buttonAge1 = true;
            sportQuizEnv();
        }
        else if (b == age2) {
            buttonAge2 = true;
            sportQuizEnv();
        }
        else if (b == age3) {
            buttonAge3 = true;
            sportQuizEnv();
        }
        else if (b == age4) {
            buttonAge4 = true;
            sportQuizEnv();
        }
        else if (b == indoor) {
            buttonIndoor = true;
            sportQuizType();
        }
        else if (b == outdoor) {
            buttonOutdoor = true;
            sportQuizType();
        }
        else if (b == group) {
            buttonGroup = true;
            quizLogic();
        }
        else if (b == single) {
            buttonSingle = true;
            quizLogic();
        }
    }

    @FXML
    private void quizLogic(){
        if(buttonAge1 && buttonIndoor && buttonGroup){
            this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
            try {
                // Load test result overview.
                FXMLLoader loaderSport = new FXMLLoader();
                loaderSport.setLocation(MainApp.class.getResource("VolleyBall.fxml"));
                Pane pane = loaderSport.load();

                // Set test result overview into the center of root layout.
                this.mainApp.getPrimaryPane().setCenter(pane);

                // Give the controller access to the main app.
                SportQuizController controller = loaderSport.getController();
                controller.setMainApp(this.mainApp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(buttonAge1 && buttonIndoor && buttonSingle){/* Arti Marziali */};
        if(buttonAge1 && buttonOutdoor && buttonGroup){/* Calcio */};
        if(buttonAge1 && buttonOutdoor && buttonSingle){/* Atletica */ };

        if(buttonAge2 && buttonIndoor && buttonGroup){/* Pallavolo */};
        if(buttonAge2 && buttonIndoor && buttonSingle){/* Nuoto */};
        if(buttonAge2 && buttonOutdoor && buttonGroup){/* Calcio con opzione a 5 */};
        if(buttonAge2 && buttonOutdoor && buttonSingle){/* Tennis */ };

        if(buttonAge3 && buttonIndoor && buttonGroup){/* Ballo */};
        if(buttonAge3 && buttonIndoor && buttonSingle){/* Nuoto */};
        if(buttonAge3 && buttonOutdoor && buttonGroup){/* Trekking */};
        if(buttonAge3 && buttonOutdoor && buttonSingle){/* Tennis */};

        if(buttonAge4 && buttonIndoor && buttonGroup){/* Ballo */};
        if(buttonAge4 && buttonIndoor && buttonSingle){/* Nuoto */};
        if(buttonAge4 && buttonOutdoor && buttonGroup){/* Trekking in group */};
        if(buttonAge4 && buttonOutdoor && buttonSingle){/* Golf */};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

