package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.bean.SignUpBean;
import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.SignUpGymController;
import it.uniroma2.dicii.ispw.sportify.util.OpenStreetMapUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Map;

public class SignUpGymGraphicController extends RegisterGraphicController{

    /* All the text field of the interface*/
    @FXML
    protected TextField gymName;
    @FXML
    protected TextField gymAddress;
    @FXML
    protected TextField gymCity;

    /** Reference to controller*/
    protected SignUpGymController signUpGymController;

    /** Reference to bean*/
    protected final SignUpBean bean = new SignUpBean();

    /** The action of the buttons*/
    @FXML
    protected void submitActionSignUpGym() {
        if(this.bean.checkEmpty(gymName) || this.bean.checkEmpty(gymAddress) || this.bean.checkEmpty(gymCity)){
            alert();
        }
        String gymValue = gymName.getText();            //get user entered gym name
        String addressValue = gymAddress.getText();     //get user entered gym address
        String cityValue = gymCity.getText();           //get user entered gym city
        String address = addressValue + ", " + cityValue;
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(address);

        //check whether the credentials are authentic or not
        if (coords.get("lat") != null && coords.get("lon") != null) {
            //if authentic, navigate user to a new page
            signUpGymController.submitActionSignUpGym(gymValue, address, coords);
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You're registered!");
            signUpGymController.login();
        } else {
            helpMethod(coords);
        }
    }

    protected void alert(){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill gym name, address and city field");
            alert.showAndWait();
    }

    protected void helpMethod(Map<String, Double> coords){
        if (coords.get("lat") == null && coords.get("lon") == null){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Wrong address");
            alert.setHeaderText("Sorry, we can't find your address");
            alert.setContentText("Please enter valid address");
            alert.showAndWait();
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.signUpGymController = (SignUpGymController) controller;
        super.setController(controller);
    }
}
