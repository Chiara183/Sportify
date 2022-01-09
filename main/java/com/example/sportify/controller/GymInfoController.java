package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GymInfoController implements Initializable {

    // Label
    @FXML
    private Label gym_name;
    @FXML
    private Label gym_description;

    // TextArea
    @FXML
    private TextArea review_area;

    // BorderPane
    @FXML
    private BorderPane review_pane;

    // VBox
    @FXML
    private VBox course;
    @FXML
    private VBox review;

    // Button

    // String
    private String[] search_cache;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to Menu bar
    private MenuController menu;

    // User
    private User user;

    public GymInfoController(){
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setMenu(MenuController menu) {
        this.menu = menu;
    }
    public void setSearchCache(String[] search) {
        this.search_cache = search;
    }
    private void setReview(){
        review_pane.setVisible(this.user != null);
    }
    private void setupEventHandlers() {
        menu.getSignOut().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.user = this.menu.getUser();
            Runnable task = () -> Platform.runLater(this::setReview);
            Task<Void> task1 = createTask(task);
            task1.setOnRunning(e -> mainApp.getPrimaryPane().setCursor(Cursor.WAIT));
            task1.setOnSucceeded(e -> mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
            task1.setOnFailed(e -> mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
            new Thread(task1).start();
        });
    }

    private void loadReview(ResultSet rs){
        try {
            Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
            labelTitle.setStyle("-fx-font-weight: bold;");
            Label labelReview = new Label(rs.getString("review"));
            Label blankSpace = new Label();
            VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
            this.review.getChildren().add(vbox);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void share_review(){
        String gym = gym_name.getText();
        StringBuilder review = new StringBuilder(review_area.getText(0, review_area.getLength()));
        String[] reviewList = review.toString().split("'");
        review = new StringBuilder();
        int i = 0;
        while(i!=reviewList.length){
            review.append(reviewList[i]);
            if(i!=reviewList.length-1){
                review.append("\\'");
            }
            i++;
        }
        String user = this.user.getUserName();
        if(!review.toString().equals("")) {
            DAO obj_DAO = mainApp.getDAO();
            obj_DAO.updateDB(
                    "INSERT INTO `review` (`gym`, `review`, `writer`, `timestamp`) VALUES ('"
                            + gym + "', '"
                            + review + "', '"
                            + user + "', " +
                            "CURRENT_TIMESTAMP);");
        } else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Review is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        loadingGymName(gym_name.getText());
    }

    private Task<Void> createTask(Runnable task){
        return new Task<>() {
            @Override
            protected Void call() {
                task.run();
                return null;
            }
        };
    }

    private void setGym(String name){

        setupEventHandlers();

        this.gym_name.setText(name);

        // Set the area where create a new review
        setReview();

        // Set gym_description
        this.gym_description.setText(
                """
                        ADDRESS:\s

                        TELEPHONE:\s""");
        Runnable task1 = () -> Platform.runLater(() -> {
            try {
                DAO obj_DAO = mainApp.getDAO();
                ResultSet rs = obj_DAO.Check_Data(
                        "SELECT * " +
                                "FROM gym " +
                                "WHERE gym.name = \"" + name + "\"");
                if (rs.next()) {
                    gym_description.setText(
                            "ADDRESS: " + rs.getString("address") +
                                    "\n\nTELEPHONE: " + rs.getString("phone"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        Task<Void> task4 = createTask(task1);
        task4.setOnRunning(e -> gym_description.setCursor(Cursor.WAIT));
        task4.setOnSucceeded(e -> gym_description.setCursor(Cursor.DEFAULT));
        task4.setOnFailed(e -> gym_description.setCursor(Cursor.DEFAULT));

        // Set review
        Runnable task2 = () -> Platform.runLater(() -> {
            try {
                DAO obj_DAO = mainApp.getDAO();
                ResultSet rs = obj_DAO.Check_Data(
                        "SELECT * " +
                                "FROM review " +
                                "WHERE review.gym = \"" + name + "\"");
                if(rs.next()) {
                    while (rs.next()) {
                        loadReview(rs);
                    }
                } else {
                    Label labelNotFound = new Label("There are no reviews");
                    labelNotFound.setStyle("-fx-font-weight: bold;");
                    this.review.getChildren().add(labelNotFound);
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        });
        Task<Void> task5 = createTask(task2);
        task5.setOnRunning(e -> review.setCursor(Cursor.WAIT));
        task5.setOnSucceeded(e -> review.setCursor(Cursor.DEFAULT));
        task5.setOnFailed(e -> {
            review.setCursor(Cursor.DEFAULT);
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle("-fx-font-weight: bold;");
            this.review.getChildren().add(labelNotFound);
        });

        // Set course
        Runnable task3 = () -> Platform.runLater(() -> {
            try {
                DAO obj_DAO = mainApp.getDAO();
                ResultSet rs = obj_DAO.Check_Data(
                        "SELECT * " +
                                "FROM course " +
                                "WHERE course.gym = \"" + name + "\"");
                if(rs.next()) {
                    while (rs.next()) {
                        Label label = new Label(rs.getString("sport") + " " + rs.getTime("time").toString());
                        label.setStyle("alignment:\"CENTER\"; contentDisplay:\"CENTER\"; maxWidth:\"1.7976931348623157E308\"; textAlignment:\"CENTER\"; wrapText:\"true\"");
                        course.getChildren().add(label);
                    }
                } else {
                    Label label = new Label("There are no course");
                    label.setStyle("-fx-font-weight: bold;");
                    this.course.getChildren().add(label);
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        });
        Task<Void> task6 = createTask(task3);
        task6.setOnRunning(e -> course.setCursor(Cursor.WAIT));
        task6.setOnSucceeded(e -> course.setCursor(Cursor.DEFAULT));
        task6.setOnFailed(e -> {
            course.setCursor(Cursor.DEFAULT);
            Label label = new Label("There are no course");
            label.setStyle("-fx-font-weight: bold;");
            this.course.getChildren().add(label);
        });

        // Run Thread and set DEFAULT review and course
        new Thread(task4).start();
        new Thread(task5).start();
        new Thread(task6).start();
    }

    @FXML
    private void findGym(){
        this.mainApp.setUser(this.user);
        this.mainApp.setSearchCache(this.search_cache);
        this.mainApp.showFindGymOverview(this.menu);
    }


    public void loadingGymName(String name) {
        this.mainApp.getPrimaryStage().setTitle("Sportify - " + name);
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(mainApp.getClass().getResource("GymInfo.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            GymInfoController controller = loaderSport.getController();
            controller.setUser(this.user);
            controller.setMainApp(this.mainApp);
            controller.setSearchCache(this.search_cache);
            controller.setMenu(this.menu);
            controller.setGym(name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
