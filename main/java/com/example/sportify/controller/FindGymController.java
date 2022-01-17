package com.example.sportify.controller;

import com.example.sportify.controller.graphic.FindGymGraphicController;
import com.example.sportify.controller.graphic.GraphicController;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class FindGymController extends Controller{

    /** Reference to graphic controller*/
    private FindGymGraphicController graphicController;

    /** The constructor.*/
    public FindGymController() {
        this.type = ControllerType.FIND_GYM;
    }

    /** Is called to set graphic controller*/
    public void setGraphicController(FindGymGraphicController graphicController) {
        this.graphicController = graphicController;
    }

    /** Set the map*/
    public void setProjection(Projection projection){
        try {
            // Load find map overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Map.fxml")));
            AnchorPane paneMap = loaderGym.load();

            // Set menu overview into the top of root layout.
            this.graphicController.getMap().getChildren().add(paneMap);

            // Give the controller access to the main app.
            MapController controller = loaderGym.getController();
            controller.setMainApp(this.mainApp);
            controller.setUser(this.user);
            controller.setMenu(this.menu);
            controller.initMapAndControls(projection);
            controller.setSearchCache(this.search_cache);
            if(search_cache!=null){
                controller.searchAction();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (FindGymGraphicController) graphicController;
    }
}

