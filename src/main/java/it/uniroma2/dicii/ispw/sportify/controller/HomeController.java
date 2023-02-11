package it.uniroma2.dicii.ispw.sportify.controller;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.GraphicController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.phone.HomePhoneGraphicController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.MenuGraphicController;
import it.uniroma2.dicii.ispw.sportify.model.domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The HomeController class extends the Controller
 * class and provides functionality for setting the
 * user and graphic controller,
 * as well as creating a new menu in the window.
 *
 * @see Controller
 */
public class HomeController extends Controller {

    /**
     * homeGraphicController is a private field that represents
     * the graphical user interface for the home screen.
     */
    private HomePhoneGraphicController homeGraphicController;

    /**
     * The constructor for the HomeController class,
     * which initializes the necessary fields.
     */
    public HomeController() {
        this.type = ControllerType.HOME;
    }

    /**
     * setUser is a public method that overrides the
     * setUser method in the Controller class and sets
     * the user for the home screen.
     *
     * @param user the User to be set as the
     *             user for the home screen
     */
    @Override
    public void setUser(User user) {
        this.user = user;
        if(MainApp.isNotMobile()) {
            homeGraphicController.getSignIn().setVisible(this.user == null);
            if (this.user != null &&
                    this.user.getRole().equals("gym")) {
                homeGraphicController.getGymInfo().setVisible(true);
                homeGraphicController.getGymInfo().setPrefWidth(141);
                homeGraphicController.getSignIn().setPrefWidth(0);
            }
            else {
                homeGraphicController.getGymInfo().setVisible(false);
                homeGraphicController.getGymInfo().setPrefWidth(0);
                homeGraphicController.getSignIn().setPrefWidth(141);
            }
        }
    }

    /**
     * setGraphicController is a public method that overrides
     * the setGraphicController method in the Controller class
     * and sets the graphicController field.
     *
     * @param graphicController the GraphicController to be set
     *                          as the graphicController field
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.homeGraphicController = (HomePhoneGraphicController) graphicController;
    }

    /**
     * menu is a public method that creates
     * and adds a new menu to the window.
     *
     * @return the MenuController
     * representing the new menu
     */
    public MenuController menu() {
        MenuController controller = MenuController.getInstance();
        URL url;
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            url = MainApp.class.getResource("DesktopView/menu.fxml");
            loaderMenu.setLocation(Objects.requireNonNull(url));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            MainApp.getPrimaryPane().setTop(paneMenu);

            // Give the gymEditController access to the main app.
            MenuGraphicController graphicController = loaderMenu.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(HomeController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return controller;
    }
}