package com.example.sportify.controller;

import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.HomeGraphicController;
import com.example.sportify.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class HomeController extends Controller {

    /** Reference to graphic controller*/
    private HomeGraphicController graphicController;

    /** The constructor.*/
    public HomeController() {
        this.type = ControllerType.HOME;
    }

    /** Is called to set user.*/
    @Override
    public void setUser(User user) {
        this.user = user;
        graphicController.getSignIn().setVisible(this.user == null);
        if(this.user!= null && this.user.getRole().equals("gym")){
            graphicController.getGymInfo().setVisible(true);
            graphicController.getGymInfo().setPrefWidth(141);
            graphicController.getSignIn().setPrefWidth(0);
        } else {
            graphicController.getGymInfo().setVisible(false);
            graphicController.getGymInfo().setPrefWidth(0);
            graphicController.getSignIn().setPrefWidth(141);
        }
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (HomeGraphicController) graphicController;
    }

    /** It's called to create and ad a new menu in the window*/
    public MenuController Menu() {
        MenuController controllerB = null;
        try {
            FXMLLoader loaderMenu = new FXMLLoader();
            loaderMenu.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Menu.fxml")));
            Pane paneMenu = loaderMenu.load();

            // Set menu overview into the top of root layout.
            this.mainApp.getPrimaryPane().setTop(paneMenu);

            // Give the controller access to the main app.
            controllerB = loaderMenu.getController();
            controllerB.setMainApp(this.mainApp);
            controllerB.setUser(this.user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return controllerB;
    }
}