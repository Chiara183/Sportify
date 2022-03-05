package com.example.sportify.bean;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class GymInfoBean {

    public String getHour(Slider hour){
        return String.valueOf((int)hour.getValue());
    }

    public String getMin(Slider min){
        return String.valueOf((int)min.getValue());
    }

    public boolean checkReview(Label gymName, TextArea reviewArea){
        return !gymName.getText().equals("") && !reviewArea.getText().equals("");
    }

    public boolean checkPhoneReview(String gymName, TextArea reviewArea){
        return !gymName.equals("") && !reviewArea.getText().equals("");
    }
}
