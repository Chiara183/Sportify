package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.phone.FindGymPhoneGraphicController;
import javafx.fxml.FXML;

public class FindGymGraphicController extends FindGymPhoneGraphicController implements GraphicController{

    /** The action of back button*/
    @FXML
    private void skipAction() {
        MainApp.setUser(this.controller.getUser());
        MainApp.showOverview(ControllerType.HOME);
    }

}
