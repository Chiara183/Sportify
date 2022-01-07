package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GymInfoController implements Initializable {

    // Label
    @FXML
    private Label gym_name;
    @FXML
    private Label gym_description;

    // VBox
    @FXML
    private VBox course;
    @FXML
    private VBox review;

    // String
    private String search_cache;

    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user;

    public GymInfoController(){
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setSearchCache(String search) {
        this.search_cache = search;
    }

    private void setGym(String name){
        this.gym_name.setText(name);
        this.gym_description.setText(
                "Address: " + "" +
                        "\nTelephone: " + "");
        try {
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM gym " +
                            "WHERE gym.name = \"" + name + "\"");
            if (rs.next()) {
                this.gym_description.setText(
                        "Address: " + rs.getString("address") +
                        "\nTelephone: " + rs.getString("phone"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM review " +
                            "WHERE review.gym = \"" + name + "\"");
            while (rs.next()) {
                Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
                Label labelReview = new Label(rs.getString("review"));
                VBox vbox = new VBox(labelTitle, labelReview);
                this.review.getChildren().add(vbox);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if(this.review.getChildren().size()<2){
            Label label = new Label("There are no reviews");
            this.review.getChildren().add(label);
        }
        try {
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM course " +
                            "WHERE course.gym = \"" + name + "\"");
            while (rs.next()) {
                Label label = new Label(rs.getString("sport") + " " + rs.getTime("time").toString());
                this.course.getChildren().add(label);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if(this.course.getChildren().size()<2){
            Label label = new Label("There are no course");
            this.course.getChildren().add(label);
        }
    }

    @FXML
    private void findGym(){
        this.mainApp.setUser(this.user);
        this.mainApp.setSearchCache(this.search_cache);
        this.mainApp.showFindGymOverview();
    }


    public void loadingGymName(String name) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - " + name);
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(mainApp.getClass().getResource("GymInfo.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            GymInfoController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.setGym(name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
