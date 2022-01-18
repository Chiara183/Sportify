package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.IO;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.SignUpGymGraphicController;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SignUpGymController extends AccessController {

    /** Reference to graphic controller*/
    private SignUpGymGraphicController graphicController;

    /** The constructor.*/
    public SignUpGymController() {
        this.type = ControllerType.SIGN_UP_GYM;
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


