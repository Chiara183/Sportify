package com.example.sportify.controller.graphic;

import com.example.sportify.OpenStreetMapUtils;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.SignUpGymController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Map;

public class SignUpGymGraphicController extends GraphicController{

    /** All the text field of the interface*/
    @FXML
    private TextField gymName;
    @FXML
    private TextField gymAddress;
    @FXML
    private TextField gymCity;

    /** Reference to controller*/
    private SignUpGymController controller;

    /** The action of the buttons*/
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
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You're registered!");
            controller.login();
        } else {
            if (coords.get("lat") == null && coords.get("lon") == null){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(controller.getMainApp().getPrimaryStage());
                alert.setTitle("Wrong address");
                alert.setHeaderText("Sorry, we can't find your address");
                alert.setContentText("Please enter valid address");
                alert.showAndWait();
            } else {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(controller.getMainApp().getPrimaryStage());
                alert.setTitle("Empty field");
                alert.setHeaderText("Some obligatory value are empty");
                alert.setContentText("Please enter all value.");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void skipAction(){
        controller.login();
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (SignUpGymController) controller;
    }
}
