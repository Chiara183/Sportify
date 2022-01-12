package com.example.sportify.controller;

import com.example.sportify.user.User;
import com.example.sportify.user.gymUser;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class GymEditController extends EditController{

    public GymEditController(){this.type = ControllerType.GYM_EDIT;}

    @Override
    public void setUser(User user) {
        super.setUser(user);
        setUser((gymUser) user);
    }

    @Override
    public void setUser(gymUser user) {
        gymName.setText(user.getGymName());
        address.setText(user.getAddress());
        telephone.setText(user.getPhone());
        gymNameLabel.setText(user.getGymName());
        addressLabel.setText(user.getAddress());
        telephoneLabel.setText(user.getPhone());
    }

    @Override
    protected void okAction(){
        if (!Objects.equals(user.getUserName(), super.username.getText())) {
            user.setUserName(super.username.getText());
        }
        if (!Objects.equals(user.getPassword(), super.password.getText())) {
            user.setPassword(super.password.getText());
        }
        if (!Objects.equals(user.getFirstName(), super.firstName.getText())) {
            user.setFirstName(super.firstName.getText());
        }
        if (!Objects.equals(user.getLastName(), super.lastName.getText())) {
            user.setLastName(super.lastName.getText());
        }
        if (!Objects.equals(user.getEmail(), super.email.getText())) {
            user.setEmail(super.email.getText());
        }
        if (!Objects.equals(user.getBirthday(), super.date.getValue())) {
            user.setBirthday(super.date.getValue());
        }
        if (!Objects.equals(user.getGymName(), gymName.getText())) {
            user.setFirstName(gymName.getText());
        }
        if (!Objects.equals(user.getAddress(), address.getText())) {
            user.setLastName(address.getText());
        }
        if (!Objects.equals(user.getPhone(), telephone.getText())) {
            user.setEmail(telephone.getText());
        }
        menu.setUser(user);
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void set_toggle_pass_gym(MouseEvent modify) {
        if (modify.getSource() == modify_gymName) {
            if (!toggle_gymName.isSelected()) {
                modify_gymName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_gymName.setSelected(true);
                togglevisible(this.toggle_gymName, this.gymNameLabel, this.gymName);
            } else {
                modify_gymName.setStyle("-fx-text-fill: black;");
                toggle_gymName.setSelected(false);
                togglevisible(this.toggle_gymName, this.gymNameLabel, this.gymName);
            }
        } else if (modify.getSource() == modify_address) {
            if (!toggle_address.isSelected()) {
                modify_address.setStyle("-fx-text-fill: #06B7C5;");
                toggle_address.setSelected(true);
                togglevisible(this.toggle_address, this.addressLabel, this.address);
            } else {
                modify_address.setStyle("-fx-text-fill: black;");
                toggle_address.setSelected(false);
                togglevisible(this.toggle_address, this.addressLabel, this.address);
            }
        } else if (modify.getSource() == modify_telephone) {
            if (!toggle_telephone.isSelected()) {
                modify_telephone.setStyle("-fx-text-fill: #06B7C5;");
                toggle_telephone.setSelected(true);
                togglevisible(this.toggle_telephone, this.telephoneLabel, this.telephone);
            } else {
                modify_telephone.setStyle("-fx-text-fill: black;");
                toggle_telephone.setSelected(false);
                togglevisible(this.toggle_telephone, this.telephoneLabel, this.telephone);
            }
        } else {
            super.set_toggle_pass(modify);
        }
    }
}
