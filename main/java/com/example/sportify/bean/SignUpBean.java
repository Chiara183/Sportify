package com.example.sportify.bean;

import javafx.scene.control.TextField;


public class SignUpBean {

    public boolean checkEmpty(TextField str){
        return !str.getText().equals("");
    }

    public boolean checkForNumbers(TextField str){
        String s = str.getText();
        return s.isEmpty() || !s.matches(".*\\d.*");
    }
}
