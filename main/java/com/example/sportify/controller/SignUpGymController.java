package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.IO;
import com.example.sportify.MainApp;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGymGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SignUpGymController extends AccessController {

    /** Reference to graphic controller*/
    private SignUpGymGraphicController graphicController;
    private MainApp mainApp;

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
            SignUpGymGraphicController graphicController = loaderSignUp.getController();
            SignUpGymController controller = new SignUpGymController(this.mainApp);
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setSubmit(this.submit);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submitActionSignUpGym(String gymValue, String address, Map<String, Double> coords) {
        DAO obj_DAO = mainApp.getDAO();
        IO obj_IO = new IO();
        obj_IO.setMainApp(this.mainApp);
        HashMap<String, String> gymAccount;
        ResultSet rs = obj_DAO.Check_Data(
                "SELECT * " +
                        "FROM user " +
                        "LEFT JOIN gym ON gym.owner = user.username " +
                        "WHERE user.ruolo = \"gym\"");
        gymAccount = obj_IO.getInfoUser(rs);
        gymAccount.put("gymName", gymValue);            //put gymName in userAccount
        gymAccount.put("address", address);             //put gymAddress in userAccount
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));

        this.submit.signUp(gymAccount);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGymGraphicController) graphicController;
    }
}


