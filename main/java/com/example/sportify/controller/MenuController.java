package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.user.User;
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

public class MenuController extends Controller {

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

    /** The variable that identify the name of the view*/
    private ControllerType view;

    /** String that identify the name of last gym loaded*/
    private String gym;

    /** The constructor.*/
    public MenuController() {
        this.type = ControllerType.MENU;
    }

    /** Is called to get the name of the view.*/
    public ControllerType getView() {
        return this.view;
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
        if (this.user!=null) {
            signOut.setVisible(true);
            signOut.setPrefWidth(112);
            signUp.setVisible(false);
            signUp.setPrefWidth(0);
            signIn.setVisible(false);
            signIn.setPrefWidth(0);
            userIcon.setVisible(true);
            username.setText(user.getUserName());
            if(this.user.getRole().equals("gym")){
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

    /** Is called to get user.*/
    public User getUser(){
        return this.user;
    }

    /** Is called to get signOut button.*/
    public Button getSignOut(){
        return this.signOut;
    }

    /** The action of the buttons*/
    @FXML
    private void signOut() {
        setUser(null);
        mainApp.setUser(null);
    }
    @FXML
    private void loadGymInfo(){
        setButton(findGym, sportQuiz, signOut, gymInfo);
        GymInfoController gym = new GymInfoController();
        this.view = ControllerType.GYM_INFO;
        gym.setMainApp(this.mainApp);
        gym.setUser(this.user);
        gym.setMenu(this);
        gym.setSearchCache(this.mainApp.getSearchCache());
        gym.loadingGymName(user.getGymName());
        this.gym = user.getGymName();
    }
    @FXML
    private void homeAction() {
        this.view = ControllerType.HOME;
        this.mainApp.setMenu(this);
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
    }
    @FXML
    private void sportQuizAction() {
        this.view = ControllerType.SPORT_QUIZ;
        setButton(findGym, signIn, signUp, sportQuiz);
        this.mainApp.setMenu(this);
        this.mainApp.setUser(this.user);
        this.mainApp.showSportQuizOverview(this);
    }
    @FXML
    private void findGymAction() {
        this.view = ControllerType.FIND_GYM;
        setButton(signIn, sportQuiz, signUp, findGym);
        this.mainApp.setMenu(this);
        this.mainApp.setUser(this.user);
        this.mainApp.showFindGymOverview(this);
    }
    @FXML
    private void signLoginAction() {
        setButton(findGym, sportQuiz, signUp, signIn);
        this.mainApp.setExternal_login(true);
        this.mainApp.setMenu(this);
        this.mainApp.setUser(this.user);
        this.mainApp.showLoginOverview();
    }
    @FXML
    private void signUpAction() {
        this.view = ControllerType.SIGN_UP;
        setButton(findGym, sportQuiz, signIn, signUp);
        this.mainApp.setMenu(this);
        this.mainApp.setUser(this.user);
        this.mainApp.showSignUpOverview();
    }
    @FXML
    private void openUserInterface() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            if(Objects.equals(user.getRole(), "gym")) {
                loader.setLocation(MainApp.class.getResource("GymEditDialog.fxml"));
            } else {
                loader.setLocation(MainApp.class.getResource("UserEditDialog.fxml"));
            }
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(user.getUserName());
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(mainApp.getClass().getResourceAsStream("Images/Sportify icon.png"))));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditController controller = loader.getController();
            controller.setUser(user);
            controller.setMenu(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /** The method called to set the view*/
    public void setSportQuiz() {
        this.view = ControllerType.SPORT_QUIZ;
        setButton(findGym, signIn, signUp, sportQuiz);
    }
    public void setFindGym() {
        this.view = ControllerType.FIND_GYM;
        setButton(signIn, sportQuiz, signUp, findGym);
    }
    public void setLogin() {
        this.view = ControllerType.LOGIN;
        setButton(findGym, sportQuiz, signUp, signIn);
    }
    public void setGymInfo(String gym){
        this.view = ControllerType.GYM_INFO;
        this.gym = gym;
        setButton(findGym, sportQuiz, signOut, gymInfo);
    }

    /** It's called to set the color and disable/able of the buttons*/
    private void setButton(Button button1, Button button2, Button button3, Button button_off){
        button1.setStyle("");
        button1.setDisable(false);
        button2.setStyle("");
        button2.setDisable(false);
        button3.setStyle("");
        button3.setDisable(false);
        button_off.setDisable(true);
        button_off.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }
}

