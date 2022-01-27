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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SportController extends Controller{

    /** The constructor.*/
    public SportController(){
        this.type = ControllerType.SPORT;
    }

    /** It's called to load the sport description from DB*/
    public void loadDescriptionFromDB(String sport){
        DAO objDAO = mainApp.getDAO();
        String rs = objDAO.checkData("SELECT * FROM sport WHERE '"+ sport + "' = sport.name", "description");
        /*String description  = null;
        try {
            while(rs.next()) {
                description = rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        this.loadingSport(sport, rs);
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
            SportGraphicController sportGraphicController = loaderSport.getController();
            SportController controller = new SportController();
            sportGraphicController.setController(controller);
            controller.setGraphicController(sportGraphicController);
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            sportGraphicController.setSportName(sport);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** It's called to load the sport from DB*/
    public void loadingSport(String sport, String descript) {
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
            SportGraphicController sportGraphicController = loaderSport.getController();
            SportController controller = new SportController();
            controller.setGraphicController(sportGraphicController);
            sportGraphicController.setController(controller);
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.setMenu(this.menu);
            sportGraphicController.setSportName(sport);
            sportGraphicController.setSportDescription(descript);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    /** Is called to set graphic  controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        /** Reference to graphic controller*/
    }
}
