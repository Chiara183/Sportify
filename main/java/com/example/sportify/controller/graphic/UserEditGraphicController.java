package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.UserEditController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Objects;

public class UserEditGraphicController extends EditGraphicController{

    /** Reference to controller*/
    private UserEditController controller;

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
        if (!Objects.equals(controller.getUser().getBirthday(), date.getValue())) {
            controller.getUser().setBirthday(date.getValue());
        }
        controller.getMenu().setUser(controller.getUser());
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (UserEditController) controller;
        super.setController(controller);
    }
}
