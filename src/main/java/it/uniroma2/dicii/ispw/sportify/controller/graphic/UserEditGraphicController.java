package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.UserEditController;
import javafx.fxml.FXML;
import java.util.Objects;

public class UserEditGraphicController extends EditGraphicController{

    /** Reference to controller*/
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

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.userEditController = (UserEditController) controller;
        super.setController(controller);
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return userEditController.getType();
    }
}
