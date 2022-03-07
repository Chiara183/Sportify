package com.example.sportify.controller.graphic;

import javafx.fxml.FXML;
import java.time.LocalDate;
import java.util.Objects;

public class GymEditPhoneGraphicController extends GymEditGraphicController{

    /** Go to gym info interface*/
    @FXML
    private void gymInfoAction() {
        controller.getMenu().loadGymInfo();
    }

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        super.okAction();
        LocalDate bday = super.settingBday();
        if(!Objects.equals(controller.getUser().getBirthday(), bday)) {
            controller.getUser().setBirthday(bday);
        }
        controller.getMenu().setUser(controller.getUser());
        controller.getMenu().back();
    }
}
