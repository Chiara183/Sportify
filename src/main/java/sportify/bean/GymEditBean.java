package sportify.bean;

import sportify.DateUtil;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

    public boolean checkBday(String dayOfBirth, String birthMonth, String birthYear){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // imposta il formato della data come non permissivo

        try {
            sdf.parse(dayOfBirth+'-'+birthMonth+'-'+birthYear);
            return true;
        } catch (ParseException e) {
            return false;
        }
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
