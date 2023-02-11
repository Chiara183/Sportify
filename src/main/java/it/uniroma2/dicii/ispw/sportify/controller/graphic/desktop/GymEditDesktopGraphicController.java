package it.uniroma2.dicii.ispw.sportify.controller.graphic.desktop;

import it.uniroma2.dicii.ispw.sportify.controller.graphic.GymEditGraphicController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Objects;

public class GymEditDesktopGraphicController extends GymEditGraphicController {
    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        super.okAction();
        LocalDate bday = super.date.getValue();
        if(!Objects.equals(gymEditController.getUser().getBirthday(), bday)) {
            gymEditController.getUser().setBirthday(bday);
        }
        gymEditController.getMenu().setUser(gymEditController.getUser());
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    /** Controls the modifiability of all field*/
    @FXML
    protected void setTogglePassGym(MouseEvent modify) {
        if (modify.getSource() == modifyGymName) {
            if (!toggleGymName.isSelected()) {
                modifyGymName.setStyle(STYLE);
                toggleGymName.setSelected(true);
                gymEditController.togglevisible(this.toggleGymName, this.gymNameLabel, this.gymName);
            } else {
                modifyGymName.setStyle(COLOR);
                toggleGymName.setSelected(false);
                gymEditController.togglevisible(this.toggleGymName, this.gymNameLabel, this.gymName);
            }
        } else if (modify.getSource() == modifyAddress) {
            if (!toggleAddress.isSelected()) {
                modifyAddress.setStyle(STYLE);
                toggleAddress.setSelected(true);
                gymEditController.togglevisible(this.toggleAddress, this.addressLabel, this.address);
            } else {
                modifyAddress.setStyle(COLOR);
                toggleAddress.setSelected(false);
                gymEditController.togglevisible(this.toggleAddress, this.addressLabel, this.address);
            }
        } else if (modify.getSource() == modifyTelephone) {
            if (!toggleTelephone.isSelected()) {
                modifyTelephone.setStyle(STYLE);
                toggleTelephone.setSelected(true);
                gymEditController.togglevisible(this.toggleTelephone, this.telephoneLabel, this.telephone);
            } else {
                modifyTelephone.setStyle(COLOR);
                toggleTelephone.setSelected(false);
                gymEditController.togglevisible(this.toggleTelephone, this.telephoneLabel, this.telephone);
            }
        } else {
            super.setTogglePass(modify);
        }
    }
}
