package com.example.sportify.controller.graphic;

import com.example.sportify.OAuth.OAuthGoogleAuthenticator;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginGraphicController extends GraphicController{

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

    /** Reference to controller*/
    private LoginController controller;

    /** Controls the visibility of the password*/
    @FXML
    private void set_toggle_pass(){
        if(!pass_toggle.isSelected()) {
            eye.setStyle("-fx-text-fill: #06B7C5;");
            pass_toggle.setSelected(true);
            controller.getMainApp().togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
        } else {
            eye.setStyle("-fx-text-fill: black;");
            pass_toggle.setSelected(false);
            controller.getMainApp().togglevisiblePassword(this.pass_toggle, this.pass_text, this.password);
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
        controller.submit(userValue, passValue);
    }
    @FXML
    private void login_with_google(){
        String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
        String gRedir = "https://localhost:8080/oauth2";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
        String gSecret = "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";
        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(controller.getMainApp());
    }
    @FXML
    private void skipAction() {
        controller.home();
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (LoginController) controller;
    }
}
