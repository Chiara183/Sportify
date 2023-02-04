package sportify;

import sportify.controller.graphic.LoginGraphicController;

/**
 * This class implements the Adapter pattern on the login
 */
public class AdapterLogin implements Login{

    private LoginGraphicController controller;
    private boolean google;

    /**
     * This method sets the value of the
     * controller field with the value given
     * as the argument.
     *
     * @param controller the value to be set
     */
    public void setController(LoginGraphicController controller) {
        this.controller = controller;
    }

    /**
     * This method sets the value of the
     * Google field with the value given
     * as argument.
     *
     * @param google the value to be set
     */
    public void setGoogle(boolean google) {
        this.google = google;
    }

    /**
     * This method returns the value of
     * the controller field.
     *
     * @return the graphic controller
     */
    public LoginGraphicController getController() {
        return controller;
    }

    /**
     * This method returns the value of
     * the Google field.
     *
     * @return if the user as chosen google login
     */
    public boolean isGoogle() {
        return google;
    }

    /**
     * This is a default constructor for
     * the AdapterLogin class.
     */
    public AdapterLogin(){}

    /**
     * This is a constructor that takes two arguments:
     * controller and google. The constructor calls the
     * setGoogle and setController methods to set the
     * values of the Google and controller fields.
     *
     * @param controller the controller to set.
     * @param google the value to set if user chose
     *               google login
     */
    public AdapterLogin(LoginGraphicController controller, boolean google){
        setGoogle(google);
        setController(controller);
    }

    /**
     * This method implements the Login interface
     * and represents the action to be performed
     * during login. If the value of the Google
     * field is true, the method calls the loginWithGoogle
     * method on the object returned by getController.
     * Otherwise, it calls the submitActionLogin method
     * on the object returned by getController.
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
