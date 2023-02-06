package sportify.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SignUpGymGraphicController;
import sportify.model.dao.GymDAO;

import java.io.IOException;
import java.net.URL;
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

    /**
     * Reference to MainApp instance
     */
    private final MainApp mainAppGym;
    /**
     * Creates a new instance of the SignUpGymController class.
     *
     * @param mainAppGym The reference to the main application instance.
     */
    public SignUpGymController(MainApp mainAppGym) {
        this.type = ControllerType.SIGN_UP_GYM;
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
        Map<String, String> gymAccount;
        GymDAO dao = new GymDAO(mainAppGym);
        gymAccount = dao.getGymAccount();
        dao.submitGymAccount(gymValue, address, coords, gymAccount);
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


