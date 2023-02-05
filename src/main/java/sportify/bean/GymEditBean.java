package sportify.bean;

import sportify.DateUtil;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * The `GymEditBean` class provides
 * methods for validating user input
 * when editing gym information.
 */
public class GymEditBean {

    /**
     * Validates the provided username to make
     * sure it is not empty.
     *
     * @param user The username to be validated
     *
     * @return `true` if the username is not empty,
     * `false` otherwise
     */
    public boolean checkUser(String user){
        return !user.isEmpty();
    }

    /**
     * Validates the provided password to make
     * sure it is not empty.
     *
     * @param password The password to be validated
     *
     * @return `true` if the password is not empty,
     * `false` otherwise
     */
    public boolean checkPass(String password){
        return !password.isEmpty();
    }

    /**
     * Validates the provided email address to
     * make sure it is a valid email address.
     *
     * @param email The email address to be validated
     *
     * @return `true` if the email address is
     * valid, `false` otherwise
     */
    public boolean checkEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Validates the provided gym name to make
     * sure it is not empty.
     *
     * @param gymName The gym name to be validated
     *
     * @return `true` if the gym name is not empty,
     * `false` otherwise
     */
    public boolean checkGymName(String gymName){
        return !gymName.isEmpty();
    }

    /**
     * Validates the provided phone number
     * to make sure it is a valid number.
     *
     * @param phone The phone number to be validated
     *
     * @return `true` if the phone number
     * is a valid number, `false` otherwise
     */
    public boolean checkTel(String phone){
        return phone.matches("-?\\d+(\\.\\d+)?") || phone.equals("");
    }

    /**
     * Validates the provided address to make
     * sure it is a valid address.
     *
     * @param address The address to be validated
     *
     * @return `true` if the address is a valid
     * address, `false` otherwise
     */
    public boolean checkAddress(String address){
        return address.matches("\\w',-\\\\/.\\s");
    }

    /**
     * Validates the provided date of birth to make sure it is a valid date.
     *
     * @param dayOfBirth The day of birth to be validated
     * @param birthMonth The birth month to be validated
     * @param birthYear The birth year to be validated
     *
     * @return `true` if the date of birth is a valid date, `false` otherwise
     */
    public boolean checkBday(String dayOfBirth, String birthMonth, String birthYear){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // imposta il formato della data come non permissivo

        try {
            sdf.parse(dayOfBirth+'-'+birthMonth+'-'+birthYear);
            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }

    /**
     * This method sets the birthday of the user.
     *
     * @param dayOfBirth The day of the birthday in "dd" format.
     * @param birthMonth The month of the birthday in "MM" format.
     * @param birthYear The year of the birthday in "yyyy" format.
     *
     * @return Returns the birthday in `LocalDate` format.
     */
    public LocalDate settingBday(TextField dayOfBirth, TextField birthMonth, TextField birthYear){
        String year = birthYear.getText();
        String month;
        LocalDate result;
        if(birthMonth.getText().length() < 2){
            month = "0" + birthMonth.getText();
        }
        else {
            month = birthMonth.getText();
        }

        String day;
        if(dayOfBirth.getText().length() < 2){
            day = "0" + dayOfBirth.getText();
        }
        else {
            day = dayOfBirth.getText();
        }
        result = DateUtil.parse(year + "-" + month + "-" + day);
        return result;
    }
}
