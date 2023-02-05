package sportify.controller;

import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SportQuizGraphicController;
import sportify.controller.graphic.phone.SportQuizPhoneGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SportQuizController is a class that extends the
 * Controller class and is used to control the quiz application.
 * It includes methods to take the quiz, set the sport, navigate
 * home and to different sections of the quiz.
 * It also includes methods to perform different actions based on
 * the type of quiz and the environment selected.
 *
 * @see Controller
 */
public class
SportQuizController extends Controller {

    /**
     * Instance variable for the SportQuizGraphicController class
     */
    SportQuizGraphicController quizGraphicController;

    /* The variable that identify the user choice*/
    /**
     * Represents the state of buttonAge1.
     */
    private static boolean buttonAge1 = false;
    private static final String AGE1 = "age1";

    /**
     * Represents the state of buttonAge2.
     */
    private static boolean buttonAge2 = false;
    private static final String AGE2 = "age2";

    /**
     * Represents the state of buttonAge3.
     */
    private static boolean buttonAge3 = false;
    private static final String AGE3 = "age3";
    private static final String AGE4 = "age4";

    /**
     * Represents the state of buttonIndoor.
     */
    private static boolean buttonIndoor = false;
    private static final String INDOOR = "indoor";
    private static final String OUTDOOR = "outdoor";
    private static final String GROUP = "group";
    private static final String SINGLE = "alone";
    private static final String END = "endQuiz";
    private static final String NEXT = "nextQuiz";
    private static final String NEXT_ENV = "nextQuizEnv";

    /**
     * Represents the lock object for synchronization.
     */
    private final Object lockObj = new Object();

    /**
     * The default constructor for SportQuizController.
     */
    public SportQuizController(){
        this.type = ControllerType.SPORT_QUIZ;
    }

    /**
     * Takes the quiz based on the user selection.
     *
     * @param b The user's selected quiz.
     */
    public void takeQuiz(String b) {
        if (Objects.equals(b, AGE1)) {
            synchronized (lockObj) {
            buttonAge1 = true;}
            sportQuizEnv();
        }
        else if (Objects.equals(b, AGE2)) {
            synchronized (lockObj) {
            buttonAge2 = true;}
            sportQuizEnv();
        }
        else if (Objects.equals(b, AGE3)) {
            synchronized (lockObj) {
            buttonAge3 = true;}
            sportQuizEnv();
        }
        else if (Objects.equals(b, AGE4)) {
            sportQuizEnv();
        }
        else if (Objects.equals(b, INDOOR)) {
            synchronized (lockObj) {
            buttonIndoor = true;}
            sportQuizType();
        }
        else if (Objects.equals(b, OUTDOOR)) {
           sportQuizType();
        }
        else if (Objects.equals(b, GROUP)) {
            groupAction();
        }
        else if (Objects.equals(b, SINGLE)) {
            singleAction();
        }
        else if (Objects.equals(b, END) || Objects.equals(b, NEXT) || Objects.equals(b, NEXT_ENV)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You need to make a choice first!\n Press a button or back");
            alert.showAndWait();
        }
        else if(Objects.equals(b, "invalid input")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input, please retry!");
            alert.showAndWait();
        }
    }

    /**
     * Performs group action.
     */
    private void groupAction(){
        if(buttonIndoor){
            groupIndoorAction();
        }
        else{
            groupOutdoorAction();
        }
    }

    /**
     * Performs single action.
     */
    private void singleAction(){
        if(buttonIndoor){
            singleIndoorAction();
        }
        else{
            singleOutdoorAction();
        }
    }

    /**
     * Performs group indoor action.
     */
    private void groupIndoorAction(){
        if(buttonAge1 || buttonAge2){
            setSport("Volleyball");
        }
        else{
            setSport("Dance");
        }
    }

    /**
     * Performs single indoor action.
     */
    private void singleIndoorAction(){
        setSport("Swimming");
    }

    /**
     * Performs group outdoor action.
     */
    private void groupOutdoorAction(){
        if(buttonAge1 || buttonAge2){
            setSport("Football");
        }
        else{
            setSport("Trekking");
        }
    }

    /**
     * Performs single outdoor action.
     */
    private void singleOutdoorAction(){
        if(buttonAge1){
            setSport("Athletics");
        }
        else if(buttonAge2 || buttonAge3){
            setSport("Tennis");
        }
        else {
            setSport("Golf");
        }
    }

    /**
     * Sets the sport based on the user selection.
     *
     * @param nameOfSport The name of the
     *                    sport selected by the user.
     */
    private void setSport(String nameOfSport){
        SportController sport = new SportController();
        sport.setMainApp(getMainApp());
        sport.setUser(getUser());
        sport.setMenu(getMenu());
        sport.setQuiz(this);
        sport.loadingSportName(nameOfSport);
    }


    /**
     * Navigates the user to the home screen.
     */
    public void home(){
        MainApp app = getMainApp();
        app.setUser(getUser());
        app.showHomeOverview();
    }

    /**
     * Navigates the user to the sport quiz screen.
     */
    public void sportQuiz(){
        MainApp app = getMainApp();
        app.setUser(getUser());
        app.showSportQuizOverview(getMenu());
    }

    /**
     * Navigates the user to the sport quiz environment screen.
     */
    public void sportQuizEnv(){
        FXMLLoader loaderTopScreen = new FXMLLoader();
        FXMLLoader loaderSport = new FXMLLoader();
        MenuController menuController = getMenu();
        if(setScene("Env", loaderSport, loaderTopScreen)){
            menuController.setView(ControllerType.SPORT_QUIZ_ENV);
            MenuGraphicController graphicMenuController = loaderTopScreen.getController();
            graphicMenuController.setController(menuController);
            this.createController(loaderSport);
        }
    }

    /**
     * Navigates the user to the sport quiz type screen.
     */
    public void sportQuizType(){
        FXMLLoader loaderSport = new FXMLLoader();
        FXMLLoader loaderTopScreen = new FXMLLoader();
        if(setScene("Type", loaderSport, loaderTopScreen)){
            SportQuizPhoneGraphicController graphicMenuController = loaderTopScreen.getController();
            graphicMenuController.setController(this);
            SportQuizPhoneGraphicController graphicQuizController = this.createController(loaderSport);
            graphicMenuController.setQuiz(graphicQuizController);
        }
    }

    /**
     * Sets the scene for the quiz based on the type of scene and the FXMLLoader instances.
     *
     * @param kindOfScene The type of scene to be set.
     * @param loaderSport The FXMLLoader instance for the sport quiz.
     * @param loaderTopScreen The FXMLLoader instance for the top screen.
     *
     * @return True if the scene was set successfully, false otherwise.
     */
    public boolean setScene(String kindOfScene, FXMLLoader loaderSport, FXMLLoader loaderTopScreen){
        MainApp app = getMainApp();
        boolean result = false;
        app.setUser(getUser());
        app.getPrimaryStage().setTitle("Sportify - Sport Quiz");
        URL url;
        if(app.isNotMobile()) {
            if(kindOfScene.equals("Env")) {
                url = MainApp.class.getResource("DesktopView/SportQuizEnv.fxml");
                loaderSport.setLocation(url);
            }
            else{
                url = MainApp.class.getResource("DesktopView/SportQuizType.fxml");
                loaderSport.setLocation(url);
            }
            this.createController(loaderSport);
        }
        else{
            result = true;
            if(kindOfScene.equals("Env")) {
                url = MainApp.class.getResource("SmartphoneView/SportQuizEnvPhone1.fxml");
                loaderSport.setLocation(url);
            }
            else{
                url = MainApp.class.getResource("SmartphoneView/SportQuizTypePhone4.fxml");
                loaderSport.setLocation(url);
            }
            if(kindOfScene.equals("Env")) {
                url = MainApp.class.getResource("SmartphoneView/topScreen1.fxml");
                loaderTopScreen.setLocation(url);
            }
            else{
                url = MainApp.class.getResource("SmartphoneView/topScreen4.fxml");
                loaderTopScreen.setLocation(url);
            }
            app.setTopMenu(loaderTopScreen);
        }
        return result;
    }

    /**
     * Creates an instance of SportQuizPhoneGraphicController.
     *
     * @param loaderSport FXMLLoader object that is used to load the FXML file
     *
     * @return an instance of SportQuizPhoneGraphicController
     */
    private SportQuizPhoneGraphicController createController(FXMLLoader loaderSport){
        SportQuizPhoneGraphicController graphicPhoneController = null;
        MainApp app = getMainApp();
        try {
            Pane pane = loaderSport.load();

            // Set sport quiz overview into the center of root layout.
            app.getPrimaryPane().setCenter(pane);

            // Give the gymEditController access to the main app.
            if(app.isNotMobile()) {
                SportQuizGraphicController graphicController = loaderSport.getController();
                this.setGraphicController(graphicController);
                graphicController.setController(this);
            }
            else{
                graphicPhoneController = loaderSport.getController();
                this.setGraphicController(graphicPhoneController);
                graphicPhoneController.setController(this);
                getMenu().setInstance(graphicPhoneController);
            }
            this.setUser(getUser());
            this.setMainApp(app);
            this.setMenu(getMenu());

        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(SportQuizController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return graphicPhoneController;
    }

    /**
     * Overrides the setGraphicController method from the Controller class.
     *
     * @param graphicController The GraphicController object to be set
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.quizGraphicController = (SportQuizGraphicController) graphicController;
    }
}

