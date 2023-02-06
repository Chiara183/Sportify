package sportify.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SportGraphicController;
import sportify.model.dao.SportDAO;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SportController class extends the Controller class
 * and handles the logic for displaying and managing the Sport related data.
 * It has SportGraphicController and SportQuizController instance variables
 * and methods to set and get the quiz controller.
 * The class also has methods for loading sport descriptions from the database,
 * handling exceptions in loading FXML files,
 * loading sport names and descriptions, setting the graphic controller and setting
 * the center pane of the screen.
 *
 * @see Controller
 */
public class SportController extends Controller{

    /**
     * Instance variable for the SportGraphicController class
     */
    SportGraphicController graphicController;

    /**
     * Instance variable for the SportQuizController class
     */
    private SportQuizController quiz;

    /**
     * Method to set the SportQuizController instance
     *
     * @param quiz - instance of SportQuizController
     */
    public void setQuiz(SportQuizController quiz) {
        this.quiz = quiz;
    }

    /**
     * Method to get the SportQuizController instance
     *
     * @return - instance of SportQuizController
     */
    public SportQuizController getQuiz() {
        return this.quiz;
    }

    /**
     * Constructor method for the SportController class
     */
    public SportController(){
        this.type = ControllerType.SPORT;
    }

    /**
     * Method to load the sport descriptions from the database
     *
     * @param sport - name of the sport
     */
    public void loadDescriptionFromDB(String sport){
        SportDAO objDAO = new SportDAO();
        String desc = objDAO.getDescriptions(sport);
        this.loadingSport(sport, desc);
    }

    /**
     * Method to handle exceptions in loading the FXML files
     *
     * @param loaderTopScreen - FXMLLoader instance for the top screen
     *
     * @return - Pane object for the top screen
     */
    public Pane tryCatch(FXMLLoader loaderTopScreen){
        Pane paneTopScreen = null;
        try {
            paneTopScreen = loaderTopScreen.load();
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return paneTopScreen;
    }

    /**
     * Method to load the sport name
     *
     * @param sport - name of the sport
     */
    public void loadingSportName(String sport) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        URL url;
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            if(this.getMainApp().isNotMobile()) {
                url = MainApp.class.getResource("DesktopView/Sport.fxml");
                loaderSport.setLocation(url);
            }
            else{
                url = MainApp.class.getResource("SmartphoneView/SportPhone3.fxml");
                loaderSport.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                url= MainApp.class.getResource("SmartphoneView/topScreen3.fxml");
                loaderTopScreen.setLocation(url);
                Pane paneTopScreen =  tryCatch(loaderTopScreen);
                SportGraphicController graphicMenuController = loaderTopScreen.getController();
                this.mainApp.getPrimaryPane().setTop(paneTopScreen);
                graphicMenuController.setController(this);
                graphicMenuController.setSportName(sport);
            }
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the gymEditController access to the main app.
            SportGraphicController sportGraphicController = loaderSport.getController();
            sportGraphicController.setController(this);
            this.setGraphicController(sportGraphicController);
            this.setUser(this.user);
            this.setMainApp(this.mainApp);
            sportGraphicController.setSportName(sport);
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Method to load the sport and its description
     *
     * @param sport - name of the sport
     * @param description - description of the sport
     */
    public void loadingSport(String sport, String description) {
        this.mainApp.setUser(this.user);
        this.mainApp.getPrimaryStage().setTitle("Sportify - Test Result");
        URL url;
        // Load test result overview.
        if(getMainApp().isNotMobile()) {
            FXMLLoader loaderSport = new FXMLLoader();
            url = MainApp.class.getResource("DesktopView/SportInfo.fxml");
            loaderSport.setLocation(url);
            // Give the gymEditController access to the main app.
            SportGraphicController sportGraphicController = setCenterPane(loaderSport);
            sportGraphicController.setSportName(sport);
            sportGraphicController.setSportDescription(description);
        }
        else {
            FXMLLoader loaderSport = new FXMLLoader();
            url = MainApp.class.getResource("SmartphoneView/SportInfoPhone0.fxml");
            loaderSport.setLocation(url);
            FXMLLoader loaderTopScreen = new FXMLLoader();
            url = MainApp.class.getResource("SmartphoneView/topScreen0.fxml");
            loaderTopScreen.setLocation(url);
            mainApp.setTopMenu(loaderTopScreen);
            menu.setView(ControllerType.SPORT_INFO);
            MenuGraphicController graphicMenuController = loaderTopScreen.getController();
            graphicMenuController.setController(menu);
            // Give the gymEditController access to the main app.
            SportGraphicController sportGraphicController = setCenterPane(loaderSport);
            sportGraphicController.setSportName(sport);
            sportGraphicController.setSportDescription(description);
        }
    }

    /**
     * Overridden method from the Controller class to set the graphic controller
     *
     * @param graphicController - instance of the GraphicController
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SportGraphicController) graphicController;
    }

    /**
     * Method to set the center pane of the screen
     *
     * @param loaderSport - FXMLLoader instance for the sport screen
     *
     * @return - instance of SportGraphicController
     */
    private SportGraphicController setCenterPane(FXMLLoader loaderSport){
        Pane pane = null;
        try {
            pane = loaderSport.load();
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        // Set test result overview into the center of root layout.
        this.mainApp.getPrimaryPane().setCenter(pane);

        // Give the gymEditController access to the main app.
        SportGraphicController sportGraphicController = loaderSport.getController();
        this.setGraphicController(sportGraphicController);
        sportGraphicController.setController(this);
        this.setUser(this.user);
        this.setMainApp(this.mainApp);
        this.setMenu(this.menu);
        return sportGraphicController;
    }

    /**
     * Method to go back to the previous screen
     */
    public void back() {
        Logger logger = Logger.getLogger(SportController.class.getName());
        logger.log(Level.WARNING, "--back--");
    }
}
