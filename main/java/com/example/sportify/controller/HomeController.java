package com.example.sportify.controller;

import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.HomeGraphicController;
import com.example.sportify.controller.graphic.MenuGraphicController;
import com.example.sportify.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController extends Controller {

    /** Reference to graphic controller*/
    private HomeGraphicController homeGraphicController;

    /** The constructor.*/
    public HomeController() {
        this.type = ControllerType.HOME;
    }

    /** Is called to set user.*/
    @Override
    public void setUser(User user) {
        this.user = user;
        if(!this.mainApp.isMobile()) {
            homeGraphicController.getSignIn().setVisible(this.user == null);
            if (this.user != null && this.user.getRole().equals("gym")) {
                homeGraphicController.getGymInfo().setVisible(true);
                homeGraphicController.getGymInfo().setPrefWidth(141);
                homeGraphicController.getSignIn().setPrefWidth(0);
            } else {
                homeGraphicController.getGymInfo().setVisible(false);
                homeGraphicController.getGymInfo().setPrefWidth(0);
                homeGraphicController.getSignIn().setPrefWidth(141);
            }
        } else {
            //TODO setMobileUser
        }
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.homeGraphicController = (HomeGraphicController) graphicController;
    }

    /** It's called to create and ad a new menu in the window*/
    public MenuController Menu() {
        MenuController controller = new MenuController();
        controller.setMainApp(this.mainApp);
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.mainApp.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            MenuGraphicController graphicController = loaderMenu.getController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(HomeController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            Logger logger1 = Logger.getLogger(HomeController.class.getName());
            logger1.log(Level.SEVERE, e.getLocalizedMessage());
        }
        return controller;
    }
}