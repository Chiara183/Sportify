package com.example.sportify;

import com.example.sportify.controller.HomeController;
import com.example.sportify.controller.LoginController;
import com.example.sportify.controller.SportQuizController;
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
    private Submit submit = new Submit();
    private User user = null;

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
     * Is called to give a reference back to submit.
     *
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * Is called to set user.
     *
     */
    public void setUser(User user) {
        this.user = user;
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
            controller.setUser(this.user);
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows login overview inside the root layout.
     */
    public void showLoginOverview() {
        try {
            this.primaryStage.setTitle("Sportify - Login");
            // Load login overview.
            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(MainApp.class.getResource("Login.fxml"));
            Pane pane = loaderLogin.load();

            // Set login overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            LoginController controller = loaderLogin.getController();
            controller.setMainApp(this);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows sport quiz overview inside the root layout.
     */
    public void showSportQuizOverview() {
        try {
            this.getPrimaryStage().setTitle("Sportify - Sport Quiz");
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("SportQuiz.fxml"));
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizController controller = loaderSport.getController();
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
