package com.example.sportify.controller;

import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GymEditController extends UserEditController{

    // TextField
    @FXML
    private TextField gymName;
    @FXML
    private TextField address;

    @Override
    public void setUser(User user) {
        super.setUser(user);
        gymName.setText(user.getGymName());
        address.setText(user.getAddress());
    }

    @Override
    protected void okAction(){
        user.setGymName(gymName.getText());
        user.setAddress(address.getText());
        user.setFirstName(super.firstName.getText());
        user.setLastName(super.lastName.getText());
        user.setEmail(super.email.getText());
        user.setBirthday(super.date.getValue());
        menu.setUser(super.user);
        Stage stage = (Stage) super.ok.getScene().getWindow();
        stage.close();
    }
}
