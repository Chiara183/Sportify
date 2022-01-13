package com.example.sportify.controller;

import com.example.sportify.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class HomeController extends Controller {

    /** All the button of the interface*/
    @FXML
    private Button signIn;
    @FXML
    private Button gymInfo;

    /** The constructor.*/
    public HomeController() {
        this.type = ControllerType.HOME;
    }

    /** Is called to set user.*/
    @Override
    public void setUser(User user) {
        this.user = user;
        signIn.setVisible(this.user == null);
        if(this.user!= null && this.user.getRole().equals("gym")){
            gymInfo.setVisible(true);
            gymInfo.setPrefWidth(141);
            signIn.setPrefWidth(0);
        } else {
            gymInfo.setVisible(false);
            gymInfo.setPrefWidth(0);
            signIn.setPrefWidth(141);
        }
    }

    /** It's called to create and ad a new menu in the window*/
    private MenuController Menu() {
        MenuController controllerB = null;
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.mainApp.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            controllerB = loaderMenu.getController();
            controllerB.setMainApp(this.mainApp);
            controllerB.setUser(this.user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return controllerB;
    }

    /** The action of the button*/
    @FXML
    private void loadGymInfo(){
        MenuController menu = this.Menu();
        menu.setGymInfo(this.user.getGymName());
        GymInfoController gym = new GymInfoController();
        gym.setMainApp(this.mainApp);
        gym.setUser(this.user);
        gym.setMenu(menu);
        gym.setSearchCache(this.mainApp.getSearchCache());
        gym.loadingGymName(user.getGymName());
    }
    @FXML
    private void sportQuizAction(){
        this.mainApp.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        try {
            MenuController menuController = Menu();
            menuController.setSportQuiz();

            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/SportQuiz.fxml")));
            Pane paneSport = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneSport);

            // Give the controller access to the main app.
            SportQuizController controllerSport = loaderSport.getController();
            controllerSport.setMainApp(this.mainApp);
            controllerSport.setUser(this.user);
            controllerSport.setMenu(menuController);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
            loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/FindGym.fxml")));
            Pane paneGym = loaderGym.load();

            // Set find gym overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneGym);

            // Give the controller access to the main app.
            FindGymController controllerGym = loaderGym.getController();
            controllerGym.setMainApp(this.mainApp);
            controllerGym.setUser(this.user);
            controllerGym.setMenu(menuController);
            controllerGym.setProjection(mainApp.getProjection());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
            loaderLogin.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Login.fxml")));
            Pane paneLogin = loaderLogin.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(paneLogin);

            // Give the controller access to the main app.
            LoginController controllerLogin = loaderLogin.getController();
            controllerLogin.setMainApp(this.mainApp);
            controllerLogin.setUser(this.user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}