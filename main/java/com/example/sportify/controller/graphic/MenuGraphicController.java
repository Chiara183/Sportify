package com.example.sportify.controller.graphic;

import com.example.sportify.MainApp;
import com.example.sportify.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuGraphicController implements GraphicController{

    /** Reference to controller*/
    private MenuController controller;

    /** All the button of the menu*/
    @FXML
    private Button sportQuiz;
    @FXML
    private Button findGym;
    @FXML
    private Button signIn;
    @FXML
    private Button signUp;
    @FXML
    private Button signOut;
    @FXML
    private Button gymInfo;

    // Pane
    @FXML
    private Pane userIcon;

    // Label
    @FXML
    private Label username;

    /** The action of the buttons*/
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
        } else if(controller.getView()==ControllerType.USER_EDIT) {
            if(controller.getEdit().controller.getView()==ControllerType.FIND_GYM) {
                findGymAction();
            } else if(controller.getEdit().controller.getView()==ControllerType.GYM_INFO) {
                setGymInfo(controller.getGym());
            } else if(controller.getEdit().controller.getView()==ControllerType.SPORT_QUIZ) {
                sportQuizAction();
            } else if(controller.getEdit().controller.getView()==ControllerType.SPORT_QUIZ_TYPE) {
                sportQuizAction();
            } else if(controller.getEdit().controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
                sportQuizAction();
            } else {
                homeAction();
            }
        }
    }
    @FXML
    private void done(){
        if(controller.getView()==ControllerType.USER_EDIT) {
            controller.getEdit().okAction();
        }
    }
    @FXML
    private void signAction(){
        if(controller.getUser()==null){
            signLoginAction();
        } else {
            openUserInterface();
        }
    }
    @FXML
    private void signOut() {
        controller.setUser(null);
        controller.getMainApp().setUser(null);
    }
    @FXML
    private void loadGymInfo(){
        controller.setButton(findGym, sportQuiz, signOut, signIn, signUp, gymInfo);
        GymInfoGraphicController graphicController = new GymInfoGraphicController();
        GymInfoController gym = new GymInfoController();
        graphicController.setController(gym);
        controller.setView(ControllerType.GYM_INFO);
        gym.setGraphicController(graphicController);
        gym.setMainApp(controller.getMainApp());
        gym.setUser(controller.getUser());
        gym.setMenu(controller);
        gym.setSearchCache(controller.getMainApp().getSearchCache());
        gym.loadingGymName(controller.getUser().getGymName());
        controller.setGym(controller.getUser().getGymName());
    }
    @FXML
    private void homeAction() {
        controller.setView(ControllerType.HOME);
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showHomeOverview();
    }
    @FXML
    private void sportQuizAction() {
        controller.setView(ControllerType.SPORT_QUIZ);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(findGym, signOut, signIn, signUp, gymInfo, sportQuiz);
        }
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showSportQuizOverview(controller);
    }
    @FXML
    private void findGymAction() {
        controller.setView(ControllerType.FIND_GYM);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(signOut, signIn, signUp, gymInfo, sportQuiz, findGym);
        }
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showFindGymOverview(controller);
    }
    @FXML
    private void signLoginAction() {
        if(controller.getMainApp().isNotMobile()) {
            controller.getMainApp().setExternalLogin(true);
        }
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showLoginOverview();
    }
    @FXML
    private void signUpAction() {
        controller.setView(ControllerType.SIGN_UP);
        controller.setButton(signOut, signIn, gymInfo, sportQuiz, findGym, signUp);
        controller.getMainApp().setMenu(controller);
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().showSignUpOverview();
    }
    @FXML
    private void openUserInterface() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            EditController editController;
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            if(controller.getMainApp().isNotMobile()) {
                if (Objects.equals(this.controller.getUser().getRole(), "gym")) {
                    loader.setLocation(MainApp.class.getResource("DesktopView/GymEditDialog.fxml"));
                    editController = new GymEditController();
                } else {
                    loader.setLocation(MainApp.class.getResource("DesktopView/UserEditDialog.fxml"));
                    editController = new UserEditController();
                }
            } else {
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
            }
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();

            if(controller.getMainApp().isNotMobile()) {
                // Create the dialog Stage.
                dialogStage.setTitle(this.controller.getUser().getUserName());
                dialogStage.getIcons().add(new Image(Objects.requireNonNull(this.controller.getMainApp().getClass().getResourceAsStream("Images/Sportify icon.png"))));
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.controller.getMainApp().getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
            } else {
                controller.getMainApp().getPrimaryPane().setCenter(page);
                controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(this.controller);
            }

            // Set the person into the editController.
            EditGraphicController graphicController = loader.getController();
            editController.setGraphicController(graphicController);
            graphicController.setController(editController);
            editController.setMainApp(this.controller.getMainApp());
            editController.setUser(this.controller.getUser());
            editController.setMenu(this.controller);
            this.controller.setEdit(graphicController);
            editController.setView(controller.getView());
            this.controller.setView(ControllerType.USER_EDIT);

            if(controller.getMainApp().isNotMobile()) {
                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(MenuGraphicController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }
    @FXML
    private void submit(){
        controller.getLogin().submitActionLogin();
    }

    /** The method called to set the view*/
    public void setSportQuiz() {
        controller.setView(ControllerType.SPORT_QUIZ);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(findGym, signOut, signIn, signUp, gymInfo, sportQuiz);
        }
    }
    public void setFindGym() {
        controller.setView(ControllerType.FIND_GYM);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(signOut, signIn, signUp, gymInfo, sportQuiz, findGym);
        }
    }
    public void setLogin() {
        controller.setView(ControllerType.LOGIN);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(signOut, gymInfo, sportQuiz, findGym, signUp, signIn);
        }
    }
    public void setGymInfo(String gym){
        controller.setView(ControllerType.GYM_INFO);
        controller.setGym(gym);
        if(controller.getMainApp().isNotMobile()) {
            controller.setButton(findGym, sportQuiz, signOut, signIn, signUp, gymInfo);
        }
    }
    public void setUserKind() {
        controller.setView(ControllerType.USER_KIND);
    }

    /** Is called to get signOut button.*/
    public Button getSignOut(){
        return this.signOut;
    }

    /** Is called to set user view*/
    public void setUser(boolean bool) {
        if (this.controller.getMainApp().isNotMobile()) {
            if (bool) {
                signOut.setVisible(true);
                signOut.setPrefWidth(112);
                signUp.setVisible(false);
                signUp.setPrefWidth(0);
                signIn.setVisible(false);
                signIn.setPrefWidth(0);
                userIcon.setVisible(true);
                username.setText(controller.getUser().getUserName());
                if (controller.getUser().getRole().equals("gym")) {
                    gymInfo.setVisible(true);
                    gymInfo.setPrefWidth(112);
                }
            } else {
                signOut.setPrefWidth(0);
                signOut.setVisible(false);
                signUp.setPrefWidth(112);
                signUp.setVisible(true);
                signIn.setPrefWidth(112);
                signIn.setVisible(true);
                gymInfo.setVisible(false);
                gymInfo.setPrefWidth(0);
                userIcon.setVisible(false);
            }
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (MenuController) controller;
    }
}
