package com.example.sportify.controller.graphic;

import javafx.fxml.FXML;

public class FindGymGraphicController extends FindGymPhoneGraphicController implements GraphicController{

    /** The action of back button*/
    @FXML
    private void skipAction() {
        this.controller.getMainApp().setUser(this.controller.getUser());
        this.controller.getMainApp().showHomeOverview();
    }

}
