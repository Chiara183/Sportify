package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.GymEditController;
import com.example.sportify.user.gymUser;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class GymEditGraphicController extends EditGraphicController{

    /** Reference to controller*/
    private GymEditController controller;

    public void setUser(gymUser user) {
        gymName.setText(user.getGymName());
        address.setText(user.getAddress());
        telephone.setText(user.getPhone());
        gymNameLabel.setText(user.getGymName());
        addressLabel.setText(user.getAddress());
        telephoneLabel.setText(user.getPhone());
    }

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        if (!Objects.equals(controller.getUser().getUserName(), super.username.getText())) {
            controller.getUser().setUserName(super.username.getText());
        }
        if (!Objects.equals(controller.getUser().getPassword(), super.password.getText())) {
            controller.getUser().setPassword(super.password.getText());
        }
        if (!Objects.equals(controller.getUser().getFirstName(), super.firstName.getText())) {
            controller.getUser().setFirstName(super.firstName.getText());
        }
        if (!Objects.equals(controller.getUser().getLastName(), super.lastName.getText())) {
            controller.getUser().setLastName(super.lastName.getText());
        }
        if (!Objects.equals(controller.getUser().getEmail(), super.email.getText())) {
            controller.getUser().setEmail(super.email.getText());
        }
        if (!Objects.equals(controller.getUser().getBirthday(), super.date.getValue())) {
            controller.getUser().setBirthday(super.date.getValue());
        }
        if (!Objects.equals(controller.getUser().getGymName(), gymName.getText())) {
            controller.getUser().setFirstName(gymName.getText());
        }
        if (!Objects.equals(controller.getUser().getAddress(), address.getText())) {
            controller.getUser().setLastName(address.getText());
        }
        if (!Objects.equals(controller.getUser().getPhone(), telephone.getText())) {
            controller.getUser().setEmail(telephone.getText());
        }
        controller.getMenu().setUser(controller.getUser());
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    /** Controls the modifiability of all field*/
    @FXML
    protected void set_toggle_pass_gym(MouseEvent modify) {
        if (modify.getSource() == modify_gymName) {
            if (!toggle_gymName.isSelected()) {
                modify_gymName.setStyle("-fx-text-fill: #06B7C5;");
                toggle_gymName.setSelected(true);
                controller.togglevisible(this.toggle_gymName, this.gymNameLabel, this.gymName);
            } else {
                modify_gymName.setStyle("-fx-text-fill: black;");
                toggle_gymName.setSelected(false);
                controller.togglevisible(this.toggle_gymName, this.gymNameLabel, this.gymName);
            }
        } else if (modify.getSource() == modify_address) {
            if (!toggle_address.isSelected()) {
                modify_address.setStyle("-fx-text-fill: #06B7C5;");
                toggle_address.setSelected(true);
                controller.togglevisible(this.toggle_address, this.addressLabel, this.address);
            } else {
                modify_address.setStyle("-fx-text-fill: black;");
                toggle_address.setSelected(false);
                controller.togglevisible(this.toggle_address, this.addressLabel, this.address);
            }
        } else if (modify.getSource() == modify_telephone) {
            if (!toggle_telephone.isSelected()) {
                modify_telephone.setStyle("-fx-text-fill: #06B7C5;");
                toggle_telephone.setSelected(true);
                controller.togglevisible(this.toggle_telephone, this.telephoneLabel, this.telephone);
            } else {
                modify_telephone.setStyle("-fx-text-fill: black;");
                toggle_telephone.setSelected(false);
                controller.togglevisible(this.toggle_telephone, this.telephoneLabel, this.telephone);
            }
        } else {
            super.set_toggle_pass(modify);
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (GymEditController) controller;
        super.setController(controller);
    }
}
