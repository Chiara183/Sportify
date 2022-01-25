package com.example.sportify.controller;

import com.example.sportify.controller.graphic.EditGraphicController;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.user.User;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class EditController extends Controller{

    /** Reference to graphic controller*/
    protected EditGraphicController graphicController;

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

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (EditGraphicController) graphicController;
    }
}
