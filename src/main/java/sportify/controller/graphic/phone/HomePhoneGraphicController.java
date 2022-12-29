package sportify.controller.graphic.phone;

import sportify.auth.OAuthGoogleAuthenticator;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.HomeController;
import sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePhoneGraphicController implements GraphicController {

    private static final Properties prop = new Properties();
    private static final String G_SECRET = prop.getProperty("G_SECRET");

    /** All the button of the interface*/
    @FXML
    protected Button signIn;
    @FXML
    protected Button gymInfo;
    @FXML
    protected ComboBox<String> comboActivity;

    public ComboBox<String>getCombo(){
        return comboActivity;
    }

    /** Reference to gymEditController*/
    protected HomeController controller;

    /** The action of comboBox*/
    @FXML
    protected void comboAction(){
        Object selectedItem = comboActivity.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        switch (choice) {
            case "Take sport quiz" -> this.controller.getMenu().getGraphicController().sportQuizAction();
            case "Login" -> this.controller.getMenu().getGraphicController().signAction();
            case "Login with Google" -> {
                String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
                String gRedir = "https://localhost:8080/oauth2";
                String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
                OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, G_SECRET, gScope);
                auth.startLogin(controller.getMainApp());
            }
            case "Find gym" -> this.controller.getMenu().getGraphicController().findGymAction();
            default -> {
                Logger logger = Logger.getLogger(HomePhoneGraphicController.class.getName());
                logger.log(Level.INFO, "Waiting a choice");
            }
        }
    }

    /** Is called to set gymEditController*/
    @Override
    public void setController(Controller controller) {
        this.controller = (HomeController) controller;
    }

    /** Is called to get gymEditController type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }

    /** Is called to get signIn button*/
    public Button getSignIn(){return this.signIn;}

    /** Is called to get gymInfo button*/
    public Button getGymInfo(){return this.gymInfo;}

}
