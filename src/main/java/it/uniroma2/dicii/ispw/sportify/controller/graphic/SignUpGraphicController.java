package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.bean.SignUpBean;
import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.SignUpController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpGraphicController extends RegisterGraphicController{

    /** Reference to controller*/
    protected SignUpController signUpController;
    protected Pane pane;
    private MenuGraphicController graphicMenuController;
    protected Pane paneTopScreen;

    /* All the button of the interface*/
    @FXML
    protected Button gymUser;
    @FXML
    protected Button normalUser;

    /* All the text field of the interface*/
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;

    /**Reference to bean*/
    protected final SignUpBean bean = new SignUpBean();

    /* All the checkbox of the interface*/
    @FXML
    private CheckBox gymTick;
    @FXML
    private CheckBox userTick;
    protected boolean user = true;

    /** Controls the sign-up method (user, gym)*/
    @FXML
    private void tickAction(ActionEvent event) {
        if (event.getSource() == gymTick && userTick.isSelected()) {
            userTick.setSelected(false);
        } else if (event.getSource() == userTick && gymTick.isSelected()) {
            gymTick.setSelected(false);
        } else if (event.getSource() == gymTick && !userTick.isSelected()) {
            userTick.setSelected(true);
        } else if (event.getSource() == userTick && !gymTick.isSelected()) {
            gymTick.setSelected(true);
        }
    }

    /** The action of the buttons*/
    @FXML
    protected void submitActionSignUp() {
        if(this.bean.checkEmpty(username) || this.bean.checkEmpty(password) || this.bean.checkEmpty(email)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill username, email and password field");
            alert.showAndWait();
        }
        if(bean.checkForNumbers(firstName) || bean.checkForNumbers(lastName)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill first name and last name fields with letters or leave them empty");
            alert.showAndWait();
        }
        String userValue = username.getText();                  //get user entered username
        String passValue = password.getText();                  //get user entered password
        String nameValue = firstName.getText();                 //get user entered first name
        String lastNameValue = lastName.getText();              //get user entered last name
        String emailString = this.email.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = timestamp.toString();
        date = date.substring(0,10);
        signUpController.submitActionSignUp(userValue, passValue, nameValue, lastNameValue, emailString, date);
    }
    public void submit(ControllerType type){
        if(type == ControllerType.SIGN_UP){
            submitActionSignUp();
        } else {
            user = false;
            submitActionSignUp();
        }
    }

    /** Is called to understand if it's a user*/
    public boolean isUser(){
        return userTick.isSelected();
    }

    /** Is called to set Controller*/
    @Override
    public void setController(Controller contr) {
        this.signUpController = (SignUpController) contr;
        super.setController(contr);
    }

    public void setSignUp(Event event) {
        // Load sign up overview.
        FXMLLoader loaderSignUp = new FXMLLoader();
        helpMethod(event, loaderSignUp);

        // Set sign up overview into the center of root layout.
        SignUpGraphicController graphicController = loaderSignUp.getController();
        helpMethod3(graphicController);
    }

    public void helpMethod3(SignUpGraphicController graphicController){
        SignUpController controllerSignUp = new SignUpController();
        controllerSignUp.setMenu(this.signUpController.getMenu());
        MainApp.getPrimaryPane().setCenter(pane);
        MainApp.getPrimaryPane().setTop(paneTopScreen);
        assert this.graphicMenuController != null;
        this.graphicMenuController.setController(this.signUpController.getMenu());

        // Give the controllerSignUp access to the main app.
        controllerSignUp.setGraphicController(graphicController);
        graphicController.setController(controllerSignUp);
        this.signUpController.getMenu().setGraphicInstance(graphicController);
    }

    public void helpMethod(Event event, FXMLLoader loaderSignUp){
        if (event.getSource() == gymUser) {
            loaderSignUp.setLocation(MainApp.class.getResource("SmartphoneView/SignUpGym0Phone.fxml"));
            helpMethod2(loaderSignUp);
            this.signUpController.getMenu().setView(ControllerType.SIGN_UP_GYM);
        } else if (event.getSource() == normalUser) {
            loaderSignUp.setLocation(MainApp.class.getResource("SmartphoneView/SignUpPhone2.fxml"));
            helpMethod2(loaderSignUp);
            this.signUpController.getMenu().setView(ControllerType.SIGN_UP);
        }
    }

    public void helpMethod2(FXMLLoader loaderSignUp){
        Logger logger = Logger.getLogger(SignUpGraphicController.class.getName());
        FXMLLoader loaderTopScreen = new FXMLLoader();
        loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen2.fxml"));
        try {
            paneTopScreen = loaderTopScreen.load();
        } catch (IOException e) {
            logger.log(Level.WARNING, "I was unable to load TopScreen");
        }
        this.graphicMenuController = loaderTopScreen.getController();
        try {
            pane = loaderSignUp.load();
        } catch (IOException e) {
            logger.log(Level.WARNING, "I was unable to load SignUp");
        }
    }
}
