package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FindGymController implements Initializable{

    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user;

    @FXML
    private Pane Map;

    /**
     * The constructor.
     */
    public FindGymController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called to set user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void skipAction(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

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
            controller.initMapAndControls(projection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

