package com.example.sportify.controller.graphic;

import com.example.sportify.bean.GymEditBean;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.GymEditController;
import com.example.sportify.user.GymUser;
import javafx.fxml.FXML;

import java.util.Objects;

public class GymEditGraphicController extends EditGraphicController{
    protected static final String STYLE = "-fx-text-fill: #06B7C5;";
    protected static final String COLOR = "-fx-text-fill: black;";

    /** Reference to controller*/
    protected GymEditController controller;

    /** Reference to bean*/
    protected final GymEditBean bean = new GymEditBean();


    public void setUser(GymUser user) {
        gymName.setText(user.getGymName());
        address.setText(user.getAddress());
        telephone.setText(user.getPhone());
        gymNameLabel.setText(user.getGymName());
        addressLabel.setText(user.getAddress());
        telephoneLabel.setText(user.getPhone());
    }

    public void checkSyntax(){
        bean.checkUser(super.username.getText());
        bean.checkPass(super.password.getText());
        bean.checkEmail(super.email.getText());
        bean.checkGymName(super.gymName.getText());
        bean.checkTel(super.telephone.getText());
        bean.checkAddress(super.address.getText());
        //TODO birthday bean check
    }

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        this.checkSyntax();
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
        if (!Objects.equals(controller.getUser().getGymName(), gymName.getText())) {
            controller.getUser().setFirstName(gymName.getText());
        }
        if (!Objects.equals(controller.getUser().getAddress(), address.getText())) {
            controller.getUser().setLastName(address.getText());
        }
        if (!Objects.equals(controller.getUser().getPhone(), telephone.getText())) {
            controller.getUser().setEmail(telephone.getText());
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (GymEditController) controller;
        super.setController(controller);
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
