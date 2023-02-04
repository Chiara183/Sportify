package sportify;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sportify.controller.*;
import sportify.controller.graphic.*;
import sportify.controller.graphic.phone.MenuPhoneGraphicController;
import sportify.controller.graphic.phone.SportQuizPhoneGraphicController;
import sportify.controller.graphic.phone.FindGymPhoneGraphicController;
import sportify.controller.graphic.phone.HomePhoneGraphicController;
import sportify.user.User;
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
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp{

    /* The variable of all application*/
    private Stage primaryStage;
    private Stage secondaryStage;
    private BorderPane rootLayout;
    private BorderPane secondaryRootLayout;
    private Submit submit;
    private User user = null;
    private String[] searchCache;
    private final DAO dao = new DAO();
    private boolean externalLogin = false;
    private MenuController menu;
    private Projection projection;
    private boolean mobile;

    /* Set method*/
    public void setMobile(boolean bool) {
        this.mobile = bool;
    }
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
    public void setSecondaryStage(Stage secondaryStage) {
        this.secondaryStage = secondaryStage;
    }
    public void setSearchCache(String[] search) {
        this.searchCache = search;
    }
    public void setExternalLogin(boolean login) {
        this.externalLogin = login;
    }
    public void setPrimaryPane(BorderPane pane){
        this.rootLayout = pane;
    }
    public void setSecondaryPane(BorderPane secondaryRootLayout) {
        this.secondaryRootLayout = secondaryRootLayout;
    }

    /* Get method*/
    public String[] getSearchCache() {
        return this.searchCache;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public Stage getSecondaryStage() {
        return secondaryStage;
    }
    public MenuController getMenu(){
        return this.menu;
    }
    public User getUser() {
        return user;
    }
    public DAO getDAO() {
        return this.dao;
    }
    public BorderPane getPrimaryPane() {
        return rootLayout;
    }
    public BorderPane getSecondaryPane() {
        return secondaryRootLayout;
    }
    public Submit getSubmit() {
        return submit;
    }
    public Projection getProjection() {
        return projection;
    }
    public boolean isNotMobile() {
        return !mobile;
    }
    public boolean isMobile(){
        return mobile;
    }
    public boolean isExternalLogin(){return externalLogin;}

    /** Initializes the root layout.*/
    public void initRootLayout() {
        String className = MainApp.class.getName();
        String os = System.getProperty("os.name");
        String dim = "The OS of device is: " + os;
        Logger logger = Logger.getLogger(className);
        logger.log(Level.INFO, dim);
        URL url;
        String resource;
        Stage stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            if(isNotMobile()) {
                // Load root layout from fxml file.
                resource = "DesktopView/RootLayout.fxml";
                url = MainApp.class.getResource(resource);
                loader.setLocation(url);
                setPrimaryPane(loader.load());

                // Show the scene containing the root layout.
                Scene scene = new Scene(getPrimaryPane(), 830, 550);
                stage = getPrimaryStage();
                stage.setScene(scene);
                stage.setFullScreen(true);
            } else {
                // Load root layout from fxml file.
                resource = "SmartphoneView/RootLayoutPhone.fxml";
                url = MainApp.class.getResource(resource);
                loader.setLocation(url);
                setPrimaryPane(loader.load());

                // Show the scene containing the root layout.
                Scene scene = new Scene(getPrimaryPane(), 270, 450);
                stage = getPrimaryStage();
                stage.setScene(scene);
                stage.setResizable(false);
            }
            stage = getPrimaryStage();
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows menu overview inside the root layout.*/
    public MenuController menu() {
        MenuController controller = new MenuController();
        URL url;
        String resource;
        MenuController m = getMenu();
        if(isExternalLogin()){
            ControllerType type = m.getView();
            String gym = m.getGym();
            setMenu(controller);
            m = getMenu();
            m.setView(type);
            m.setGym(gym);
        } else {
            setMenu(controller);
        }
        controller.setMainApp(this);
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            BorderPane pane = this.getPrimaryPane();
            if(isNotMobile()) {
                MenuGraphicController graphicController;
                resource = "DesktopView/menu.fxml";
                url = getClass().getResource(resource);
                loaderMenu.setLocation(Objects.requireNonNull(url));
                Pane paneMenu = loaderMenu.load();

                // Set menu overview into the top of root layout.
                pane.setTop(paneMenu);

                // Give the gymEditController access to the main app.
                graphicController = loaderMenu.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
            } else {
                MenuPhoneGraphicController graphicController;
                resource = "SmartphoneView/MenuPhone.fxml";
                url = getClass().getResource(resource);
                loaderMenu.setLocation(Objects.requireNonNull(url));
                Pane paneMenu = loaderMenu.load();

                // Set menu overview into the top of root layout.
                pane.setBottom(paneMenu);
                graphicController = loaderMenu.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
            }
            // Give the gymEditController access to the main app.
            controller.setUser(this.getUser());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MainApp.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
        return controller;
    }

    /** Shows home overview inside the root layout.*/
    public void showHomeOverview() {
        String title;
        String resource;
        URL url;
        String item1 = "";
        String item2 = "";
        String item3 = "";
        String item4 = "";
        String className = MainApp.class.getName();
        BorderPane pane = getPrimaryPane();
        try {
            FXMLLoader loader = new FXMLLoader();
            title = "Sportify - Home";
            Stage stage = this.getPrimaryStage();
            stage.setTitle(title);
            HomeController controller = new HomeController();
            if(isNotMobile()) {
                // Load home overview.
                resource = "DesktopView/Home.fxml";
                url = MainApp.class.getResource(resource);
                loader.setLocation(url);
                item1 = "Take sport quiz";
                item2 = "Find gym";
                item3 = "Login";
                item4 = "Login with Google";
            } else {
                // Load home overview.
                resource = "SmartphoneView/HomePhone.fxml";
                url = MainApp.class.getResource(resource);
                loader.setLocation(url);
                MenuController menuController = menu();
                controller.setMenu(menuController);
            }
            Pane homeOverview = loader.load();

            // Set home overview into the center of root layout.
            pane.setCenter(homeOverview);
            pane.setTop(null);

            // Give the gymEditController access to the main app.
            HomePhoneGraphicController graphicController = loader.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setUser(this.getUser());
            if(isMobile()) {
                ComboBox<String> combo = graphicController.getCombo();
                ObservableList<String> obs = combo.getItems();
                obs.add(item1);
                obs.add(item2);
                obs.add(item3);
                obs.add(item4);
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows home overview inside the root layout.*/
    public void showOAuthAuthenticator(WebView root, String name) {
        String title;
        Stage stage;
        BorderPane pane;
        if(!isExternalLogin()) {
            title = "Sportify - " + name;
            stage = this.getPrimaryStage();
            stage.setTitle(title);

            // Set OAuth overview into the center of root layout.
            pane = getPrimaryPane();
            pane.setCenter(root);
        } else {
            title = "Sportify - " + name;
            stage = getSecondaryStage();
            stage.setTitle(title);

            // Set OAuth overview into the center of root layout.
            pane = getSecondaryPane();
            pane.setCenter(root);
        }
        if(isMobile()){
            pane = getPrimaryPane();
            pane.setTop(null);
        }
    }

    /** Shows login overview inside the root layout.*/
    public void showLoginOverview() {
        LoginController controller = new LoginController();
        LoginGraphicController graphicController;
        String title;
        String resource;
        URL url;
        Modality m;
        String className = MainApp.class.getName();
        Stage stage;
        try {
            title = "Sportify - Login";
            stage = getPrimaryStage();
            stage.setTitle(title);

            MenuController menuController = menu();
            menuController.setLogin();

            // Load login overview.
            FXMLLoader loaderLogin = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(isNotMobile()) {
                resource = "DesktopView/Login.fxml";
                url = MainApp.class.getResource(resource);
                loaderLogin.setLocation(url);
            } else {
                resource = "SmartphoneView/LoginPhone2.fxml";
                url = MainApp.class.getResource(resource);
                loaderLogin.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                resource = "SmartphoneView/topScreen2.fxml";
                url = MainApp.class.getResource(resource);
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderLogin.load();

            // Setting gymEditController
            graphicController = loaderLogin.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setSubmit(getSubmit());
            controller.setUser(getUser());
            menuController.setInstance(graphicController);
            controller.setMenu(getMenu());
            controller.setExternal(isExternalLogin());

            if(!isExternalLogin()) {
                // Set login overview into the center of root layout.
                this.getPrimaryPane().setCenter(pane);
                if(isMobile()){
                    this.getPrimaryPane().setTop(paneTopScreen);
                    assert graphicMenuController != null;
                    graphicMenuController.setController(menuController);
                }
            } else {
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                setSecondaryStage(dialogStage);
                title = "Sportify - Login";
                dialogStage.setTitle(title);
                resource = "Images/Sportify icon.png";
                InputStream inS = getClass().getResourceAsStream(resource);
                Image img = new Image(Objects.requireNonNull(inS));
                dialogStage.getIcons().add(img);

                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                resource = "DesktopView/RootLayout.fxml";
                url = getClass().getResource(resource);
                loader.setLocation(url);
                BorderPane root = loader.load();
                setSecondaryPane(root);

                // SetWindowModal
                m = Modality.WINDOW_MODAL;
                dialogStage.initModality(m);
                dialogStage.initOwner(getPrimaryStage());
                Scene scene = new Scene(root, 830, 550);
                root.setCenter(pane);
                dialogStage.setScene(scene);
                dialogStage.setResizable(false);

                // Set the person into the gymEditController.
                controller.setMenu(getMenu());
                controller.setStage(dialogStage);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            }

        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows sport quiz overview inside the root layout.*/
    public void showSportQuizOverview(MenuController menu) {
        String title;
        String resource;
        URL url;
        String className = MainApp.class.getName();
        try {
            title = "Sportify - Sport Quiz";
            this.getPrimaryStage().setTitle(title);
            // Load sport quiz overview.
            FXMLLoader loaderSport = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(isNotMobile()) {
                resource = "DesktopView/SportQuiz.fxml";
                url = MainApp.class.getResource(resource);
                loaderSport.setLocation(url);
            } else {
                resource = "SmartphoneView/SportQuizPhone1.fxml";
                url = MainApp.class.getResource(resource);
                loaderSport.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                resource = "SmartphoneView/topScreen1.fxml";
                url = MainApp.class.getResource(resource);
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            this.getPrimaryPane().setCenter(pane);
            if(isMobile()){
                this.getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(menu);
            }

            // Give the gymEditController access to the main app.
            SportQuizController controller = new SportQuizController();
            if(isNotMobile()) {
                SportQuizGraphicController graphicController = loaderSport.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
            } else {
                SportQuizPhoneGraphicController graphicController = loaderSport.getController();
                controller.setGraphicController(graphicController);
                graphicController.setController(controller);
                menu.setInstance(graphicController);
            }
            controller.setMainApp(this);
            controller.setUser(getUser());
            controller.setMenu(menu);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows find gym overview inside the root layout.*/
    public void showFindGymOverview(MenuController menu) {
        String title;
        String resource;
        URL url;
        String className = MainApp.class.getName();
        BorderPane primaryPane = getPrimaryPane();
        try {
            title = "Sportify - Find Gym";
            this.getPrimaryStage().setTitle(title);
            // Load find gym overview.
            FXMLLoader loaderGym = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(isNotMobile()) {
                resource = "DesktopView/FindGym.fxml";
                url = MainApp.class.getResource(resource);
                loaderGym.setLocation(url);
            } else {
                resource = "SmartphoneView/FindGymPhone0.fxml";
                url = MainApp.class.getResource(resource);
                loaderGym.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                resource = "SmartphoneView/topScreen0.fxml";
                url = MainApp.class.getResource(resource);
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderGym.load();

            // Set find gym overview into the center of root layout.
            primaryPane.setCenter(pane);
            if(isMobile()){
                primaryPane.setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(menu);
            }

            // Give the gymEditController access to the main app.
            FindGymPhoneGraphicController graphicController = loaderGym.getController();
            FindGymController controller = new FindGymController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this);
            controller.setSearchCache(getSearchCache());
            controller.setMenu(menu);
            controller.setProjection(getProjection());

        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Shows sign up overview inside the root layout.*/
    public void showSignUpOverview() {
        String title;
        String resource;
        URL url;
        FXMLLoader loaderTopScreen;
        String className = MainApp.class.getName();
        BorderPane primaryPane = getPrimaryPane();
        try {
            title = "Sportify - Sign Up";
            this.getPrimaryStage().setTitle(title);
            // Load sign up overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(isNotMobile()) {
                resource = "DesktopView/SignUp.fxml";
                url = MainApp.class.getResource(resource);
                loaderSignUp.setLocation(url);
            } else {
                resource = "SmartphoneView/UserKind0.fxml";
                url = MainApp.class.getResource(resource);
                loaderSignUp.setLocation(url);
                loaderTopScreen = new FXMLLoader();
                resource = "SmartphoneView/topScreen0.fxml";
                url = MainApp.class.getResource(resource);
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderSignUp.load();

            // Set sign up overview into the center of root layout.
            primaryPane.setCenter(pane);
            if(isMobile()){
                this.getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(getMenu());
                graphicMenuController.setUserKind();
            }

            // Give the gymEditController access to the main app.
            SignUpGraphicController graphicController = loaderSignUp.getController();
            SignUpController controller = new SignUpController();
            controller.setGraphicController(graphicController);
            controller.setMenu(getMenu());
            graphicController.setController(controller);
            controller.setMainApp(this);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to create account hashmap*/
    public Map<String, String> createAccount(String username, String password, String firstName,
                                             String lastName, String email, String date){
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

    /** Is called to set top menu*/
    public void setTopMenu(FXMLLoader loaderTopScreen){
        Pane paneTopScreen = null;
        String className = MainApp.class.getName();
        try {
            paneTopScreen = loaderTopScreen.load();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
        this.getPrimaryPane().setTop(paneTopScreen);
    }
}
