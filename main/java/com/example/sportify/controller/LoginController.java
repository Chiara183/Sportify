package com.example.sportify.controller;

import com.example.sportify.OAuth.OAuthGoogleAuthenticator;
import com.example.sportify.Submit;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.GymInfoGraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController extends AccessController{

    /** All the text field of the interface*/
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField pass_text;

    //Label
    @FXML
    private Label eye;

    //CheckBox
    @FXML
    private CheckBox pass_toggle;

    /** Is true when login is in external window*/
    private boolean external;

    /** Set up the external stage*/
    private Stage externalStage;

    /** The constructor.*/
    public LoginController() {
        this.type = ControllerType.LOGIN;
        this.submit = new Submit(null);
    }

    /** It's called to set external stage.*/
    public void setStage(Stage stage) {
        this.externalStage = stage;
    }

    /** Is called to set login in external window.*/
    public void setExternal(boolean external) {
        this.external = external;
    }

    /** Controls the visibility of the password*/
    @FXML
    private void set_toggle_pass(){
        if(!pass_toggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #06B7C5;");
            pass_toggle.setSelected(true);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            pass_toggle.setSelected(false);
            mainApp.togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        }
    }

    /** The action of the buttons*/
    @FXML
    protected void submitActionLogin() {
        String userValue = username.getText();      //get user entered username from the textField1
        String passValue;
        if(pass_toggle.isSelected()) {
            passValue = pass_text.getText();      //get user entered password from the textField2
        } else {
            passValue = password.getText();      //get user entered password from the textField2
        }

        //check whether the credentials are authentic or not
        if (this.submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            this.user = this.submit.setUser(userValue);
            if(!external) {
                home();
            } else {
                this.mainApp.setUser(this.user);
                this.menu.setUser(this.user);
                MenuController menu = this.mainApp.Menu();
                menu.setUser(this.user);
                GymInfoGraphicController graphicController = new GymInfoGraphicController();
                GymInfoController gym = new GymInfoController();
                graphicController.setController(gym);
                gym.setGraphicController(graphicController);
                if (Objects.equals(this.menu.getView(), ControllerType.GYM_INFO)){
                    menu.setGymInfo(this.menu.getGym());
                    gym.setMainApp(this.mainApp);
                    gym.setUser(this.user);
                    gym.setMenu(menu);
                    gym.setSearchCache(this.search_cache);
                    gym.loadingGymName(this.menu.getGym());
                } else if (Objects.equals(this.menu.getView(), ControllerType.FIND_GYM) && this.menu.getGym() != null){
                    menu.setFindGym();
                    menu.setGym(this.menu.getGym());
                    gym.setMainApp(this.mainApp);
                    gym.setUser(this.user);
                    gym.setMenu(menu);
                    gym.setSearchCache(this.search_cache);
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
    @FXML
    private void login_with_google(){
        String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
        String gRedir = "https://localhost:8080/oauth2";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
        String gSecret = "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";
        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(this.mainApp);
    }
    @FXML
    private void skipAction() {
        home();
    }

    /** It's called to load the home overview*/
    private void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    @Override
    public void setGraphicController(GraphicController graphicController) {
        //TODO
    }
}


