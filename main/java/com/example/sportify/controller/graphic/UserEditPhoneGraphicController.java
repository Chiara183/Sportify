package com.example.sportify.controller.graphic;

import com.example.sportify.DateUtil;
import com.example.sportify.bean.UserEditBean;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.Objects;

public class UserEditPhoneGraphicController extends UserEditGraphicController{

    /** Reference to bean*/
    protected final UserEditBean bean = new UserEditBean();

    int  day = controller.getUser().getBirthday().getDayOfMonth();
    int month = controller.getUser().getBirthday().getMonth().getValue();
    int year = controller.getUser().getBirthday().getYear();

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        super.okAction();
        helpMethod();
        controller.getMenu().setUser(controller.getUser());
        controller.getMenu().back();
    }

    public void helpMethod(){
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
            helpMethod1();
        }
    }

    public void helpMethod1(){
        LocalDate bday = this.bean.settingBirthDay(day, month, year);
        controller.getUser().setBirthday(bday);
    }
}
