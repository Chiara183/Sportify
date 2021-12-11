package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user = null;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public HomeController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called to set user.
     *
     */
    public void setUser(User user) {
        this.user = user;
    }

    private MenuController Menu() {
        MenuController controllerB = null;
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.mainApp.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            controllerB = loaderMenu.getController();
            controllerB.setMainApp(this.mainApp);
            if (this.user!= null){
                try {
                    // Load user overview.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("UserIcon.fxml"));
                    Pane userOverview = loader.load();

                    // Set home overview into the center of root layout.
                    paneMenu.getChildren().add(userOverview);

                    // Give the controller access to the main app.
                    UserController controller = loader.getController();
                    controller.setUser(this.user);
                    controller.setMainApp(this.mainApp);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controllerB;
    }

    @FXML
    private void sportQuizAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            MenuController menuController = Menu();
            menuController.setSportQuiz();

            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("SportQuiz.fxml")));
            Pane paneSport = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneSport);

            // Give the controller access to the main app.
            SportQuizController controllerSport = loaderSport.getController();
            controllerSport.setMainApp(this.mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void findGymAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Find Gym");
        try {
            MenuController menuController = Menu();
            menuController.setFindGym();

            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("FindGym.fxml")));
            Pane paneGym = loaderGym.load();

            // Set find gym overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneGym);

            // Give the controller access to the main app.
            FindGymController controllerGym = loaderGym.getController();
            controllerGym.setMainApp(this.mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void signLoginAction() {
        this.mainApp.getPrimaryStage().setTitle("Sportify - Login");
        try {
            MenuController menuController = Menu();
            menuController.setLogin();

            // Load login overview.
            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("Login.fxml")));
            Pane paneLogin = loaderLogin.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneLogin);

            // Give the controller access to the main app.
            LoginController controllerLogin = loaderLogin.getController();
            controllerLogin.setMainApp(this.mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}