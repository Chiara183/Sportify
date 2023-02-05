package sportify.controller;

import sportify.DBConnection;
import sportify.IO;
import sportify.MainApp;
import sportify.Submit;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SignUpGymGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sportify.errorlogic.DAOException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SignUpGymController class extends the AccessController
 * class and is responsible for handling the 'sign up' process
 * for a gym. It holds a reference to the main application, a
 * Submit instance and the graphic controller.
 *
 * @see AccessController
 */
public class SignUpGymController extends AccessController {

    /**
     * Reference to graphicController
     */
    private SignUpGymGraphicController graphicController;
    private final MainApp mainAppGym;
    private final Submit submitGym;

    /**
     * Creates a new instance of the SignUpGymController class.
     *
     * @param mainAppGym The reference to the main application instance.
     */
    public SignUpGymController(MainApp mainAppGym) {
        this.type = ControllerType.SIGN_UP_GYM;
        this.submitGym = new Submit(mainAppGym);
        this.mainAppGym = mainAppGym;
    }

    /**
     * Performs the 'sign up' action for a gym.
     */
    public void signUpGymAction(){
        mainAppGym.getPrimaryStage().setTitle("Sportify - Sign Up");
        URL url;
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(mainAppGym.isNotMobile()) {
                url = MainApp.class.getResource("DesktopView/SignUpGym.fxml");
                loaderSignUp.setLocation(url);
            }
            else {
                url = MainApp.class.getResource("SmartphoneView/SignUpGym2Phone.fxml");
                loaderSignUp.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                url = MainApp.class.getResource("SmartphoneView/topScreen2.fxml");
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            getMainApp().getPrimaryPane().setCenter(pane);
            if(!getMainApp().isNotMobile()){
                getMainApp().getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(getMenu());
            }

            // Give the gymEditController access to the main app.
            SignUpGymGraphicController signUpGymGraphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController(getMainApp());
            controller.setGraphicController(signUpGymGraphicController);
            signUpGymGraphicController.setController(controller);
            controller.setMainApp(getMainApp());
            controller.setSubmit(getSubmit());
            getMenu().setInstance(signUpGymGraphicController);
            getMenu().setView(ControllerType.SIGN_UP_GYM2);

        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(SignUpGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Submits the 'sign up' information for a gym.
     *
     * @param gymValue The value of the gym.
     * @param address The address of the gym.
     * @param coords The coordinates of the gym.
     */
    public void submitActionSignUpGym(String gymValue, String address, Map<String, Double> coords) {
        IO objIO = new IO();
        objIO.setMainApp(this.mainAppGym);
        Map<String, String> gymAccount;
        PreparedStatement ps = null;
        ResultSet rs;
        DBConnection db = DBConnection.getSingletonInstance();
        Connection connection = null;
        try {
            connection = db.getConnection();
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(SignUpGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        try{
            assert connection != null;
            ps = connection.prepareStatement("SELECT * " +
                    "FROM user " +
                    "LEFT JOIN gym " +
                    "ON gym.owner = user.username " +
                    "WHERE user.ruolo = \"gym\"");
            rs = ps.executeQuery();
        gymAccount = objIO.getInfoUser(rs);
        gymAccount.put("gymName", gymValue);            //put gymName in userAccount
        gymAccount.put("address", address);             //put gymAddress in userAccount
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));

        this.submitGym.signUp(gymAccount);
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger(SignUpGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(SignUpGymController.class.getName());
                logger.info(e.toString());
            }
        }
    }

    /**
     * Sets the graphic controller for the SignUpGymController class.
     *
     * @param graphicController The graphic controller instance.
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGymGraphicController) graphicController;
    }

    /**
     * Gets the graphic controller for the SignUpGymController class.
     *
     * @return The graphic controller instance.
     */
    public SignUpGymGraphicController getGraphicController() {
        return graphicController;
    }
}


