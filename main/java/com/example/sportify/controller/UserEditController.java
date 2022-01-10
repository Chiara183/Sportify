package com.example.sportify.controller;

import com.example.sportify.user.User;
import com.example.sportify.user.gymUser;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Objects;

public class UserEditController extends EditController{

    @Override
    public void setUser(gymUser user) {
        //TODO
    }

    @Override
    @FXML
    protected void okAction() {
        if (!Objects.equals(user.getUserName(), username.getText())) {
            user.setUserName(username.getText());
        }
        if (!Objects.equals(user.getPassword(), password.getText())) {
            user.setPassword(password.getText());
        }
        if (!Objects.equals(user.getFirstName(), firstName.getText())) {
            user.setFirstName(firstName.getText());
        }
        if (!Objects.equals(user.getLastName(), lastName.getText())) {
            user.setLastName(lastName.getText());
        }
        if (!Objects.equals(user.getEmail(), email.getText())) {
            user.setEmail(email.getText());
        }
        if (!Objects.equals(user.getBirthday(), date.getValue())) {
            user.setBirthday(date.getValue());
        }
        menu.setUser(user);
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }
}
