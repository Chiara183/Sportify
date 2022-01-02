package com.example.sportify.controller;

import com.example.sportify.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SignUpGymController implements Initializable {

    //TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to submit.
    private Submit submit;

    /**
     * The constructor.
     * The constructor is called before to initialize() method.
     */
    public SignUpGymController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setSubmit(Submit submit){
        this.submit = submit;
    }

    @FXML
    protected void submitActionSignUpGym() {
        DAO obj_DAO = new DAO();
        HashMap<String, String> gymAccount = new HashMap<>();
        try {
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM user " +
                            "LEFT JOIN gym ON gym.owner = user.username " +
                            "WHERE user.ruolo = \"gym\"");
            String userValue = rs.getString("username");                    //get user username
            String passValue = rs.getString("password");                    //get user password
            String nameValue = rs.getString("first_name");                  //get user first name
            String lastNameValue = rs.getString("last_name");               //get user last name
            String email = rs.getString("email");                           //get user email
            String birthday = "";
            if(rs.getDate("birthday") != null) {
                birthday = rs.getDate("birthday").toString();        //get user birthday
            }
            String ruolo = rs.getString("ruolo");                           //get user ruolo
            String gym_name = rs.getString("name");                         //get user gym_name
            String address = rs.getString("address");                       //get user gym_address
            String latitude = String.valueOf(rs.getDouble("latitude"));     //get user gym_latitude
            String longitude = String.valueOf(rs.getDouble("longitude"));   //get user gym_longitude
            String phone = rs.getString("phone");                           //get user gym_phone
            gymAccount.put("username", userValue);                                     //put userValue in userAccount
            gymAccount.put("password", passValue);                                     //put user password in userAccount
            gymAccount.put("firstName", nameValue);                                    //put user firstName in userAccount
            gymAccount.put("lastName", lastNameValue);                                 //put user lastName in userAccount
            gymAccount.put("email", email);                                            //put user email in userAccount
            gymAccount.put("birthday", birthday);                                      //put user birthday in userAccount
            gymAccount.put("gymName", gym_name);                                       //put user gym_name in userAccount
            gymAccount.put("address", address);                                        //put user address in userAccount
            gymAccount.put("latitude", latitude);                                      //put user latitude in userAccount
            gymAccount.put("longitude", longitude);                                    //put user longitude in userAccount
            gymAccount.put("phone", phone);                                            //put user phone in userAccount
            gymAccount.put("ruolo", ruolo);                                            //put user ruolo in userAccount
        }catch (SQLException e){
            e.printStackTrace();
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}


