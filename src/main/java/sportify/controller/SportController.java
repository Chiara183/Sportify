package sportify.controller;

import sportify.DAO;
import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SportGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sportify.errorlogic.DAOException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SportController extends Controller{

    SportGraphicController graphicController;

    /** Reference to the sport quiz*/
    private SportQuizController quiz;

    /** Is called to set quiz gymEditController*/
    public void setQuiz(SportQuizController quiz) {
        this.quiz = quiz;
    }

    /** Is called to get quiz gymEditController*/
    public SportQuizController getQuiz() {
        return this.quiz;
    }

    /** The constructor.*/
    public SportController(){
        this.type = ControllerType.SPORT;
    }

    /** It's called to load the sport description from DB*/
    public void loadDescriptionFromDB(String sport){
        DAO objDAO = mainApp.getDAO();
        String query = "SELECT * " +
                "FROM sport " +
                "WHERE '" + sport + "' = sport.name";
        List<String> list = null;
        try {
            list = objDAO.checkData(query, "description");
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(SportController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert list != null;
        String rs = list.get(list.size() - 1);
        this.loadingSport(sport, rs);
    }

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

    /** It's called to load the sport name from DB*/
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

    /** It's called to load the sport from DB*/
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

    /** Is called to set graphicController*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SportGraphicController) graphicController;
    }

    /** Is called to set center pane*/
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

    public void back() {
        Logger logger = Logger.getLogger(SportController.class.getName());
        logger.log(Level.WARNING, "--back--");
    }
}
