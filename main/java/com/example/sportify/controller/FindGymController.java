package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import com.sothawo.mapjfx.MapView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FindGymController implements Initializable{

    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user;

    // MapView
    @FXML
    private MapView mapView;

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
}

