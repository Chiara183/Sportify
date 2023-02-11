package it.uniroma2.dicii.ispw.sportify.controller;

import it.uniroma2.dicii.ispw.sportify.controller.graphic.GraphicController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.GymEditGraphicController;
import it.uniroma2.dicii.ispw.sportify.model.domain.User;
import it.uniroma2.dicii.ispw.sportify.model.domain.GymUser;

/**
 * This class extends the EditController class and
 * implements its own methods to specifically handle
 * editing of gym users.
 *
 * @see EditController
 */
public class GymEditController extends EditController{

    /**
     * Reference to the {@link GymEditGraphicController}
     * object used to manage the graphical representation
     * of the data.
     */
    private GymEditGraphicController graphicGymController;

    /**
     * Constructor for the {@link GymEditController} class.
     * This sets the type of the controller to
     * {@link ControllerType#USER_EDIT}.
     */
    public GymEditController(){this.type = ControllerType.USER_EDIT;}

    /**
     * Overrides the parent class method to set the user
     * to be edited in the dialog.
     * The user is cast to a {@link GymUser} object before
     * being passed to the graphic controller.
     *
     * @param user The {@link User} object to be edited.
     */
    @Override
    public void setUser(User user) {
        super.setUser(user);
        graphicGymController.setUser((GymUser) user);
    }

    /**
     * Overrides the parent class method to set the graphic controller used
     * by the class.
     * The graphic controller is cast to a {@link GymEditGraphicController}
     * object before being stored.
     *
     * @param graphicController The {@link GraphicController} object
     *                          to be used by the class.
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicGymController = (GymEditGraphicController) graphicController;
        super.setGraphicController(graphicController);
    }
}
