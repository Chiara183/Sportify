package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.controller.graphic.GymInfoGraphicController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class GymInfoController extends Controller {

    /** Reference to graphic controller*/
    private GymInfoGraphicController graphicController;

    // ObservableList
    private ObservableList<String> sport;

    /** Is called to set graphic controller*/
    public void setGraphicController(GymInfoGraphicController graphicController) {
        this.graphicController = graphicController;
    }

    /** The constructor.*/
    public GymInfoController(){
        this.type = ControllerType.GYM_INFO;
    }

    /** Method that set up the controller*/
    private void setReview(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                graphicController.getReview_pane().setVisible(true);
            } else graphicController.getReview_pane().setVisible(!Objects.equals(this.user.getGymName(), getGymName()));
        } else {
            graphicController.getReview_pane().setVisible(false);
        }
    }
    private void setCourse(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                graphicController.getCourse_pane().setVisible(false);
            } else graphicController.getCourse_pane().setVisible(Objects.equals(this.user.getGymName(), getGymName()));
        } else {
            graphicController.getCourse_pane().setVisible(false);
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

    /** initializes the event handlers.*/
    private void setupEventHandlers() {
        this.menu.getSignOut().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> settingPage());
    }

    /** It's called to create new task*/
    private Task<Void> createTask(Runnable task){
        return new Task<>() {
            @Override
            protected Void call() {
                task.run();
                return null;
            }
        };
    }

    /** Load the review of gym*/
    private void loadReview(ResultSet rs){
        graphicController.getReview().getChildren().remove(0, graphicController.getReview().getChildren().size());
        try {
            while (rs.next()) {
                Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
                String string = rs.getString("writer") + ";" + rs.getTimestamp("timestamp").toString();
                labelTitle.setStyle("-fx-font-weight: bold;");
                Label labelReview = new Label(rs.getString("review"));
                Label blankSpace = new Label();
                VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
                if (this.user != null && Objects.equals(this.user.getRole(), "gym") && Objects.equals(this.user.getGymName(), getGymName())) {
                    Label cancel = new Label("⮿");
                    cancel.setStyle("-fx-text-fill: red; ");
                    cancel.setEllipsisString(string);
                    cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> graphicController.cancelReview(e));
                    cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                    cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                    HBox hbox = new HBox(vbox, new Label(), new Label(), new Label(), new Label(), cancel);
                    graphicController.getReview().getChildren().add(hbox);
                } else {
                    graphicController.getReview().getChildren().add(vbox);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /** Download the review*/
    private void downloadReview(){
        DAO obj_DAO = this.mainApp.getDAO();
        ResultSet rs = obj_DAO.Check_Data(
                "SELECT * " +
                        "FROM review " +
                        "WHERE review.gym = \"" + getGymName() + "\"");
        loadReview(rs);
        if(graphicController.getReview().getChildren().size()<1) {
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle("-fx-font-weight: bold;");
            graphicController.getReview().getChildren().add(labelNotFound);
        }
    }

    /** Load the course of gym*/
    private void loadCourse(ResultSet rs){
        graphicController.getCourse().getChildren().remove(1, graphicController.getCourse().getChildren().size());
        try {
            Label label = new Label(rs.getString("sport") + " " + rs.getTime("time").toString().substring(0, 5));
            String s = rs.getString("sport") + ";" + rs.getTime("time").toString();
            label.setStyle("-fx-text-fill: white;");
            if (this.user != null && Objects.equals(this.user.getGymName(), getGymName())) {
                Label cancel = new Label("⮿");
                cancel.setStyle("-fx-text-fill: red;");
                cancel.setEllipsisString(s);
                cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> graphicController.cancelCourse(e));
                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                HBox hbox = new HBox(label, new Label(), new Label(), cancel);
                graphicController.getCourse().getChildren().add(hbox);
            } else {
                graphicController.getCourse().getChildren().add(label);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /** Download Course*/
    private void downloadCourse(){
        try {
            DAO obj_DAO = this.mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM course " +
                            "WHERE course.gym = \"" + getGymName() + "\"");
            while (rs.next()) {
                loadCourse(rs);
            }
            if(graphicController.getCourse().getChildren().size()<2) {
                Label label = new Label("There are no course");
                label.setStyle("-fx-font-weight: bold;");
                graphicController.getCourse().getChildren().add(label);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /** Set Gym View*/
    private void setGym(String name){

        // Handlers
        setupEventHandlers();

        // Window Title
        graphicController.getGym_name().setText(name);

        // Set visible or not area new review and new course
        settingPage();

        // set ComboBox
        Runnable task0 = () -> Platform.runLater(() -> {
            this.sport = getSport();
            graphicController.getCombo_sport().setValue("select sport");
            graphicController.getCombo_sport().setItems(this.sport);
        });
        Task<Void> task00 = createTask(task0);
        task00.setOnRunning(e -> graphicController.getCombo_sport().setCursor(Cursor.WAIT));
        task00.setOnSucceeded(e -> graphicController.getCombo_sport().setCursor(Cursor.DEFAULT));
        task00.setOnFailed(e -> graphicController.getCombo_sport().setCursor(Cursor.DEFAULT));

        // Set gym_description
        graphicController.getGym_description().setText(
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
                        graphicController.getGym_description().setText(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: \\\\");
                    } else {
                        graphicController.getGym_description().setText(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: " + rs.getString("phone"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        Task<Void> task4 = createTask(task1);
        task4.setOnRunning(e -> graphicController.getGym_description().setCursor(Cursor.WAIT));
        task4.setOnSucceeded(e -> graphicController.getGym_description().setCursor(Cursor.DEFAULT));
        task4.setOnFailed(e -> graphicController.getGym_description().setCursor(Cursor.DEFAULT));

        // Set review
        Runnable task2 = () -> Platform.runLater(this::downloadReview);
        Task<Void> task5 = createTask(task2);
        task5.setOnRunning(e -> graphicController.getReview().setCursor(Cursor.WAIT));
        task5.setOnSucceeded(e -> graphicController.getReview().setCursor(Cursor.DEFAULT));
        task5.setOnFailed(e -> {
            graphicController.getReview().setCursor(Cursor.DEFAULT);
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle("-fx-font-weight: bold;");
            graphicController.getReview().getChildren().add(labelNotFound);
        });

        // Set course
        Runnable task3 = () -> Platform.runLater(this::downloadCourse);
        Task<Void> task6 = createTask(task3);
        task6.setOnRunning(e -> graphicController.getCourse().setCursor(Cursor.WAIT));
        task6.setOnSucceeded(e -> graphicController.getCourse().setCursor(Cursor.DEFAULT));
        task6.setOnFailed(e -> {
            graphicController.getCourse().setCursor(Cursor.DEFAULT);
            Label label = new Label("There are no course");
            label.setStyle("-fx-font-weight: bold;");
            graphicController.getCourse().getChildren().add(label);
        });

        // Run Thread and set DEFAULT review and course
        new Thread(task00).start();
        new Thread(task4).start();
        new Thread(task5).start();
        new Thread(task6).start();
    }

    /** It's called to set the controller and view*/
    public void loadingGymName(String name) {
        this.mainApp.getPrimaryStage().setTitle("Sportify - " + name);
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.mainApp.getClass().getResource("DesktopView/GymInfo.fxml"));
            Pane pane = loader.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);

            // Give the controller access to the main app.
            GymInfoGraphicController graphicController = loader.getController();
            this.setGraphicController(graphicController);
            setGym(name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /** It's called to get gym name*/
    public String getGymName(){
        return graphicController.getGym_name().getText();
    }
}
