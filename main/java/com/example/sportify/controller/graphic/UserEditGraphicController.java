package com.example.sportify.controller.graphic;

import com.example.sportify.DateUtil;
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
        if(controller.getMainApp().isNotMobile()) {
            if (!Objects.equals(controller.getUser().getBirthday(), date.getValue())) {
                controller.getUser().setBirthday(date.getValue());
            }
        } else {
            if (!toggleBirthday.isSelected() && !Objects.equals((controller.getUser().getBirthday().getDayOfMonth() +
                    "/" + controller.getUser().getBirthday().getMonth().getValue() +
                    "/" + controller.getUser().getBirthday().getYear()), birthday.getText())) {
                String birth = birthday.getText();
                String[] list = birth.split("/");
                birth = list[2] + "-" + list[1] + "-" + list[0];
                System.out.println(birth);
                System.out.println(DateUtil.parse(birth));
                controller.getUser().setBirthday(DateUtil.parse(birth));
            } else if (toggleBirthday.isSelected() && !Objects.equals((controller.getUser().getBirthday().getDayOfMonth() +
                    "/" + controller.getUser().getBirthday().getMonth().getValue() +
                    "/" + controller.getUser().getBirthday().getYear()), (birthDay.getText() +
                    "/" + birthMonth.getText() +
                    "/" + birthYear.getText()))){
                System.out.println((birthYear.getText() +
                        "-" + birthMonth.getText() +
                        "-" + birthDay.getText()));
                System.out.println(DateUtil.parse((birthYear.getText() +
                        "-" + birthMonth.getText() +
                        "-" + birthDay.getText())));
                controller.getUser().setBirthday(DateUtil.parse((birthYear.getText() +
                        "-" + birthMonth.getText() +
                        "-" + birthDay.getText())));
            }
        }
        controller.getMenu().setUser(controller.getUser());
        if(controller.getMainApp().isNotMobile()) {
            Stage stage = (Stage) ok.getScene().getWindow();
            stage.close();
        } else {
            controller.getMenu().back();
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (UserEditController) controller;
        super.setController(controller);
    }
}
