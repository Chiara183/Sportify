package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.OAuth.OAuthFacebookAuthenticator;
import com.example.sportify.OAuth.OAuthGithubAuthenticator;
import com.example.sportify.OAuth.OAuthGoogleAuthenticator;
import com.example.sportify.Submit;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    //TextField
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField pass_text;

    //CheckBox
    @FXML
    private CheckBox pass_toggle;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to submit.
    private Submit submit;

    // User
    private User user;

    /**
     * The constructor.
     */
    public LoginController() {
        this.submit = new Submit();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called to set user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Is called to set submit.
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * Controls the visibility of the Password field
     */
    @FXML
    public void togglevisiblePassword() {
        if (pass_toggle.isSelected()) {
            pass_text.setText(password.getText());
            pass_text.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(pass_text.getText());
        password.setVisible(true);
        pass_text.setVisible(false);
    }

    @FXML
    protected void submitActionLogin() {
        String userValue = username.getText();      //get user entered username from the textField1
        String passValue = password.getText();      //get user entered password from the textField2

        //check whether the credentials are authentic or not
        if (this.submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            this.user = this.submit.setUser(userValue);
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Correct!");
            home();
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

    private void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }

    @FXML
    private void login_with_google(){
        String gClientId = "941217546228-08fmsjebj3jn1a0agnt9tu9tnijgn2pq.apps.googleusercontent.com";
        String gRedir = "https://localhost:9191/oauth2";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile";
        String gSecret = "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";
        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(this.mainApp);
    }

    @FXML
    private void login_with_facebook(){
        String FACEBOOK_clientID = "497348525027976";
        String FACEBOOK_redirectUri = "https://localhost:9191/oauth2";
        String FACEBOOK_fieldsString = "name,gender,id";
        String FACEBOOK_clientSecret = "78069fee1d59a5ee89e7061116c40320";
        OAuthFacebookAuthenticator authFB = new OAuthFacebookAuthenticator(FACEBOOK_clientID, FACEBOOK_redirectUri, FACEBOOK_clientSecret, FACEBOOK_fieldsString);
        authFB.startLogin(this.mainApp);
    }

    @FXML
    private void login_with_git(){
        String GIT_clientID = "############";
        String GIT_redirectUri = "############";
        String GIT_scope = "user";
        String GIT_clientSecret = "##############";
        OAuthGithubAuthenticator authGit = new OAuthGithubAuthenticator(GIT_clientID, GIT_redirectUri, GIT_clientSecret, GIT_scope);
        authGit.startLogin(this.mainApp);
    }

    @FXML
    private void skipAction() {
        home();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}


