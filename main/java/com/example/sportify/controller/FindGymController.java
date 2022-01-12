package com.example.sportify.controller;

import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class FindGymController extends Controller{

    /** Reference to the map pane*/
    @FXML
    private Pane Map;

    /** The constructor.*/
    public FindGymController() {
        this.type = ControllerType.FIND_GYM;
    }

    /** The action of back button*/
    @FXML
    private void skipAction(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    /** Set the map*/
    public void setProjection(Projection projection){
        try {
            // Load find map overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("Map.fxml")));
            AnchorPane paneMap = loaderGym.load();

            // Set menu overview into the top of root layout.
            this.Map.getChildren().add(paneMap);

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
}

