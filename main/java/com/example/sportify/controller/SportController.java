package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportController extends Controller{

    /** All the label of the interface*/
    @FXML
    private Label sport_name;
    @FXML
    private Label sport_description;
    @FXML
    private Label sportName;

    /** The constructor.*/
    public SportController(){
        this.type = ControllerType.SPORT;
    }

    /** The action of the buttons*/
    @FXML
    private void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
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

    /** It's called to load the sport description from DB*/
    public void loadDescriptionFromDB(String sport){
        DAO obj_DAO = mainApp.getDAO();
        ResultSet rs = obj_DAO.Check_Data("SELECT * FROM sport WHERE '"+ sport + "' = sport.name");
        String description  = null;
        try {
            while(rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadingSport(sport, description);
    }

    /** It's called to load the sport name from DB*/
    public void loadingSportName(String sport) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/Sport.fxml"));
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

    /** It's called to load the sport from DB*/
    public void loadingSport(String sport, String Description) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportInfo.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.setMenu(this.menu);
            controller.sport_name.setText(sport);
            controller.sport_description.setText(Description);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        //TODO
    }
}
