package com.example.sportify.controller;

import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.GymEditGraphicController;
import com.example.sportify.user.User;
import com.example.sportify.user.gymUser;

public class GymEditController extends EditController{

    /** Reference to graphic controller*/
    private GymEditGraphicController graphicController;

    /** The constructor.*/
    public GymEditController(){this.type = ControllerType.GYM_EDIT;}

    /** Sets the user to be edited in the dialog.*/
    @Override
    public void setUser(User user) {
        super.setUser(user);
        graphicController.setUser((gymUser) user);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (GymEditGraphicController) graphicController;
        super.setGraphicController(graphicController);
    }
}
