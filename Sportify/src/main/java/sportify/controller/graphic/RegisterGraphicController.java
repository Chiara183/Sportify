package sportify.controller.graphic;

import javafx.fxml.FXML;

abstract class RegisterGraphicController extends AccessGraphicController{

    /** The action of the buttons*/
    @FXML
    private void skipAction(){
        controller.login();
    }
}
