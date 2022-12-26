package com.example.sportify.controller;

import com.example.sportify.controller.graphic.EditGraphicController;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.user.User;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public abstract class EditController extends Controller{

    /** Reference to graphic controller*/
    protected EditGraphicController graphicController;

    /** The variable that identify the name of the view*/
    protected ControllerType view;

    /** Is called to set the name of the view.*/
    public void setView(ControllerType view) {
        this.view = view;
    }

    /** Is called to get the name of the view.*/
    public ControllerType getView() {
        return this.view;
    }

    /** Sets the user to be edited in the dialog.*/
    @Override
    public void setUser(User user){
        this.user = user;
        graphicController.setUser(user);
    }

    public void togglevisible(CheckBox passToggle, Label passText, TextField password) {
        if (!passToggle.isSelected()) {
            passText.setText(password.getText());
            passText.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(passText.getText());
        password.setVisible(true);
        passText.setVisible(false);
    }

    public void togglevisibleBirthday(CheckBox passToggle, Label passText, Pane password, TextField birthDay, TextField birthMonth, TextField birthYear) {
        if (!passToggle.isSelected()) {
            passText.setText(birthDay.getText() +
                    "/" + birthMonth.getText() +
                    "/" + birthYear.getText());
            passText.setVisible(true);
            password.setVisible(false);
            return;
        }
        String birth = passText.getText();
        String[] birth1 = birth.split("/");
        birthDay.setText(birth1[0]);
        birthMonth.setText(birth1[1]);
        birthYear.setText(birth1[2]);
        password.setVisible(true);
        passText.setVisible(false);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (EditGraphicController) graphicController;
    }
}
