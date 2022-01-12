package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.IO;
import com.example.sportify.OpenStreetMapUtils;
import com.example.sportify.Submit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class SignUpGymController extends Controller {

    //TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    // Reference to submit.
    private Submit submit;

    public SignUpGymController() {
        this.type = ControllerType.SIGN_UP_GYM;
    }

    /**
     * Is called to set submit.
     */
    public void setSubmit(Submit submit){
        this.submit = submit;
    }

    @FXML
    protected void submitActionSignUpGym() {
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
        String gymValue = gymName.getText();            //get user entered gym name
        String addressValue = gymAddress.getText();     //get user entered gym address
        String cityValue = gymCity.getText();           //get user entered gym city
        String address = addressValue + ", " + cityValue;
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
        gymAccount.put("gymName", gymValue);            //put gymName in userAccount
        gymAccount.put("address", address);             //put gymAddress in userAccount
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));

        //check whether the credentials are authentic or not
        if (!gymValue.equals("") && !addressValue.equals("") && !cityValue.equals("") && coords.get("lat") != null && coords.get("lon") != null) {
            //if authentic, navigate user to a new page
            this.submit.signUp(gymAccount);
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You're registered!");
            login();
        } else {
            if (coords.get("lat") == null && coords.get("lon") == null){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Wrong address");
                alert.setHeaderText("Sorry, we can't find your address");
                alert.setContentText("Please enter valid address");
                alert.showAndWait();
            } else {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Empty field");
                alert.setHeaderText("Some obligatory value are empty");
                alert.setContentText("Please enter all value.");
                alert.showAndWait();
            }
        }
    }

    private void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
    }

    @FXML
    private void skipAction(){
        login();
    }
}


