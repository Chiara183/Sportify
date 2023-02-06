package sportify.pattern;

import sportify.controller.Login;
import sportify.controller.graphic.LoginGraphicController;

/**
 * Class that implements the Login interface
 * and acts as an pattern for the
 * LoginGraphicController class.
 *
 * @see Login
 */
public class AdapterLogin implements Login{

    private LoginGraphicController controller;
    private boolean google;

    /**
     * Sets the LoginGraphicController object.
     *
     * @param controller The LoginGraphicController
     *                  object to be set.
     */
    public void setController(LoginGraphicController controller) {
        this.controller = controller;
    }

    /**
     * Sets the value for the 'google' variable.
     *
     * @param google A boolean value to set
     *              whether login will be done
     *              through Google or not.
     */
    public void setGoogle(boolean google) {
        this.google = google;
    }

    /**
     * Gets the LoginGraphicController object.
     *
     * @return The LoginGraphicController object.
     */
    public LoginGraphicController getController() {
        return controller;
    }

    /**
     * Gets the value of the 'google' variable.
     *
     * @return A boolean value indicating
     * whether login will be done through
     * Google or not.
     */
    public boolean isGoogle() {
        return google;
    }

    /**
     * The constructor.
     */
    public AdapterLogin(){}

    /**
     * Parameterized constructor.
     *
     * @param controller The LoginGraphicController
     *                  object to be set.
     * @param google A boolean value to set whether
     *              login will be done through Google
     *              or not.
     */
    public AdapterLogin(LoginGraphicController controller, boolean google){
        setGoogle(google);
        setController(controller);
    }

    /**
     * Override of the doLogin method defined
     * in the Login interface.
     * Calls either the loginWithGoogle()
     * method or the submitActionLogin()
     * method of the LoginGraphicController
     * object, depending on the value of the
     * 'google' variable.
     */
    @Override
    public void doLogin() {
        if(isGoogle()){
            getController().loginWithGoogle();
        }
        else{
            getController().submitActionLogin();
        }

    }
}
