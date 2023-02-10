package sportify.controller;

import sportify.MainApp;
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
import sportify.model.dao.Submit;

import java.util.Objects;

/**
 * The LoginController class extends the AccessController
 * class and provides functionality for logging in,
 * setting the stage, and processing user submissions. It also
 * contains methods for controlling the user interface and displaying errors.
 *
 * @see AccessController
 */
public class LoginController extends AccessController{

    /**
     * graphicController is a private field that represents
     * the graphical user interface for the login process.
     */
    private LoginGraphicController graphicController;

    /* Error login variable*/
    /**
     * delay is a private field that represents
     * the delay time for displaying an error message.
     */
    private Double delay = 3.0;

    /**
     * label is a private final field that represents
     * a label to display error messages.
     */
    private final Label LABEL = new Label();

    /**
     * external is a private boolean field that
     * indicates whether the login process is
     * being performed externally.
     */
    private boolean external;

    /**
     * externalStage is a private field that
     * represents the stage for external logins.
     */
    private Stage externalStage;

    /**
     * The constructor for the LoginController
     * class, which initializes the necessary fields.
     */
    public LoginController() {
        this.type = ControllerType.LOGIN;
    }

    /**
     * setStage is a public method that takes in
     * a Stage object and sets it as the stage for
     * the login process.
     *
     * @param stage the Stage to be set as
     *              the login stage
     */
    public void setStage(Stage stage) {
        this.externalStage = stage;
    }

    /**
     * setExternal is a public method that sets the external
     * boolean field to indicate whether the login process is
     * being performed externally.
     *
     * @param external the boolean value to set
     *                 the external field to
     */
    public void setExternal(boolean external) {
        this.external = external;
    }

    /**
     * home is a public method that
     * navigates the user to the home screen.
     */
    public void home(){
        MainApp.setUser(this.user);
        MainApp.showHomeOverview();
        if(external){
            Stage stage = this.externalStage;
            stage.close();
        }
    }

    /**
     * submit is a public method that takes in a userValue
     * and passValue as arguments and submits them for processing.
     *
     * @param userValue the username provided by the user
     * @param passValue the password provided by the user
     */
    public void submit(String userValue, String passValue){
        if (Submit.login(userValue, passValue)) {   //if authentic, navigate user to a new page
            this.user = Submit.setUser(userValue);
            if(!external) {
                home();
            }
            else {
                MainApp.setUser(this.user);
                this.menu.setUser(this.user);
                MenuController menu = MainApp.menu();
                menu.setUser(this.user);
                GymInfoGraphicController gymInfoGraphicController = new GymInfoGraphicController();
                GymInfoController gym = new GymInfoController();
                gym.setUser(this.user);
                gymInfoGraphicController.setController(gym);
                gym.setGraphicController(gymInfoGraphicController);
                loadUser(gym);
                Stage stage = this.externalStage;
                stage.close();
            }
        }
        else {
            Throwable cause = new Throwable("The cause of the exception is in submit(), Login Class");
            String message = "Exception rose in submit() method, Login Class";
            NewException ne = new NewException(message,cause);
            try {
                throw ne;
            }
            catch (NewException e) {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(MainApp.getPrimaryStage());
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

    /**
     * doTime is a private method that
     * implements the error login countdown.
     */
    private void doTime(){
        final int[] seconds = {delay.intValue()};
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(delay),
                event -> {
            seconds[0]--;
            LABEL.setText("Waiting... " + seconds[0]);
            if(seconds[0] <=0){
                Stage stage = (Stage) LABEL.getParent().getScene().getWindow();
                stage.close();
                time.stop();
            }
        }
        );
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    /**
     * loadUser is a private method that takes
     * in a GymInfoController and loads the user information.
     *
     * @param gym the GymInfoController
     *            to be processed
     */
    private void loadUser(GymInfoController gym){
        if (Objects.equals(this.menu.getView(), ControllerType.GYM_INFO)){
            menu.setGymInfo(this.menu.getGym());
        }
        else if (Objects.equals(this.menu.getView(), ControllerType.FIND_GYM) &&
                this.menu.getGym() != null){
            menu.setFindGym();
            menu.setGym(this.menu.getGym());
        }
        if (!Objects.equals(this.menu.getView(), null)) {
            gym.setMenu(menu);
            gym.setSearchCache(this.searchCache);
            gym.loadingGymName(this.menu.getGym());
        }
    }

    /**
     * setLoginDisable is a private method
     * that sets the login button as disabled or enabled.
     *
     * @param bool the boolean value to set the
     *             login button to
     */
    private void setLoginDisable(boolean bool){
        graphicController.getUsernameField().setDisable(bool);
        graphicController.getPassField().setDisable(bool);
        graphicController.getPasswordField().setDisable(bool);
        if(MainApp.isNotMobile()) {
            graphicController.getSubmitButton().setDisable(bool);
            graphicController.getSkipButton().setDisable(bool);
            menu.setMenuDisable(bool);
        }
    }

    /**
     * createCountdown is a private method
     * that creates the countdown timer for
     * the error login message.
     *
     * @return the Stage representing
     * the countdown timer
     */
    private Stage createCountdown(){
        int seconds;
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        stage.initOwner(MainApp.getPrimaryStage());
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        seconds = delay.intValue();
        LABEL.setTextFill(Color.BLACK);
        LABEL.setFont(Font.font(20));
        LABEL.setTextAlignment(TextAlignment.CENTER);
        LABEL.setText("Waiting... " + seconds);
        doTime();
        Pane root = new Pane();
        root.getChildren().add(LABEL);
        if(MainApp.isNotMobile()) {
            stage.setScene(new Scene(root, 300, 70));
        }
        else {
            stage.setScene(new Scene(root, 100, 40));
        }
        return stage;
    }

    /**
     * setGraphicController is a public method that
     * overrides the setGraphicController method in
     * the GraphicController class
     * and sets the graphicController field.
     *
     * @param graphicController the GraphicController to be
     *                          set as the graphicController field
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (LoginGraphicController) graphicController;
    }
}


