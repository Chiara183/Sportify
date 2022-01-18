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

    public void togglevisible(CheckBox pass_toggle, Label pass_text, TextField password) {
        if (!pass_toggle.isSelected()) {
            pass_text.setText(password.getText());
            pass_text.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(pass_text.getText());
        password.setVisible(true);
        pass_text.setVisible(false);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (EditGraphicController) graphicController;
    }
}
