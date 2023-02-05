package sportify.controller;

/**
 * Controller class used to manage user information editing.
 *
 * @see EditController
 */
public class UserEditController extends EditController{

    /**
     * Constructor for the UserEditController class.
     * Initializes the type of the controller as USER_EDIT.
     */
    public UserEditController(){
        this.type = ControllerType.USER_EDIT;
    }
}
