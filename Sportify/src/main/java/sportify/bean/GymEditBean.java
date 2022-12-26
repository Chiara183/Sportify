package sportify.bean;

import sportify.DateUtil;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;

public class GymEditBean {

    public boolean checkUser(String user){
        return !user.isEmpty();
    }

    public boolean checkPass(String password){
        return !password.isEmpty();
    }

    public boolean checkEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean checkGymName(String gymName){
        return !gymName.isEmpty();
    }

    public boolean checkTel(String phone){
        return phone.matches("-?\\d+(\\.\\d+)?") || phone.equals("");
    }

    public boolean checkAddress(String address){
        return address.matches("\\w',-\\\\/.\\s");
    }

    public LocalDate settingBday(TextField dayOfBirth, TextField birthMonth, TextField birthYear){
        String year = birthYear.getText();
        String month;
        if(birthMonth.getText().length() < 2){
            month = "0" + birthMonth.getText();
        } else {
            month = birthMonth.getText();
        }

        String day;
        if(dayOfBirth.getText().length() < 2){
            day = "0" + dayOfBirth.getText();
        } else {
            day = dayOfBirth.getText();
        }
        return DateUtil.parse(year + "-" + month + "-" + day);
    }
}
