package it.uniroma2.dicii.ispw.sportify.controller;

import it.uniroma2.dicii.ispw.sportify.MainApp;

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
     * Loads the login overview by setting the
     * {@code Submit} instance in the main
     * application and calling the
     * {@code showLoginOverview} method.
     */
    public void login(){
        MainApp.showOverview(ControllerType.LOGIN);
    }
}
