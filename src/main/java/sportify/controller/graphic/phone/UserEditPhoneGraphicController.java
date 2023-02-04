package sportify.controller.graphic.phone;

import sportify.DateUtil;
import sportify.bean.UserEditBean;
import sportify.controller.graphic.UserEditGraphicController;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.Objects;

public class UserEditPhoneGraphicController extends UserEditGraphicController {

    /** Reference to bean*/
    protected final UserEditBean bean = new UserEditBean();

    final int  day = userEditController.getUser().getBirthday().getDayOfMonth();
    final int month = userEditController.getUser().getBirthday().getMonth().getValue();
    final int year = userEditController.getUser().getBirthday().getYear();

    /* The action of the button.*/
    @Override
    @FXML
    protected void okAction() {
        super.okAction();
        helpMethod();
        userEditController.getMenu().setUser(userEditController.getUser());
        userEditController.getMenu().back();
    }

    public void helpMethod(){
        String dayH = String.valueOf(userEditController.getUser().getBirthday().getDayOfMonth());
        String monthH = String.valueOf(userEditController.getUser().getBirthday().getMonth().getValue());
        String yearH = String.valueOf(userEditController.getUser().getBirthday().getYear());
        if (!toggleBirthday.isSelected() && !Objects.equals(dayH + "/" + monthH + "/" + yearH, birthday.getText())) {
            String birth = birthday.getText();
            String[] list = birth.split("/");
            yearH = list[2];
            if(list[1].length() < 2){
                monthH = "0" + list[1];
            } else {
                monthH = list[1];
            }

            if(list[0].length() < 2){
                dayH = "0" + list[0];
            } else {
                dayH = list[0];
            }
            birth = yearH + "-" + monthH + "-" + dayH;
            userEditController.getUser().setBirthday(DateUtil.parse(birth));
        } else if (toggleBirthday.isSelected() && !Objects.equals(dayH + "/" + monthH + "/" + yearH,
                (dayOfBirth.getText() + "/" + birthMonth.getText() + "/" + birthYear.getText()))){
            helpMethod1();
        }
    }

    public void helpMethod1(){
        LocalDate bday = this.bean.settingBirthDay(day, month, year);
        userEditController.getUser().setBirthday(bday);
    }
}
