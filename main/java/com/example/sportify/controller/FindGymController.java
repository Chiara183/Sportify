package com.example.sportify.controller;

import com.example.sportify.MainApp;
import javafx.fxml.*;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FindGymController implements Initializable{

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public FindGymController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void skipAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Home");
        try {
            // Load person overview.
            FXMLLoader loaderHome = new FXMLLoader();
            loaderHome.setLocation(MainApp.class.getResource("Home.fxml"));
            Pane pane = loaderHome.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);
            this.mainApp.getPrimaryPane().setTop(null);

            // Give the controller access to the main app.
            HomeController controller = loaderHome.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

