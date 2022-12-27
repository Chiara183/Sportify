package sportify.controller.graphic;

import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.UserEditController;
import javafx.fxml.FXML;
import java.util.Objects;

public class UserEditGraphicController extends EditGraphicController{

    /** Reference to gymEditController*/
    protected UserEditController userEditController;

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        if (!Objects.equals(userEditController.getUser().getUserName(), username.getText())) {
            userEditController.getUser().setUserName(username.getText());
        }
        if (!Objects.equals(userEditController.getUser().getPassword(), password.getText())) {
            userEditController.getUser().setPassword(password.getText());
        }
        if (!Objects.equals(userEditController.getUser().getFirstName(), firstName.getText())) {
            userEditController.getUser().setFirstName(firstName.getText());
        }
        if (!Objects.equals(userEditController.getUser().getLastName(), lastName.getText())) {
            userEditController.getUser().setLastName(lastName.getText());
        }
        if (!Objects.equals(userEditController.getUser().getEmail(), email.getText())) {
            userEditController.getUser().setEmail(email.getText());
        }
    }

    /** Is called to set gymEditController*/
    @Override
    public void setController(Controller controller) {
        this.userEditController = (UserEditController) controller;
        super.setController(controller);
    }

    /** Is called to get gymEditController type*/
    @Override
    public ControllerType getGraphicType(){
        return userEditController.getType();
    }
}
