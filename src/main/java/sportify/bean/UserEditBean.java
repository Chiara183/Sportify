package sportify.bean;

import sportify.DateUtil;

import java.time.LocalDate;

public class UserEditBean {
        public LocalDate settingBirthDay(int dayOfBirth, int birthMonth, int birthYear){
            String month;
            if(birthMonth < 10){
                month = "0" + birthMonth;
            } else {
                month = String.valueOf(birthMonth);
            }

            String day;
            if(dayOfBirth < 10){
                day = "0" + dayOfBirth;
            } else {
                day = String.valueOf(dayOfBirth);
            }
            String year = String.valueOf(birthYear);
            return DateUtil.parse(year + "-" + month + "-" + day);
        }
}

