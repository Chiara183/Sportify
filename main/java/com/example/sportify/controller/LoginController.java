package com.example.sportify.controller;

import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.GymInfoGraphicController;
import com.example.sportify.controller.graphic.LoginGraphicController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController extends AccessController{

    /** Is true when login is in external window*/
    private boolean external;

    /** Set up the external stage*/
    private Stage externalStage;

    private Submit submit;

    /** The constructor.*/
    public LoginController() {
        this.type = ControllerType.LOGIN;
        this.submit = new Submit(this.mainApp);
    }

    /** It's called to set external stage.*/
    public void setStage(Stage stage) {
        this.externalStage = stage;
    }

    /** Is called to set login in external window.*/
    public void setExternal(boolean external) {
        this.external = external;
    }

    /** It's called to load the home overview*/
    public void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    /** Check whether the credentials are authentic or not and do the right action*/
    public void submit(String userValue, String passValue){
        if (this.submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            this.user = this.submit.setUser(userValue);
            if(!external) {
                home();
            } else {
                this.mainApp.setUser(this.user);
                this.menu.setUser(this.user);
                MenuController menu = this.mainApp.menu();
                menu.setUser(this.user);
                GymInfoGraphicController gymInfoGraphicController = new GymInfoGraphicController();
                GymInfoController gym = new GymInfoController();
                gymInfoGraphicController.setController(gym);
                gym.setGraphicController(gymInfoGraphicController);
                if (Objects.equals(this.menu.getView(), ControllerType.GYM_INFO)){
                    menu.setGymInfo(this.menu.getGym());
                    gym.setMainApp(this.mainApp);
                    gym.setUser(this.user);
                    gym.setMenu(menu);
                    gym.setSearchCache(this.searchCache);
                    gym.loadingGymName(this.menu.getGym());
                } else if (Objects.equals(this.menu.getView(), ControllerType.FIND_GYM) && this.menu.getGym() != null){
                    menu.setFindGym();
                    menu.setGym(this.menu.getGym());
                    gym.setMainApp(this.mainApp);
                    gym.setUser(this.user);
                    gym.setMenu(menu);
                    gym.setSearchCache(this.searchCache);
                    gym.loadingGymName(this.menu.getGym());
                }
                Stage stage = this.externalStage;
                stage.close();
            }
        } else {
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Wrong Username or Password");
            alert.setHeaderText("You wrote wrong username or password");
            alert.setContentText("Please enter valid username and password or Signup");
            alert.showAndWait();
        }
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        /* Reference to graphic controller*/
    }
}


