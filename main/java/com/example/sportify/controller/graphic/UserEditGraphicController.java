package com.example.sportify.controller.graphic;

import com.example.sportify.DateUtil;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
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
            String day = String.valueOf(controller.getUser().getBirthday().getDayOfMonth());
            String month = String.valueOf(controller.getUser().getBirthday().getMonth().getValue());
            String year = String.valueOf(controller.getUser().getBirthday().getYear());
            if (!toggleBirthday.isSelected() && !Objects.equals(day + "/" + month + "/" + year, birthday.getText())) {
                String birth = birthday.getText();
                String[] list = birth.split("/");
                year = list[2];
                if(list[1].length() < 2){
                    month = "0" + list[1];
                } else {
                    month = list[1];
                }

                if(list[0].length() < 2){
                    day = "0" + list[0];
                } else {
                    day = list[0];
                }
                birth = year + "-" + month + "-" + day;
                controller.getUser().setBirthday(DateUtil.parse(birth));
            } else if (toggleBirthday.isSelected() && !Objects.equals(day + "/" + month + "/" + year,
                    (dayOfBirth.getText() + "/" + birthMonth.getText() + "/" + birthYear.getText()))){
                year = birthYear.getText();
                if(birthMonth.getText().length() < 2){
                    month = "0" + birthMonth.getText();
                } else {
                    month = birthMonth.getText();
                }

                if(dayOfBirth.getText().length() < 2){
                    day = "0" + dayOfBirth.getText();
                } else {
                    day = dayOfBirth.getText();
                }
                controller.getUser().setBirthday(DateUtil.parse(year + "-" + month + "-" + day));
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

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
