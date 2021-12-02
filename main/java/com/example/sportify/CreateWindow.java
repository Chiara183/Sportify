package com.example.sportify;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateWindow implements Initializable {

    private static void create(Button button, Parent root, String string){

        //HomeScene
        Scene sceneBack = new Scene(root, 780, 437);
        Stage stage = (Stage) button.getScene().getWindow();

        //set stage
        stage.getIcons().add(new Image(Objects.requireNonNull(CreateWindow.class.getResourceAsStream("Images/Sportify icon.png"))));
        stage.setTitle(string);
        stage.setScene(sceneBack);
        stage.setResizable(false);
        stage.show();
    }

    public static void signUpGym(Button submitSignUp) throws Exception {
        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUpGym.fxml")));
        create(submitSignUp, root, "Sportify - Sign Up");
    }

    public static void home(Button home) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Home.fxml")));
        create(home, root, "Sportify - Home");
    }

    public static void login(Button login) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("Login.fxml")));
        create(login, root, "Sportify - Login");
    }

    public static void signUp(Button signUp) throws Exception {
        //SignUpScene
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SignUp.fxml")));
        create(signUp, root, "Sportify - Sign Up");
    }

    public static void sportQuiz(Button sportQuiz) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SportQuiz.fxml")));
        create(sportQuiz, root, "Sportify - Sport Quiz");
    }


    public static void findGym(Button findGym) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("FindGym.fxml")));
            create(findGym, root, "Sportify - Gym Finder");
        }

    public static void sportQuizEnv(Button nextQuiz) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SportQuizEnv.fxml")));
        create(nextQuiz, root, "Sportify - Sport Quiz");
    }
    public static void sportQuizType(Button sportQuiz) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateWindow.class.getResource("SportQuizType.fxml")));
        create(sportQuiz, root, "Sportify - Sport Quiz");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

    /*Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Background.fxml")));
        VBox view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("VBox.fxml")));
        GridPane grid = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GridPane.fxml")));
        Pane image = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ImageUp.fxml")));
        HBox menu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        grid.getChildren().add(image);
        grid.getChildren().add(menu);
        view.getChildren().add(grid);
        root.getChildren().add(view);*/
}
