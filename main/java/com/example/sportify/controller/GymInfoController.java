package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.user.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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

    // TextField
    @FXML
    private TextField hour;
    @FXML
    private TextField min;

    // BorderPane
    @FXML
    private BorderPane review_pane;
    @FXML
    private BorderPane course_pane;

    // VBox
    @FXML
    private VBox course;
    @FXML
    private VBox review;

    // ComboBox
    @FXML
    private ComboBox<String> combo_sport;

    // Slider
    @FXML
    private Slider hour_slider;
    @FXML
    private Slider min_slider;

    // String
    private String[] search_cache;

    // Reference to the main application.
    private MainApp mainApp;

    // Reference to Menu bar
    private MenuController menu;

    // User
    private User user;

    // ObservableList
    private ObservableList<String> sport;

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
        search_cache = search;
    }
    private void setReview(){
        review_pane.setVisible(user != null && !Objects.equals(user.getGymName(), gym_name.getText()));
    }
    private void setCourse(){
        course_pane.setVisible(user != null && Objects.equals(user.getGymName(), gym_name.getText()));
    }
    private void setupEventHandlers() {
        menu.getSignOut().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            user = menu.getUser();
            Runnable task = () -> Platform.runLater(this::setReview);
            Task<Void> task1 = createTask(task);
            task1.setOnRunning(e -> mainApp.getPrimaryPane().setCursor(Cursor.WAIT));
            task1.setOnSucceeded(e -> mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
            task1.setOnFailed(e -> mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
            new Thread(task1).start();
        });
    }
    private ObservableList<String> getSport(){
        ObservableList<String> sport = FXCollections.observableArrayList();
        try {
            assert mainApp != null;
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM sport ");
            while (rs.next()) {
                sport.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sport;
    }

    private void loadReview(ResultSet rs){
        try {
            Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
            labelTitle.setStyle("-fx-font-weight: bold;");
            Label labelReview = new Label(rs.getString("review"));
            Label blankSpace = new Label();
            VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
            review.getChildren().add(vbox);
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

        // set ComboBox
        Runnable task0 = () -> Platform.runLater(() -> {
            sport = getSport();
            combo_sport.setValue("select sport");
            combo_sport.setItems(sport);
        });
        Task<Void> task00 = createTask(task0);
        task00.setOnRunning(e -> combo_sport.setCursor(Cursor.WAIT));
        task00.setOnSucceeded(e -> combo_sport.setCursor(Cursor.DEFAULT));
        task00.setOnFailed(e -> combo_sport.setCursor(Cursor.DEFAULT));

        setupEventHandlers();

        gym_name.setText(name);

        // Set the area where create a new review
        setReview();

        // Set the area where create a new course
        setCourse();

        // Set gym_description
        gym_description.setText(
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
                    review.getChildren().add(labelNotFound);
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
            review.getChildren().add(labelNotFound);
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
            course.getChildren().add(label);
        });

        // Run Thread and set DEFAULT review and course
        new Thread(task00).start();
        new Thread(task4).start();
        new Thread(task5).start();
        new Thread(task6).start();
    }

    @FXML
    private void findGym(){
        mainApp.setUser(user);
        mainApp.setSearchCache(search_cache);
        mainApp.showFindGymOverview(menu);
    }

    @FXML
    private void add_course(){
        //TODO
    }

    @FXML
    private void change_hour(){
        String hour = String.valueOf((int) hour_slider.getValue());
        this.hour.setText(hour);
    }

    @FXML
    private void change_min(){
        String min = String.valueOf((int) min_slider.getValue());
        this.min.setText(min);
    }

    public void loadingGymName(String name) {
        mainApp.getPrimaryStage().setTitle("Sportify - " + name);
        try {
            // Load test result overview.
            FXMLLoader loaderSport = new FXMLLoader();
            loaderSport.setLocation(mainApp.getClass().getResource("GymInfo.fxml"));
            Pane pane = loaderSport.load();

            // Set test result overview into the center of root layout.
            mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            GymInfoController controller = loaderSport.getController();
            controller.setUser(user);
            controller.setSearchCache(search_cache);
            controller.setMenu(menu);
            controller.setMainApp(mainApp);
            controller.setGym(name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
