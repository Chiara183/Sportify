package com.example.sportify.controller.graphic;

import com.example.sportify.auth.OAuthGoogleAuthenticator;
import com.example.sportify.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeGraphicController implements GraphicController{

    public ComboBox<String> comboActivity;

    /** Reference to controller*/
    private HomeController controller;

    /** All the button of the interface*/
    @FXML
    private Button signIn;
    @FXML
    private Button gymInfo;

    /** The action of comboBox*/
    @FXML
    private void comboAction(){
        Object selectedItem = comboActivity.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        switch (choice) {
            case "Take sport quiz" -> this.controller.getMenu().getGraphicController().sportQuizAction();
            case "Login" -> this.controller.getMenu().getGraphicController().signAction();
            case "Login with Google" -> {
                String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
                String gRedir = "https://localhost:8080/oauth2";
                String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
                String gSecret = "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";
                OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
                auth.startLogin(controller.getMainApp());
            }
            case "Find gym" -> this.controller.getMenu().getGraphicController().setFindGym();
        }
    }

    /** The action of the button*/
    @FXML
    private void loadGymInfo(){
        MenuController menu = controller.Menu();
        menu.setGymInfo(controller.getUser().getGymName());
        GymInfoGraphicController graphicController = new GymInfoGraphicController();
        GymInfoController gym = new GymInfoController();
        graphicController.setController(gym);
        gym.setGraphicController(graphicController);
        gym.setMainApp(controller.getMainApp());
        gym.setUser(controller.getUser());
        gym.setMenu(menu);
        gym.setSearchCache(controller.getMainApp().getSearchCache());
        gym.loadingGymName(controller.getUser().getGymName());
    }
    @FXML
    private void sportQuizAction(){
        controller.getMainApp().getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            MenuController menuController = controller.Menu();
            menuController.setSportQuiz();

            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(Objects.requireNonNull(controller.getMainApp().getClass().getResource("DesktopView/SportQuiz.fxml")));
            Pane paneSport = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            controller.getMainApp().getPrimaryPane().setCenter(paneSport);

            // Give the controller access to the main app.
            SportQuizGraphicController graphicController = loaderSport.getController();
            SportQuizController controllerSport = new SportQuizController();
            controllerSport.setGraphicController(graphicController);
            graphicController.setController(controllerSport);
            controllerSport.setMainApp(controller.getMainApp());
            controllerSport.setUser(controller.getUser());
            controllerSport.setMenu(menuController);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(HomeGraphicController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    @FXML
    private void findGymAction(){
        controller.getMainApp().getPrimaryStage().setTitle("Sportify - Find Gym");
        try {
            MenuController menuController = controller.Menu();
            menuController.setFindGym();

            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(Objects.requireNonNull(controller.getMainApp().getClass().getResource("DesktopView/FindGym.fxml")));
            Pane paneGym = loaderGym.load();

            // Set find gym overview into the center of root layout.
            controller.getMainApp().getPrimaryPane().setCenter(paneGym);

            // Give the controller access to the main app.
            FindGymGraphicController graphicController = loaderGym.getController();
            FindGymController controllerGym = new FindGymController();
            controllerGym.setGraphicController(graphicController);
            graphicController.setController(controllerGym);
            controllerGym.setMainApp(controller.getMainApp());
            controllerGym.setUser(controller.getUser());
            controllerGym.setMenu(menuController);
            controllerGym.setProjection(controller.getMainApp().getProjection());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(HomeGraphicController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    @FXML
    private void signLoginAction() {
        controller.getMainApp().getPrimaryStage().setTitle("Sportify - Login");
        try {
            MenuController menuController = controller.Menu();
            menuController.setLogin();

            // Load login overview.
            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(Objects.requireNonNull(controller.getMainApp().getClass().getResource("DesktopView/Login.fxml")));
            Pane paneLogin = loaderLogin.load();

            // Set login overview into the center of root layout.
            controller.getMainApp().getPrimaryPane().setCenter(paneLogin);

            // Give the controller access to the main app.
            LoginGraphicController graphicController = loaderLogin.getController();
            LoginController controllerLogin = new LoginController();
            controllerLogin.setGraphicController(graphicController);
            graphicController.setController(controllerLogin);
            controllerLogin.setMainApp(controller.getMainApp());
            controllerLogin.setUser(controller.getUser());
            controllerLogin.setMenu(menuController);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(HomeGraphicController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to get signIn button*/
    public Button getSignIn(){return this.signIn;}

    /** Is called to get gymInfo button*/
    public Button getGymInfo(){return this.gymInfo;}

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (HomeController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
