package com.example.sportify;

import com.example.sportify.controller.*;
import com.example.sportify.controller.graphic.*;
import com.example.sportify.user.User;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp{

    /** The variable of all application*/
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Submit submit;
    private User user = null;
    private String[] searchCache;
    private DAO dao = DAO.getSingletonInstance();
    private boolean externalLogin = false;
    private MenuController menu;
    private Projection projection;

    /** Set method*/
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }
    public void setProjection(Projection projection) {
        this.projection = projection;
    }
    public void setUser(User user) {
        this.user = user;
        if(user != null) {
            this.user.setMainApp(this);
        }
    }
    public void setMenu(MenuController menu) {
        this.menu = menu;
    }
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    public void setSearchCache(String[] search) {
        this.searchCache = search;
    }
    public void setExternalLogin(boolean login) {
        this.externalLogin = login;
    }

    /** Get method*/
    public String[] getSearchCache() {
        return this.searchCache;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public DAO getDAO() {
        return this.dao;
    }
    public BorderPane getPrimaryPane() {
        return rootLayout;
    }
    public Projection getProjection() {
        return projection;
    }

    /** Initializes the root layout.*/
    public void initRootLayout() {
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        *int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        System.out.println("Dimension of screen is: " + width + "x" + height);*/
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("DesktopView/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 830, 550);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows menu overview inside the root layout.*/
    public MenuController menu() {
        MenuController controller = new MenuController();
        MenuGraphicController graphicController;
        controller.setMainApp(this);
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(getClass().getResource("DesktopView/Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            graphicController = loaderMenu.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
        return controller;
    }

    /** Shows home overview inside the root layout.*/
    public void showHomeOverview() {
        try {
            this.primaryStage.setTitle("Sportify - Home");
            // Load home overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("DesktopView/Home.fxml"));
            Pane homeOverview = loader.load();

            // Set home overview into the center of root layout.
            rootLayout.setCenter(homeOverview);
            rootLayout.setTop(null);

            // Give the controller access to the main app.
            HomeGraphicController graphicController = loader.getController();
            HomeController controller = new HomeController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
            controller.setMainApp(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows home overview inside the root layout.*/
    public void showOAuthAuthenticator(WebView root, String name) {
        this.primaryStage.setTitle("Sportify - " + name);

        // Set OAuth overview into the center of root layout.
        rootLayout.setCenter(root);
        rootLayout.setTop(null);

    }

    /** Shows login overview inside the root layout.*/
    public void showLoginOverview() {
        LoginController controller = new LoginController();
        LoginGraphicController graphicController;
        try {
            this.primaryStage.setTitle("Sportify - Login");

            MenuController menuController = menu();
            menuController.setLogin();

            // Load login overview.
            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(MainApp.class.getResource("DesktopView/Login.fxml"));
            Pane pane = loaderLogin.load();

            // Setting controller
            graphicController = loaderLogin.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setSubmit(this.submit);
            controller.setUser(this.user);
            controller.setExternal(this.externalLogin);

            if(!externalLogin) {
                // Set login overview into the center of root layout.
                this.getPrimaryPane().setCenter(pane);
            } else {
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Sportify - Login");
                dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/Sportify icon.png"))));

                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("DesktopView/RootLayout.fxml"));
                BorderPane root = loader.load();

                // SetWindowModal
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.primaryStage);
                Scene scene = new Scene(root, 830, 550);
                root.setCenter(pane);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);

                // Set the person into the controller.
                controller.setMenu(this.menu);
                controller.setStage(dialogStage);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            }

        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows sport quiz overview inside the root layout.*/
    public void showSportQuizOverview(MenuController menu) {
        try {
            this.getPrimaryStage().setTitle("Sportify - Sport Quiz");
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(MainApp.class.getResource("DesktopView/SportQuiz.fxml"));
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SportQuizGraphicController graphicController = loaderSport.getController();
            SportQuizController controller = new SportQuizController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setUser(this.user);
            controller.setMenu(menu);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows find gym overview inside the root layout.*/
    public void showFindGymOverview(MenuController menu) {
        try {
            this.getPrimaryStage().setTitle("Sportify - Find Gym");
            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            loaderGym.setLocation(MainApp.class.getResource("DesktopView/FindGym.fxml"));
            Pane pane = loaderGym.load();

            // Set find gym overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            FindGymGraphicController graphicController = loaderGym.getController();
            FindGymController controller = new FindGymController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setSearchCache(this.searchCache);
            controller.setMenu(menu);
            controller.setProjection(this.projection);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows sign up overview inside the root layout.*/
    public void showSignUpOverview() {
        try {
            this.getPrimaryStage().setTitle("Sportify - Sign Up");
            // Load sign up overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("DesktopView/SignUp.fxml"));
            Pane pane = loaderSignUp.load();

            // Set sign up overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGraphicController graphicController = loaderSignUp.getController();
            SignUpController controller = new SignUpController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to create account hashmap*/
    public Map<String, String> createAccount(String username, String password, String firstName, String lastName, String email, String date){
        HashMap<String, String> account = new HashMap<>();
        account.put("username", username);
        account.put("password", password);
        account.put("firstName", firstName);
        account.put("lastName", lastName);
        account.put("email", email);
        account.put("birthday", date);
        return account;
    }

    /** Controls the visibility of the Password field*/
    public void togglevisiblePassword(CheckBox passToggle, TextField passText, TextField password) {
        if (passToggle.isSelected()) {
            passText.setText(password.getText());
            passText.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(passText.getText());
        password.setVisible(true);
        passText.setVisible(false);
    }

}
