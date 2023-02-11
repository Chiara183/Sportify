package it.uniroma2.dicii.ispw.sportify.controller.graphic.desktop;

import it.uniroma2.dicii.ispw.sportify.controller.graphic.UserEditGraphicController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Objects;

public class UserEditDesktopGraphicController extends UserEditGraphicController {

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        super.okAction();
        if (!Objects.equals(userEditController.getUser().getBirthday(), date.getValue())) {
            userEditController.getUser().setBirthday(date.getValue());
        }
        userEditController.getMenu().setUser(userEditController.getUser());
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }
}
