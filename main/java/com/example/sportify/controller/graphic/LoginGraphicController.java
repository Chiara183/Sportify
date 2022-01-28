package com.example.sportify.controller.graphic;

import com.example.sportify.auth.OAuthGoogleAuthenticator;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginGraphicController extends AccessGraphicController{

    /** All the text field of the interface*/
    @FXML
    private TextField username;

    /** All the button of the interface*/
    @FXML
    private Button submit;

    /** Reference to controller*/
    private LoginController controller;

    /** Get method*/
    public TextField getUsernameField(){
        return this.username;
    }
    public TextField getPassField(){
        return this.passText;
    }
    public TextField getPasswordField(){
        return this.password;
    }
    public Button getSubmitButton(){
        return this.submit;
    }

    /** The action of the buttons*/
    @FXML
    protected void submitActionLogin() {
        String userValue = username.getText();      //get user entered username from the textField1
        String passValue;
        if(passToggle.isSelected()) {
            passValue = passText.getText();      //get user entered password from the textField2
        } else {
            passValue = password.getText();      //get user entered password from the textField2
        }

        //check whether the credentials are authentic or not
        controller.submit(userValue, passValue);
    }
    @FXML
    private void loginWithGoogle(){
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
        super.setController(controller);
    }
}
