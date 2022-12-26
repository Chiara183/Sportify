package sportify.controller.graphic;

import sportify.OpenStreetMapUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.Map;

public class SignUpGymPhoneGraphicController extends SignUpGymGraphicController{

    @Override
    @FXML
    protected void submitActionSignUpGym() {
        if(!this.bean.checkEmpty(gymName) || !this.bean.checkEmpty(gymAddress) || !this.bean.checkEmpty(gymCity)){
            //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(controller.getMainApp().getPrimaryStage());
                alert.setTitle("Field empty");
                alert.setHeaderText("A field is empty");
                alert.setContentText("Please fill gym name, address and city field");
                alert.showAndWait();
        }
        String gymValue = gymName.getText();            //get user entered gym name
        String addressValue = gymAddress.getText();     //get user entered gym address
        String cityValue = gymCity.getText();           //get user entered gym city
        String address = addressValue + ", " + cityValue;
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(address);

        //check whether the credentials are authentic or not
        if (coords.get("lat") != null && coords.get("lon") != null) {
            //if authentic, navigate user to a new page
            controller.submitActionSignUpGym(gymValue, address, coords);
            controller.login();
        } else {
            helpMethod(coords);
        }
    }

    public void calledSubmitAction(){
        submitActionSignUpGym();
    }
}
