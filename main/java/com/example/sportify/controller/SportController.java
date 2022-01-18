package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SportGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportController extends Controller{

    /** Reference to graphic controller*/
    private SportGraphicController graphicController;

    /** The constructor.*/
    public SportController(){
        this.type = ControllerType.SPORT;
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
            SportGraphicController graphicController = loaderSport.getController();
            SportController controller = new SportController();
            graphicController.setController(controller);
            controller.setGraphicController(graphicController);
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            graphicController.setSportName(sport);
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
            SportGraphicController graphicController = loaderSport.getController();
            SportController controller = new SportController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.setMenu(this.menu);
            graphicController.setSportName(sport);
            graphicController.setSportDescription(Description);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Is called to set graphic  controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SportGraphicController) graphicController;
    }
}
