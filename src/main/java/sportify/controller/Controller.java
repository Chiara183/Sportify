package sportify.controller;

import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.model.domain.User;

/**
 * The abstract class {@code Controller} serves as the
 * base class for all the controllers in the application.
 * A controller is responsible for managing the logic behind
 * a particular part of the UI, and it communicates with
 * the MainApp to access the necessary data and services.
 */
public abstract class Controller{

    /**
     * The type of the controller.
     */
    protected ControllerType type;

    /**
     * The reference to the main application.
     */
    protected MainApp mainApp;

    /**
     * The current user.
     */
    protected User user = null;

    /**
     * The reference to the menu controller.
     */
    protected MenuController menu;

    /**
     * The cache for the search results.
     */
    protected String[] searchCache;

    /**
     * Sets the main application for the controller.
     *
     * @param mainApp The reference to the main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the current user for the controller.
     *
     * @param user The reference to the current user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the menu controller for the controller.
     *
     * @param menu The reference to the menu controller.
     */
    public void setMenu(MenuController menu){
        this.menu = menu;
    }

    /**
     * Sets the cache for the search results.
     *
     * @param search The array of strings that
     *               represent the search results.
     */
    public void setSearchCache(String[] search) {
        this.searchCache = search;
    }

    /**
     * Sets the graphic controller for the controller.
     * This method is abstract and must be overridden by subclasses.
     *
     * @param graphicController The reference to the graphic controller.
     */
    public abstract void setGraphicController(GraphicController graphicController);

    /**
     * Returns the type of the controller.
     *
     * @return The type of the controller.
     */
    public ControllerType getType() {
        return this.type;
    }

    /**
     * Returns the reference to the main application.
     *
     * @return The reference to the main application.
     */
    public MainApp getMainApp() {
        return this.mainApp;
    }

    /**
     * Returns the reference to the current user.
     *
     * @return The reference to the current user.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Returns the reference to the menu controller.
     *
     * @return The reference to the menu controller.
     */
    public MenuController getMenu() {
        return this.menu;
    }
}
