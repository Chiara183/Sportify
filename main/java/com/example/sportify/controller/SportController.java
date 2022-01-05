package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SportController implements Initializable{
    @FXML
    private Label sport_name;
    @FXML
    private Label sport_description;
    @FXML
    private Label sportName;




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

    public void loadDescriptionFromDB(String sport){
        DAO obj_DAO = new DAO();
        ResultSet rs = obj_DAO.Check_Data("SELECT * FROM sport WHERE '"+ sport + "' = sport.name");
        String description  = null;
        try {
            while(rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadingSport_Name(sport, description);
    }


    public void loadingSportName(String sport) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("Sport.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.sportName.setText(sport);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadingSport_Name(String sport, String Description) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SportInfo.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.sport_name.setText(sport);
            controller.sport_description.setText(Description);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void getInfo(){
        if(this.sportName.getText().equals("Volleyball") ){
            loadDescriptionFromDB("Volleyball");
        }
        if(this.sportName.getText().equals("Trekking")){
            loadDescriptionFromDB("Trekking");
        }
        if(this.sportName.getText().equals("Tennis")){
            loadDescriptionFromDB("Tennis");
        }
        if(this.sportName.getText().equals("Swimming")){
            loadDescriptionFromDB("Swimming");
        }
        if(this.sportName.getText().equals("Golf")){
            loadDescriptionFromDB("Golf");
        }
        if(this.sportName.getText().equals("Football")){
            loadDescriptionFromDB("Football");
        }
        if(this.sportName.getText().equals("Dance")){
            loadDescriptionFromDB("Dance");
        }
        if(this.sportName.getText().equals("Athletics")){
            loadDescriptionFromDB("Athletics");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
