package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    // Button
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

    // Pane
    @FXML
    private Pane userIcon;

    // Label
    @FXML
    private Label username;

    // Reference to the main application.
    private MainApp mainApp;

    // User
    private User user;

    /**
     * The constructor.
     */
    public MenuController() {
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
        if (this.user!=null) {
            signOut.setVisible(true);
            signOut.setPrefWidth(112);
            signUp.setVisible(false);
            signUp.setPrefWidth(0);
            signIn.setVisible(false);
            signIn.setPrefWidth(0);
            userIcon.setVisible(true);
            username.setText(user.getUserName());
        } else {
            signOut.setPrefWidth(0);
            signOut.setVisible(false);
            signUp.setPrefWidth(112);
            signUp.setVisible(true);
            signIn.setPrefWidth(112);
            signIn.setVisible(true);
            userIcon.setVisible(false);
        }
    }

    @FXML
    private void signOut() {
        setUser(null);
        mainApp.setUser(null);
    }

    @FXML
    private void homeAction() {
        this.mainApp.showHomeOverview();
    }

    @FXML
    private void sportQuizAction() {
        findGym.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        sportQuiz.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showSportQuizOverview();
    }

    @FXML
    private void findGymAction() {
        sportQuiz.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        findGym.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showFindGymOverview();
    }

    @FXML
    private void signLoginAction() {
        findGym.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("");
        signIn.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showLoginOverview();
    }

    @FXML
    private void signUpAction() {
        findGym.setStyle("");
        signIn.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
        this.mainApp.showSignUpOverview();
    }

    @FXML
    private void openUserInterface() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            if(user.getGymName() != null) {
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
            UserEditController controller = loader.getController();
            controller.setUser(user);
            controller.setMenuController(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSportQuiz() {
        findGym.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        sportQuiz.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void setFindGym() {
        sportQuiz.setStyle("");
        signIn.setStyle("");
        signUp.setStyle("");
        findGym.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void setLogin() {
        findGym.setStyle("");
        sportQuiz.setStyle("");
        signUp.setStyle("");
        signIn.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}

