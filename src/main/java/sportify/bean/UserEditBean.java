package sportify.bean;

import sportify.util.DateUtil;

import java.time.LocalDate;

/**
 * The UserEditBean class represents a bean for editing user information.
 */
public class UserEditBean {

    /**
     * This method takes the day, month and year of a
     * user's birthdate and returns the date in the
     * format of LocalDate.
     *
     * @param dayOfBirth the day of the user's birth
     * @param birthMonth the month of the user's birth
     * @param birthYear the year of the user's birth
     *
     * @return the user's birthdate in LocalDate format
     */
    public LocalDate settingBirthDay(int dayOfBirth, int birthMonth, int birthYear){
        String month;
        LocalDate result;
        String date;
        if(birthMonth < 10){
            month = "0" + birthMonth;
        }
        else {
            month = String.valueOf(birthMonth);
        }

        String day;
        if(dayOfBirth < 10){
            day = "0" + dayOfBirth;
        }
        else {
            day = String.valueOf(dayOfBirth);
        }
        String year = String.valueOf(birthYear);
        date = year + "-" + month + "-" + day;
        result = DateUtil.parse(date);
        return result;
    }
}

