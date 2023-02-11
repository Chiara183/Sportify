package it.uniroma2.dicii.ispw.sportify.controller.graphic.phone;

import it.uniroma2.dicii.ispw.sportify.controller.graphic.GymEditGraphicController;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.Objects;

public class GymEditPhoneGraphicController extends GymEditGraphicController {

    /** Go to gym info interface*/
    @FXML
    private void gymInfoAction() {
        gymEditController.getMenu().loadGymInfo();
    }

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        super.okAction();
        LocalDate bday = bean.settingBday(super.dayOfBirth, super.birthMonth, super.birthYear);
        if(!Objects.equals(gymEditController.getUser().getBirthday(), bday)) {
            gymEditController.getUser().setBirthday(bday);
        }
        gymEditController.getMenu().setUser(gymEditController.getUser());
        gymEditController.getMenu().back();
    }
}
