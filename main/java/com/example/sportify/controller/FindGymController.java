package com.example.sportify.controller;

import com.example.sportify.controller.graphic.FindGymGraphicController;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FindGymController extends Controller{

    /** Reference to graphic controller*/
    private FindGymGraphicController findGymGraphicController;

    /** The constructor.*/
    public FindGymController() {
        this.type = ControllerType.FIND_GYM;
    }

    /** Is called to set graphic controller*/
    public void setGraphicController(FindGymGraphicController graphicController) {
        this.findGymGraphicController = graphicController;
    }

    /** Set the map*/
    public void setProjection(Projection projection){
        try {
            // Load find map overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Map.fxml")));
            AnchorPane paneMap = loaderGym.load();

            // Set menu overview into the top of root layout.
            this.findGymGraphicController.getMap().getChildren().add(paneMap);

            // Give the controller access to the main app.
            MapGraphicController graphicController = loaderGym.getController();
            MapController controller = new MapController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setUser(this.user);
            controller.setMenu(this.menu);
            controller.initMapAndControls(projection);
            controller.setSearchCache(this.searchCache);
            if(searchCache !=null){
                graphicController.searchAction();
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(FindGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.findGymGraphicController = (FindGymGraphicController) graphicController;
    }
}

