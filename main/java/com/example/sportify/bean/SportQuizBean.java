package com.example.sportify.bean;

public class SportQuizBean {

    public boolean checkIsNumeric(String s){
        return s.matches("[0-9]+");
    }

    public boolean checkEnv(String env){
        return env.equals("indoor") || env.equals("outdoor");
    }

    public boolean checkType(String type){
        return type.equals("single") || type.equals("group");
    }
}