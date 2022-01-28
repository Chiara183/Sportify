package com.example.sportify.controller;

import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.GymInfoGraphicController;
import com.example.sportify.controller.graphic.LoginGraphicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Objects;

public class LoginController extends AccessController{

    /** Reference to graphic controller*/
    private LoginGraphicController graphicController;

    /** Delay error login variable, initialize at 3*/
    int delay = 3;
    int seconds = delay;
    Label label = new Label();

    /** Is true when login is in external window*/
    private boolean external;

    /** Set up the external stage*/
    private Stage externalStage;

    /** The constructor.*/
    public LoginController() {
        this.type = ControllerType.LOGIN;
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
            graphicController.getUsernameField().setDisable(true);
            graphicController.getPassField().setDisable(true);
            graphicController.getPasswordField().setDisable(true);
            graphicController.getSubmitButton().setDisable(true);
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.getIcons().add(
                    new Image(
                            Objects.requireNonNull(
                                    mainApp.getClass().getResourceAsStream("Images/Sportify icon.png"))));
            stage.initOwner(mainApp.getPrimaryStage());
            stage.setResizable(false);
            stage.setTitle("WRONG LOGIN");
            stage.initStyle(StageStyle.UNDECORATED);
            label.setTextFill(Color.BLACK);
            label.setFont(Font.font(20));
            seconds = delay;
            label.setText("Waiting... " + seconds);
            doTime();
            StackPane root = new StackPane();
            root.getChildren().add(label);
            stage.setScene(new Scene(root,300, 70));
            stage.showAndWait();
            delay = delay*delay;
            graphicController.getUsernameField().setDisable(false);
            graphicController.getPassField().setDisable(false);
            graphicController.getPasswordField().setDisable(false);
            graphicController.getSubmitButton().setDisable(false);
        }
    }

    private void doTime(){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(delay), event -> {
            seconds--;
            label.setText("Waiting... " + seconds);
            if(seconds<=0){
                Stage stage = (Stage)label.getParent().getScene().getWindow();
                stage.close();
                time.stop();
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (LoginGraphicController) graphicController;
    }
}


