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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpGymController extends AccessController {

    private static final Logger LOGGER = Logger.getLogger(SignUpGymController.class.getName());

    /** Reference to graphicController*/
    private SignUpGymGraphicController graphicController;
    private final MainApp mainAppGym;
    private final Submit submitGym;

    /** The constructor.*/
    public SignUpGymController(MainApp mainAppGym) {
        this.type = ControllerType.SIGN_UP_GYM;
        this.submitGym = new Submit(mainAppGym);
        this.mainAppGym = mainAppGym;
    }

    /** It's called to load sign up gym overview*/
    public void signUpGymAction(){
        mainAppGym.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(mainAppGym.isNotMobile()) {
                loaderSignUp.setLocation(MainApp.class.getResource("DesktopView/SignUpGym.fxml"));
            } else {
                loaderSignUp.setLocation(MainApp.class.getResource("SmartphoneView/SignUpGym2Phone.fxml"));
                FXMLLoader loaderTopScreen = new FXMLLoader();
                loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen2.fxml"));
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainAppGym.getPrimaryPane().setCenter(pane);
            if(!mainAppGym.isNotMobile()){
                this.mainAppGym.getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(menu);
            }

            // Give the gymEditController access to the main app.
            SignUpGymGraphicController signUpGymGraphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController(this.mainAppGym);
            controller.setGraphicController(signUpGymGraphicController);
            signUpGymGraphicController.setController(controller);
            controller.setMainApp(this.mainAppGym);
            controller.setSubmit(this.submitGym);
            menu.setInstance(signUpGymGraphicController);
            menu.setView(ControllerType.SIGN_UP_GYM2);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());        }
    }

    public void submitActionSignUpGym(String gymValue, String address, Map<String, Double> coords) {
        IO objIO = new IO();
        objIO.setMainApp(this.mainAppGym);
        Map<String, String> gymAccount;
        PreparedStatement ps = null;
        ResultSet rs;
        Connection connection = DBConnection.getSingletonInstance().getConnection();
        try{
            assert connection != null;
            ps = connection.prepareStatement("SELECT * " +
                    "FROM user " +
                    "LEFT JOIN gym ON gym.owner = user.username " +
                    "WHERE user.ruolo = \"gym\"");
            rs = ps.executeQuery();
        gymAccount = objIO.getInfoUser(rs);
        gymAccount.put("gymName", gymValue);            //put gymName in userAccount
        gymAccount.put("address", address);             //put gymAddress in userAccount
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));

        this.submitGym.signUp(gymAccount);
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                LOGGER.info(e.toString());
            }
        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGymGraphicController) graphicController;
    }

    public SignUpGymGraphicController getGraphicController() {
        return graphicController;
    }
}


