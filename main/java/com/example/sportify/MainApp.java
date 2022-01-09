package com.example.sportify;

import com.example.sportify.controller.*;
import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Submit submit;
    private User user = null;
    private String[] search_cache;
    private final DAO dao = new DAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = obj_DB_Connection.get_connection();
        this.dao.setConnection(connection);

        this.submit = new Submit(this);

        this.primaryStage = primaryStage;

        // Set the application.
        this.primaryStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("Images/Sportify icon.png"))));

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
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * Is called to set user.
     */
    public void setUser(User user) {
        this.user = user;
        if(user != null) {
            this.user.setMainApp(this);
        }
    }

    /**
     * Is called to set search_cache.
     */
    public void setSearchCache(String[] search) {
        this.search_cache = search;
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows menu overview inside the root layout.
     */
    private MenuController Menu() {
        MenuController controllerB = null;
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            controllerB = loaderMenu.getController();
            controllerB.setMainApp(this);
            controllerB.setUser(this.user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return controllerB;
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
            Pane homeOverview = loader.load();

            // Set home overview into the center of root layout.
            rootLayout.setCenter(homeOverview);
            rootLayout.setTop(null);

            // Give the controller access to the main app.
            HomeController controller = loader.getController();
            controller.setUser(this.user);
            controller.setMainApp(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows home overview inside the root layout.
     */
    public void showOAuthAuthenticator(WebView root, String name) {
        this.primaryStage.setTitle("Sportify - " + name);

        // Set OAuth overview into the center of root layout.
        rootLayout.setCenter(root);
        rootLayout.setTop(null);

    }

    /**
     * Shows login overview inside the root layout.
     */
    public void showLoginOverview() {
        try {
            this.primaryStage.setTitle("Sportify - Login");

            MenuController menuController = Menu();
            menuController.setLogin();

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
            controller.setUser(this.user);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows sport quiz overview inside the root layout.
     */
    public void showSportQuizOverview(MenuController menu) {
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
            controller.setUser(this.user);
            controller.setMenu(menu);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows find gym overview inside the root layout.
     */
    public void showFindGymOverview(MenuController menu) {
        try {
            this.getPrimaryStage().setTitle("Sportify - Find Gym");
            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(MainApp.class.getResource("FindGym.fxml"));
            Pane pane = loaderGym.load();

            // Set find gym overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            FindGymController controller = loaderGym.getController();
            controller.setMainApp(this);
            controller.setSearchCache(this.search_cache);
            controller.setMenu(menu);
            Projection projection = getParameters().getUnnamed().contains("wgs84")
                    ? Projection.WGS_84 : Projection.WEB_MERCATOR;
            controller.setProjection(projection);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows sign up overview inside the root layout.
     */
    public void showSignUpOverview() {
        try {
            this.getPrimaryStage().setTitle("Sportify - Sign Up");
            // Load sign up overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("SignUp.fxml"));
            Pane pane = loaderSignUp.load();

            // Set sign up overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpController controller = loaderSignUp.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    /**
     * Returns the dao.
     */
    public DAO getDAO() {
        return this.dao;
    }

    /**
     * Controls the visibility of the Password field
     */
    public void togglevisiblePassword(CheckBox pass_toggle, TextField pass_text, TextField password) {
        if (pass_toggle.isSelected()) {
            pass_text.setText(password.getText());
            pass_text.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(pass_text.getText());
        password.setVisible(true);
        pass_text.setVisible(false);
    }

}
