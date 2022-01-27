package com.example.sportify.controller;

import com.example.sportify.*;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGymGraphicController;
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

    /** Reference to graphic controller*/
    private SignUpGymGraphicController graphicController;
    private MainApp mainApp;
    private Submit submit;

    /** The constructor.*/
    public SignUpGymController(MainApp mainApp) {
        this.type = ControllerType.SIGN_UP_GYM;
        this.submit = new Submit(null);
        this.mainApp = mainApp;
    }

    /** It's called to load sign up gym overview*/
    public void signUpGymAction(){
        mainApp.getPrimaryStage().setTitle("Sportify - Sign Up");
        try {
            // Load login overview.
            FXMLLoader loaderSignUp = new FXMLLoader();
            loaderSignUp.setLocation(MainApp.class.getResource("DesktopView/SignUpGym.fxml"));
            Pane pane = loaderSignUp.load();

            // Set login overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            SignUpGymGraphicController signUpGymGraphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController(this.mainApp);
            controller.setGraphicController(signUpGymGraphicController);
            signUpGymGraphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(SignUpGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    public void submitActionSignUpGym(String gymValue, String address, Map<String, Double> coords) {
        IO objIO = new IO();
        objIO.setMainApp(this.mainApp);
        Map<String, String> gymAccount;
        PreparedStatement ps = null;
        ResultSet rs;
        Connection connection  = new DBConnection().getConnection();
        try{
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
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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


