package com.example.sportify.controller;

import com.example.sportify.*;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.MenuGraphicController;
import com.example.sportify.controller.graphic.SignUpGymGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
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

    /** Reference to graphic controller*/
    private SignUpGymGraphicController graphicController;
    private final MainApp mainApp;
    private final Submit submit;

    /** The constructor.*/
    public SignUpGymController(MainApp mainApp) {
        this.type = ControllerType.SIGN_UP_GYM;
        this.submit = new Submit(mainApp);
        this.mainApp = mainApp;
    }

    /** It's called to load sign up gym overview*/
    public void signUpGymAction(){
        mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(mainApp.isNotMobile()) {
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
            this.mainApp.getPrimaryPane().setCenter(pane);
            if(!mainApp.isNotMobile()){
                this.mainApp.getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(menu);
            }

            // Give the controller access to the main app.
            SignUpGymGraphicController signUpGymGraphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController(this.mainApp);
            controller.setGraphicController(signUpGymGraphicController);
            signUpGymGraphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);
            menu.setInstance(signUpGymGraphicController);
            menu.setView(ControllerType.SIGN_UP_GYM2);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());        }
    }

    public void submitActionSignUpGym(String gymValue, String address, Map<String, Double> coords) {
        IO objIO = new IO();
        objIO.setMainApp(this.mainApp);
        Map<String, String> gymAccount;
        PreparedStatement ps = null;
        ResultSet rs;
        Connection connection  = null;
        try {
            connection = new DBConnection().getConnection();
        } catch (FileNotFoundException e) {
            LOGGER.info(e.toString());
        }
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

        this.submit.signUp(gymAccount);
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


