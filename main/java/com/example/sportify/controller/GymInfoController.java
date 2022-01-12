package com.example.sportify.controller;

import com.example.sportify.DAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class GymInfoController extends Controller {

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

    // ObservableList
    private ObservableList<String> sport;

    // Method that set up the controller
    public GymInfoController(){
    }
    private void setReview(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                this.review_pane.setVisible(true);
            } else this.review_pane.setVisible(!Objects.equals(this.user.getGymName(), this.gym_name.getText()));
        } else {
            this.review_pane.setVisible(false);
        }
    }
    private void setCourse(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                this.course_pane.setVisible(false);
            } else this.course_pane.setVisible(Objects.equals(this.user.getGymName(), this.gym_name.getText()));
        } else {
            this.course_pane.setVisible(false);
        }
    }
    private void settingPage(){
        this.user = this.menu.getUser();
        Runnable task = () -> Platform.runLater(() -> {
            setReview();
            setCourse();
        });
        Task<Void> task1 = createTask(task);
        task1.setOnRunning(e -> this.mainApp.getPrimaryPane().setCursor(Cursor.WAIT));
        task1.setOnSucceeded(e -> this.mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
        task1.setOnFailed(e -> this.mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT));
        new Thread(task1).start();
    }
    private void setupEventHandlers() {
        this.menu.getSignOut().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> settingPage());
    }
    private ObservableList<String> getSport(){
        ObservableList<String> sport = FXCollections.observableArrayList();
        try {
            assert this.mainApp != null;
            DAO obj_DAO = this.mainApp.getDAO();
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
    private Task<Void> createTask(Runnable task){
        return new Task<>() {
            @Override
            protected Void call() {
                task.run();
                return null;
            }
        };
    }

    // Load the review of gym
    private void loadReview(ResultSet rs){
        this.review.getChildren().remove(0, this.review.getChildren().size());
        try {
            Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
            String string = rs.getString("writer") + ";" + rs.getTimestamp("timestamp").toString();
            labelTitle.setStyle("-fx-font-weight: bold;");
            Label labelReview = new Label(rs.getString("review"));
            Label blankSpace = new Label();
            VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
            if (this.user != null && Objects.equals(this.user.getRole(), "gym") && Objects.equals(this.user.getGymName(), this.gym_name.getText())) {
                Label cancel = new Label("⮿");
                cancel.setStyle("-fx-text-fill: red; ");
                cancel.setEllipsisString(string);
                cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::cancelReview);
                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                HBox hbox = new HBox(vbox, new Label(), new Label(), new Label(), new Label(), cancel);
                this.review.getChildren().add(hbox);
            } else {
                this.review.getChildren().add(vbox);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Cancel a review of gym
    @FXML
    private void cancelReview(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO obj_DAO = this.mainApp.getDAO();
        obj_DAO.updateDB("DELETE FROM `review` WHERE `review`.`writer` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `review`.`gym` = '" +
                this.gym_name.getText() +
                "' AND `review`.`timestamp` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        loadingGymName(gym_name.getText());
    }

    // Download the review
    private void downloadReview(){
        try {
            DAO obj_DAO = this.mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM review " +
                            "WHERE review.gym = \"" + this.gym_name.getText() + "\"");

            while (rs.next()) {
                loadReview(rs);
            }
            if(this.review.getChildren().size()<1) {
                Label labelNotFound = new Label("There are no reviews");
                labelNotFound.setStyle("-fx-font-weight: bold;");
                this.review.getChildren().add(labelNotFound);
            }
        }catch (SQLException q){
            System.out.println(q.getMessage());
        }
    }

    // Load the course of gym
    private void loadCourse(ResultSet rs){
        this.course.getChildren().remove(1, this.course.getChildren().size());
        try {
            Label label = new Label(rs.getString("sport") + " " + rs.getTime("time").toString().substring(0, 5));
            String s = rs.getString("sport") + ";" + rs.getTime("time").toString();
            label.setStyle("-fx-text-fill: white;");
            if (this.user != null && Objects.equals(this.user.getGymName(), this.gym_name.getText())) {
                Label cancel = new Label("⮿");
                cancel.setStyle("-fx-text-fill: red;");
                cancel.setEllipsisString(s);
                cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::cancelCourse);
                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                HBox hbox = new HBox(label, new Label(), new Label(), cancel);
                this.course.getChildren().add(hbox);
            } else {
                this.course.getChildren().add(label);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Cancel a course of gym
    @FXML
    private void cancelCourse(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO obj_DAO = this.mainApp.getDAO();
        obj_DAO.updateDB("DELETE FROM `course` WHERE `course`.`sport` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `course`.`gym` = '" +
                this.gym_name.getText() +
                "' AND `course`.`time` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        loadingGymName(gym_name.getText());
    }

    // Download Course
    private void downloadCourse(){
        try {
            DAO obj_DAO = this.mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM course " +
                            "WHERE course.gym = \"" + this.gym_name.getText() + "\"");
            while (rs.next()) {
                loadCourse(rs);
            }
            if(this.course.getChildren().size()<2) {
                Label label = new Label("There are no course");
                label.setStyle("-fx-font-weight: bold;");
                this.course.getChildren().add(label);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // Set Gym View
    private void setGym(String name){

        // Handlers
        setupEventHandlers();

        // Window Title
        this.gym_name.setText(name);

        // Set visible or not area new review and new course
        settingPage();

        // set ComboBox
        Runnable task0 = () -> Platform.runLater(() -> {
            this.sport = getSport();
            this.combo_sport.setValue("select sport");
            this.combo_sport.setItems(this.sport);
        });
        Task<Void> task00 = createTask(task0);
        task00.setOnRunning(e -> this.combo_sport.setCursor(Cursor.WAIT));
        task00.setOnSucceeded(e -> this.combo_sport.setCursor(Cursor.DEFAULT));
        task00.setOnFailed(e -> this.combo_sport.setCursor(Cursor.DEFAULT));

        // Set gym_description
        this.gym_description.setText(
                """
                        ADDRESS:\s

                        TELEPHONE:\s""");
        Runnable task1 = () -> Platform.runLater(() -> {
            try {
                DAO obj_DAO = this.mainApp.getDAO();
                ResultSet rs = obj_DAO.Check_Data(
                        "SELECT * " +
                                "FROM gym " +
                                "WHERE gym.name = \"" + name + "\"");
                if (rs.next()) {
                    if(Objects.equals(rs.getString("phone"), "null")){
                        this.gym_description.setText(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: \\\\");
                    } else {
                        this.gym_description.setText(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: " + rs.getString("phone"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        Task<Void> task4 = createTask(task1);
        task4.setOnRunning(e -> this.gym_description.setCursor(Cursor.WAIT));
        task4.setOnSucceeded(e -> this.gym_description.setCursor(Cursor.DEFAULT));
        task4.setOnFailed(e -> this.gym_description.setCursor(Cursor.DEFAULT));

        // Set review
        Runnable task2 = () -> Platform.runLater(this::downloadReview);
        Task<Void> task5 = createTask(task2);
        task5.setOnRunning(e -> this.review.setCursor(Cursor.WAIT));
        task5.setOnSucceeded(e -> this.review.setCursor(Cursor.DEFAULT));
        task5.setOnFailed(e -> {
            this.review.setCursor(Cursor.DEFAULT);
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle("-fx-font-weight: bold;");
            this.review.getChildren().add(labelNotFound);
        });

        // Set course
        Runnable task3 = () -> Platform.runLater(this::downloadCourse);
        Task<Void> task6 = createTask(task3);
        task6.setOnRunning(e -> this.course.setCursor(Cursor.WAIT));
        task6.setOnSucceeded(e -> this.course.setCursor(Cursor.DEFAULT));
        task6.setOnFailed(e -> {
            this.course.setCursor(Cursor.DEFAULT);
            Label label = new Label("There are no course");
            label.setStyle("-fx-font-weight: bold;");
            this.course.getChildren().add(label);
        });

        // Run Thread and set DEFAULT review and course
        new Thread(task00).start();
        new Thread(task4).start();
        new Thread(task5).start();
        new Thread(task6).start();
    }

    @FXML
    private void share_review(){
        String gym = this.gym_name.getText();
        StringBuilder review = new StringBuilder(this.review_area.getText(0, this.review_area.getLength()));
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
            DAO obj_DAO = this.mainApp.getDAO();
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
        loadingGymName(gym);
    }

    @FXML
    private void findGym(){
        this.mainApp.setUser(this.user);
        this.mainApp.setSearchCache(this.search_cache);
        this.mainApp.showFindGymOverview(this.menu);
    }

    @FXML
    private void add_course(){
        String gym = this.gym_name.getText();
        String sport = this.combo_sport.getValue();
        String hour;
        if(this.hour.getText().equals("")){
            hour = "01";
        } else {
            hour = this.hour.getText();
        }
        String min;
        if(this.min.getText().equals("")){
            min = "00";
        } else {
            min = this.min.getText();
        }
        String time = hour + ':' + min + ":00";
        if(!sport.equals("select sport")) {
            DAO obj_DAO = mainApp.getDAO();
            obj_DAO.updateDB(
                    "INSERT INTO `course` (`sport`, `gym`, `time`) VALUES ('" +
                            sport + "', '" +
                            gym + "', '" +
                            time + "');");
            loadingGymName(gym_name.getText());
        } else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert all value.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void change_hour(){
        String hour = String.valueOf((int) hour_slider.getValue());
        if(Integer.parseInt(hour)<10) {
            this.hour.setText("0" + hour);
        } else {
            this.hour.setText(hour);
        }
    }

    @FXML
    private void change_min(){
        String min = String.valueOf((int) min_slider.getValue());
        if(Integer.parseInt(min)<10) {
            this.min.setText("0" + min);
        } else {
            this.min.setText(min);
        }
    }

    public void loadingGymName(String name) {
        this.mainApp.getPrimaryStage().setTitle("Sportify - " + name);
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.mainApp.getClass().getResource("GymInfo.fxml"));
            Pane pane = loader.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            GymInfoController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setUser(this.user);
            controller.setSearchCache(this.search_cache);
            controller.setMenu(this.menu);
            controller.setGym(name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Get gym name
    public String getGymName(){
        return this.gym_name.getText();
    }
}
