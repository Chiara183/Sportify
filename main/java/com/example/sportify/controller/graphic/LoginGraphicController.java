package com.example.sportify.controller.graphic;

import com.example.sportify.AdapterLogin;
import com.example.sportify.auth.OAuthGoogleAuthenticator;
import com.example.sportify.bean.LoginBean;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginGraphicController extends AccessGraphicController{

    private boolean google;
    /** All the text field of the interface*/
    @FXML
    private TextField user;
    @FXML
    private TextField passText;

    /** All the button of the interface*/
    @FXML
    private Button submit;
    @FXML
    private Button skip;

    /** Reference to controller*/
    private LoginController controller;

    /**Reference to bean instance*/
    private final LoginBean bean = new LoginBean();

    /** Get method*/
    public TextField getUsernameField(){
        return this.user;
    }
    public TextField getPassField(){
        return this.passText;
    }
    public TextField getPasswordField(){
        return this.password;
    }
    public Button getSkipButton(){
        return this.skip;
    }
    public Button getSubmitButton(){
        return this.submit;
    }


    @FXML
    public void setGoogle(){
        this.google = true;
        makeLogin();
    }

    @FXML
    public void makeLogin(){
        AdapterLogin adapterLogin;
        if(this.google){
            adapterLogin = new AdapterLogin(this, true);
        }else{
            adapterLogin = new AdapterLogin(this, false);
        }
        adapterLogin.doLogin();
    }
    /** The action of the buttons*/
    public void submitActionLogin() {
        String userValue = "";
        if(bean.userCheck(user.getText())) {
            userValue = user.getText(); //get user entered username from the textField1
        }else{
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("User is empty");
            alert.setHeaderText("The user field is empty");
            alert.setContentText("Please enter a username.");
            alert.showAndWait();
        }
        String passValue;
        if(passToggle.isSelected()) {
            if(bean.passCheck(passText.getText())){
                passValue = passText.getText(); //get user entered password from the textField2
                controller.submit(userValue, passValue);
            }else{
                alert();
            }
        } else {
            if(bean.passCheck(password.getText())){
                passValue = password.getText(); //get user entered password from the textField2
                controller.submit(userValue, passValue);
            }else{
                alert();
            }
        }
    }

    public void alert(){
        //show error message
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(controller.getMainApp().getPrimaryStage());
        alert.setTitle("Password is empty");
        alert.setHeaderText("The password field is empty");
        alert.setContentText("Please enter a password.");
        alert.showAndWait();
    }

    public void loginWithGoogle(){
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
    @FXML
    private void signUpAction(){
        controller.getMainApp().showSignUpOverview();
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (LoginController) controller;
        super.setController(controller);
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
