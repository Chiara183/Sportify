package sportify.controller.graphic.desktop;

import sportify.controller.graphic.UserEditGraphicController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Objects;

public class UserEditDesktopGraphicController extends UserEditGraphicController {

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        super.okAction();
        if (!Objects.equals(controller.getUser().getBirthday(), date.getValue())) {
            controller.getUser().setBirthday(date.getValue());
        }
        controller.getMenu().setUser(controller.getUser());
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }
}
