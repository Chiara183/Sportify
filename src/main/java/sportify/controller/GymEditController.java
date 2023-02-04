package sportify.controller;

import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymEditGraphicController;
import sportify.user.User;
import sportify.user.GymUser;

public class GymEditController extends EditController{

    /** Reference to graphicController*/
    private GymEditGraphicController graphicGymController;

    /** The constructor.*/
    public GymEditController(){this.type = ControllerType.USER_EDIT;}

    /** Sets the user to be edited in the dialog.*/
    @Override
    public void setUser(User user) {
        super.setUser(user);
        graphicGymController.setUser((GymUser) user);
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicGymController = (GymEditGraphicController) graphicController;
        super.setGraphicController(graphicController);
    }
}
