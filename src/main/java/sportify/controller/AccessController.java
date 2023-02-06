package sportify.controller;

import sportify.MainApp;
import sportify.model.dao.Submit;

/**
 * The abstract class {@code AccessController} extends
 * the {@code Controller} class and serves as the base class for
 * all the access controllers in the application. An access controller
 * is responsible for managing the logic behind the
 * login and authentication process, and it communicates with the
 * MainApp and Submit to access the necessary data and
 * services.
 *
 * @see Controller
 */
public abstract class AccessController extends Controller{

    /**
     * The reference to the
     * {@code Submit} instance.
     */
    protected Submit submit;

    /**
     * Overrides the method from the {@code Controller}
     * class to set the main application and initialize the
     * {@code Submit} instance.
     *
     * @param mainApp The reference to the
     *                main application.
     */
    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.submit = new Submit(mainApp);
    }

    /**
     * Sets the {@code Submit} instance for
     * the controller.
     *
     * @param submit The reference to the
     * {@code Submit} instance.
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * Returns the reference to the
     * {@code Submit} instance.
     *
     * @return The reference to the
     * {@code Submit} instance.
     */
    public Submit getSubmit() {
        return this.submit;
    }

    /**
     * Loads the login overview by setting the
     * {@code Submit} instance in the main
     * application and calling the
     * {@code showLoginOverview} method.
     */
    public void login(){
        this.mainApp.setSubmit(this.submit);
        this.mainApp.showLoginOverview();
    }
}
