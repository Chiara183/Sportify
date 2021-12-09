package com.example.sportify;

import com.example.sportify.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        // Set the application.
        this.primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/Sportify icon.png"))));

        initRootLayout();

        showHomeOverview();
    }

    /**
     * Constructor
     */
    public MainApp(){
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 830, 550);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows home overview inside the root layout.
     */
    public void showHomeOverview() {
        try {
            this.primaryStage.setTitle("Sportify - Home");
            // Load home overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Home.fxml"));
            Pane personOverview = loader.load();

            // Set home overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            HomeController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the primary pane.
     */
    public BorderPane getPrimaryPane() {
        return rootLayout;
    }

}
