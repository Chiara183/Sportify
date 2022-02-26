package com.example.sportify.controller.graphic;

import com.example.sportify.OpenStreetMapUtils;
import javafx.fxml.FXML;

import java.util.Map;

public class SignUpGymPhoneGraphicController extends SignUpGymGraphicController{

    @Override
    @FXML
    protected void submitActionSignUpGym() {
        String gymValue = gymName.getText();            //get user entered gym name
        String addressValue = gymAddress.getText();     //get user entered gym address
        String cityValue = gymCity.getText();           //get user entered gym city
        String address = addressValue + ", " + cityValue;
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(address);

        //check whether the credentials are authentic or not
        if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("") && coords.get("lat") != null && coords.get("lon") != null) {
            //if authentic, navigate user to a new page
            controller.submitActionSignUpGym(gymValue, address, coords);
            controller.login();
        } else {
            helpMethod(coords);
        }
    }
}
