package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SportGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
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
        List<String> list = objDAO.checkData("SELECT * FROM sport WHERE '"+ sport + "' = sport.name", "description");
        String rs = list.get(list.size() - 1);
        this.loadingSport(sport, rs);
    }

    /** It's called to load the sport name from DB*/
    public void loadingSportName(String sport) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            if(this.getMainApp().isNotMobile()) {
                loaderSport.setLocation(MainApp.class.getResource("DesktopView/Sport.fxml"));
            }else{
                loaderSport.setLocation(MainApp.class.getResource("SmartphoneView/SportPhone3.fxml"));
            }
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
    public void loadingSport(String sport, String description) {
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
            sportGraphicController.setSportDescription(description);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    /** Is called to set graphic  controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        //Do nothing because it doesn't need to, just override operation
    }
}
