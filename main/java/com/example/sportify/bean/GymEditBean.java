package com.example.sportify.bean;

import org.apache.commons.validator.routines.EmailValidator;

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
}
