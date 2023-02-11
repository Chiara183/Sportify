package sportify.controller.graphic;

import sportify.MainApp;
import sportify.controller.ControllerType;
import sportify.controller.graphic.phone.FindGymPhoneGraphicController;
import javafx.fxml.FXML;

public class FindGymGraphicController extends FindGymPhoneGraphicController implements GraphicController{

    /** The action of back button*/
    @FXML
    private void skipAction() {
        MainApp.setUser(this.controller.getUser());
        MainApp.showOverview(ControllerType.HOME);
    }

}
