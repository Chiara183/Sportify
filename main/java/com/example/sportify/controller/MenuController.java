package com.example.sportify.controller;

import com.example.sportify.controller.graphic.*;
import com.example.sportify.user.User;
import javafx.scene.control.Button;

public class MenuController extends Controller {

    /** Reference to graphic controller*/
    public MenuGraphicController graphicController;

    /** The variable that identify the name of the view*/
    private ControllerType view;

    /** Instance of the view.*/
    private LoginGraphicController login;
    private EditGraphicController edit;
    private SignUpGraphicController signUp;
    private SignUpGymGraphicController signUpGym;

    /** String that identify the name of last gym loaded*/
    private String gym;

    /** The constructor.*/
    public MenuController() {
        this.type = ControllerType.MENU;
    }

    /** Is called to set the name of the view.*/
    public void setView(ControllerType view) {
        this.view = view;
    }

    /** Method to set the instance of the view.*/
    public void setLogin(LoginGraphicController login) {
        this.login = login;
    }
    public void setSignUp(SignUpGraphicController signUp) {
        this.signUp = signUp;
    }
    public void setSignUpGym(SignUpGymGraphicController signUpGym) {
        this.signUpGym = signUpGym;
    }
    public void setEdit(EditGraphicController edit) {
        this.edit = edit;
    }

    /** Is called to get the name of the view.*/
    public ControllerType getView() {
        return this.view;
    }

    /** Method to get the instance of the view.*/
    public LoginGraphicController getLogin() {
        return this.login;
    }
    public SignUpGraphicController getSignUp() {
        return this.signUp;
    }
    public SignUpGymGraphicController getSignUpGym() {
        return this.signUpGym;
    }
    public EditGraphicController getEdit() {
        return this.edit;
    }

    /** Is called to get the name of gym.*/
    public String getGym() {
        return this.gym;
    }

    /** Is called to set the name of gym.*/
    public void setGym(String gym) {
        this.gym = gym;
    }

    /** Is called to set user.*/
    @Override
    public void setUser(User user) {
        this.user = user;
        graphicController.setUser(this.user!=null);
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (MenuGraphicController) graphicController;
    }

    /** Is called to get signOut button.*/
    public Button getSignOut(){
        return this.graphicController.getSignOut();
    }

    /** The method called to set the view*/
    public void setSportQuiz() {
        graphicController.setSportQuiz();
    }
    public void setFindGym() {
        graphicController.setFindGym();
    }
    public void setLogin() {
        graphicController.setLogin();
    }
    public void setGymInfo(String gym){
        graphicController.setGymInfo(gym);
    }
    public void setMenuDisable(boolean bool){
        mainApp.getPrimaryPane().getTop().setDisable(bool);
    }

    /** It's called to set the color and disable/able of the buttons*/
    public void setButton(Button button1, Button button2, Button button3, Button button4, Button button5, Button buttonOff){
        button1.setStyle("");
        button1.setDisable(false);
        button2.setStyle("");
        button2.setDisable(false);
        button3.setStyle("");
        button3.setDisable(false);
        button4.setStyle("");
        button4.setDisable(false);
        button5.setStyle("");
        button5.setDisable(false);
        buttonOff.setDisable(true);
        buttonOff.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void back() {
        graphicController.back();
    }
}

