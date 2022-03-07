package com.example.sportify.controller.graphic;

import com.example.sportify.MainApp;
import com.example.sportify.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPhoneGraphicController extends MenuGraphicController {

    @Override
    @FXML
    protected void signOut() {
        controller.setUser(null);
        controller.getMainApp().setUser(null);
        homeAction();
    }

    @Override
    @FXML
    public void findGymAction() {
        controller.setView(ControllerType.FIND_GYM);
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showFindGymOverview(controller);
    }

    @Override
    @FXML
    public void loadGymInfo() {
        GymInfoPhoneGraphicController graphicController = new GymInfoPhoneGraphicController();
        GymInfoController gym = new GymInfoController();
        graphicController.setController(gym);
        helpMethod1(gym, graphicController);
    }

    @Override
    @FXML
    public void sportQuizAction() {
        controller.setView(ControllerType.SPORT_QUIZ);
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showSportQuizOverview(controller);
    }

    @Override
    @FXML
    public void signLoginAction() {
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showLoginOverview();
    }

    @Override
    @FXML
    protected void openUserInterface() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            EditController editController;
            Pane paneTopScreen;
            MenuPhoneGraphicController graphicMenuController;
            if (Objects.equals(this.controller.getUser().getRole(), "gym")) {
                loader.setLocation(MainApp.class.getResource("SmartphoneView/GymEditDialogPhone5.fxml"));
                editController = new GymEditController();
            } else {
                loader.setLocation(MainApp.class.getResource("SmartphoneView/UserEditDialogPhone.fxml"));
                editController = new UserEditController();
            }
            FXMLLoader loaderTopScreen = new FXMLLoader();
            loaderTopScreen.setLocation(MainApp.class.getResource("SmartphoneView/topScreen5.fxml"));
            paneTopScreen = loaderTopScreen.load();
            graphicMenuController = loaderTopScreen.getController();
            AnchorPane page = loader.load();

            controller.getMainApp().getPrimaryPane().setCenter(page);
            controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
            assert graphicMenuController != null;
            graphicMenuController.setController(this.controller);

            // Set the person into the editController.
            helpMethod2(loader, editController);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MenuGraphicController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    @Override
    @FXML
    public void back(){
        if(controller.getView()==ControllerType.LOGIN) {
            homeAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ) {
            homeAction();
        } else if(controller.getView()==ControllerType.GYM_INFO) {
            findGymAction();
        } else if(controller.getView()==ControllerType.FIND_GYM) {
            homeAction();
        } else if(controller.getView()==ControllerType.USER_KIND) {
            signAction();
        } else if(controller.getView()==ControllerType.SPORT_INFO) {
            homeAction();
        }else if(controller.getView()==ControllerType.USER_EDIT) {
            helpMethod();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            signLoginAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
            sportQuizAction();
        } else if(controller.getView()==ControllerType.COURSE_GYM || controller.getView()==ControllerType.REVIEW_GYM){
            setGymInfo(controller.getGym());
            GymInfoPhoneGraphicController gymInfoGraphicController = new GymInfoPhoneGraphicController();
            helpMethod4(gymInfoGraphicController);
        }
    }

    @Override
    @FXML
    protected void submit(){
        if(controller.getView()==ControllerType.LOGIN) {
            LoginGraphicController login = (LoginGraphicController) controller.getInstance();
            login.submitActionLogin();
        } else if(controller.getView()==ControllerType.USER_EDIT) {
            EditGraphicController edit = (EditGraphicController) controller.getInstance();
            edit.okAction();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            SignUpPhoneGraphicController sU = (SignUpPhoneGraphicController) controller.getInstance();
            sU.submit(controller.getView());
        } else if (controller.getView()==ControllerType.SIGN_UP_GYM2){
            SignUpGymPhoneGraphicController gym = (SignUpGymPhoneGraphicController) controller.getInstance();
            gym.submitActionSignUpGym();
        }
    }

    @Override
    public void setSportQuiz() {
        controller.setView(ControllerType.SPORT_QUIZ);
    }

    @Override
    public void setFindGym() {
        controller.setView(ControllerType.FIND_GYM);
    }

    @Override
    public void setLogin() {
        controller.setView(ControllerType.LOGIN);
    }

    @Override
    public void setGymInfo(String gym){
        controller.setView(ControllerType.GYM_INFO);
        controller.setGym(gym);
    }
}
