package sportify.controller.graphic;

import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.UserEditController;
import javafx.fxml.FXML;
import java.util.Objects;

public class UserEditGraphicController extends EditGraphicController{

    /** Reference to controller*/
    protected UserEditController controller;

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        if (!Objects.equals(controller.getUser().getUserName(), username.getText())) {
            controller.getUser().setUserName(username.getText());
        }
        if (!Objects.equals(controller.getUser().getPassword(), password.getText())) {
            controller.getUser().setPassword(password.getText());
        }
        if (!Objects.equals(controller.getUser().getFirstName(), firstName.getText())) {
            controller.getUser().setFirstName(firstName.getText());
        }
        if (!Objects.equals(controller.getUser().getLastName(), lastName.getText())) {
            controller.getUser().setLastName(lastName.getText());
        }
        if (!Objects.equals(controller.getUser().getEmail(), email.getText())) {
            controller.getUser().setEmail(email.getText());
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (UserEditController) controller;
        super.setController(controller);
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
