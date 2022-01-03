package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SportController implements Initializable{
    @FXML
    private Button infoVolley;
    @FXML
    private Button backSport;
    @FXML
    private Button infoTrekking;
    @FXML
    private Button tennisInfo;
    @FXML
    private Button infoSwim;
    @FXML
    private Button infoGolf;
    @FXML
    private Button infoFootball;
    @FXML
    private Button infoDance;
    @FXML
    private Button infoAthletics;




    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user;

    public SportController(){}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }


    public void loading(String sportName) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource(sportName + ".fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void getInfo(ActionEvent event){
        Button b = (Button) event.getSource();
        if(b == infoVolley){
            this.loading("VolleyInfo");
        }
        if(b == infoTrekking){
            this.loading("TrekkingInfo");
        }
        if(b == tennisInfo){
            this.loading("TennisInfo");
        }
        if(b == infoSwim){
            this.loading("SwimmingInfo");
        }
        if(b == infoGolf){
            this.loading("GolfInfo");
        }
        if(b == infoFootball){
            this.loading("FootballInfo");
        }
        if(b == infoDance){
            this.loading("DanceInfo");
        }
        if(b == infoAthletics){
            this.loading("AthleticsInfo");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
