package sportify.controller;

import sportify.errorlogic.NewException;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymInfoGraphicController;
import sportify.controller.graphic.LoginGraphicController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Objects;

public class LoginController extends AccessController{

    /** Reference to graphicController*/
    private LoginGraphicController graphicController;

    /* Error login variable*/
    private Double delay = 3.0;
    private final Label label = new Label();

    /** Is true when login is in external window*/
    private boolean external;

    /** Set up the external stage*/
    private Stage externalStage;

    /** The constructor.*/
    public LoginController() {
        this.type = ControllerType.LOGIN;
    }

    /** It's called to set external stage.*/
    public void setStage(Stage stage) {
        this.externalStage = stage;
    }

    /** Is called to set login in external window.*/
    public void setExternal(boolean external) {
        this.external = external;
    }

    /** It's called to load the home overview*/
    public void home(){
        this.mainApp.setUser(this.user);
        this.mainApp.showHomeOverview();
        if(external){
            Stage stage = this.externalStage;
            stage.close();
        }
    }

    /** Check whether the credentials are authentic or not and do the right action*/
    public void submit(String userValue, String passValue){
        if (this.submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            this.user = this.submit.setUser(userValue);
            if(!external) {
                home();
            } else {
                this.mainApp.setUser(this.user);
                this.menu.setUser(this.user);
                MenuController menu = this.mainApp.menu();
                menu.setUser(this.user);
                GymInfoGraphicController gymInfoGraphicController = new GymInfoGraphicController();
                GymInfoController gym = new GymInfoController();
                gym.setMainApp(this.mainApp);
                gym.setUser(this.user);
                gymInfoGraphicController.setController(gym);
                gym.setGraphicController(gymInfoGraphicController);
                loadUser(gym);
                Stage stage = this.externalStage;
                stage.close();
            }
        } else {
            Throwable cause = new Throwable("The cause of the exception is in submit(), Login Class");
            String message = "Exception rose in submit() method, Login Class";
            NewException ne = new NewException(message,cause);
            try {
                throw ne;
            } catch (NewException e) {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Wrong Username or Password");
                alert.setHeaderText("You wrote wrong username or password");
                alert.setContentText("Please enter valid username and password or Signup");
                setLoginDisable(true);
                Stage stage = createCountdown();
                alert.showAndWait();
                stage.showAndWait();
                delay = delay*(delay/2.0);
                setLoginDisable(false);
            }
        }
    }

    /** Countdown of error login*/
    private void doTime(){
        final int[] seconds = {delay.intValue()};
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(delay), event -> {
            seconds[0]--;
            label.setText("Waiting... " + seconds[0]);
            if(seconds[0] <=0){
                Stage stage = (Stage)label.getParent().getScene().getWindow();
                stage.close();
                time.stop();
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    /** Load user in current view*/
    private void loadUser(GymInfoController gym){
        if (Objects.equals(this.menu.getView(), ControllerType.GYM_INFO)){
            menu.setGymInfo(this.menu.getGym());
        } else if (Objects.equals(this.menu.getView(), ControllerType.FIND_GYM) && this.menu.getGym() != null){
            menu.setFindGym();
            menu.setGym(this.menu.getGym());
        }
        if (!Objects.equals(this.menu.getView(), null)) {
            gym.setMenu(menu);
            gym.setSearchCache(this.searchCache);
            gym.loadingGymName(this.menu.getGym());
        }
    }

    /** Is called to activate or disable the login interface*/
    private void setLoginDisable(boolean bool){
        graphicController.getUsernameField().setDisable(bool);
        graphicController.getPassField().setDisable(bool);
        graphicController.getPasswordField().setDisable(bool);
        if(mainApp.isNotMobile()) {
            graphicController.getSubmitButton().setDisable(bool);
            graphicController.getSkipButton().setDisable(bool);
            menu.setMenuDisable(bool);
        }
    }

    /** Is called to create countdown window*/
    private Stage createCountdown(){
        int seconds;
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.initOwner(mainApp.getPrimaryStage());
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        seconds = delay.intValue();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font(20));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Waiting... " + seconds);
        doTime();
        Pane root = new Pane();
        root.getChildren().add(label);
        if(mainApp.isNotMobile()) {
            stage.setScene(new Scene(root, 300, 70));
        } else {
            stage.setScene(new Scene(root, 100, 40));
        }
        return stage;
    }

    /** Is called to set graphicController*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (LoginGraphicController) graphicController;
    }
}


