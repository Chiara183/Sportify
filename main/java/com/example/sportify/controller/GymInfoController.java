package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.DBConnection;
import com.example.sportify.controller.graphic.GraphicController;
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

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GymInfoController extends Controller {

    /** Reference to graphic controller*/
    private GymInfoGraphicController graphicController;

    private static final String SELECT = "SELECT * ";
    private static final String FONT = "-fx-font-weight: bold;";

    /** The name of the gym*/
    private String gym;

    // ObservableList
    private ObservableList<String> sport;

    /** The constructor.*/
    public GymInfoController(){
        this.type = ControllerType.GYM_INFO;
    }

    /** Method that set up the controller*/
    private void setReview(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                graphicController.reviewPane_isVisible(true);
            } else graphicController.reviewPane_isVisible(!Objects.equals(this.user.getGymName(), this.gym));
        } else {
            graphicController.reviewPane_isVisible(false);
        }
    }
    private void setCourse(){
        if(this.user != null){
            if(Objects.equals(this.user.getRole(), "user")){
                graphicController.coursePane_isVisible(false);
            } else graphicController.coursePane_isVisible(Objects.equals(this.user.getGymName(), this.gym));
        } else {
            graphicController.coursePane_isVisible(false);
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
        ObservableList<String> sports = FXCollections.observableArrayList();
        assert this.mainApp != null;
        DAO objDAO = this.mainApp.getDAO();
        String rs = objDAO.checkData(
                SELECT +
                        "FROM sport ", "name");

        sports.add(rs);

        return sports;
    }

    /** Cancel a review of gym*/
    private void cancelReview(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO objDAO = this.mainApp.getDAO();
        objDAO.updateDB("DELETE FROM `review` WHERE `review`.`writer` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `review`.`gym` = '" +
                this.gym +
                "' AND `review`.`timestamp` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        loadingGymName(this.gym);
    }

    /** Cancel a course of gym*/
    private void cancelCourse(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO objDAO = this.mainApp.getDAO();
        objDAO.updateDB("DELETE FROM `course` WHERE `course`.`sport` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `course`.`gym` = '" +
                this.gym +
                "' AND `course`.`time` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        loadingGymName(this.gym);
    }

    /** Cancel a review of gym*/
    public void shareReview(String gym, StringBuilder review){
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
            DAO objDAO = this.mainApp.getDAO();
            objDAO.updateDB(
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

    /** Cancel a course of gym*/
    public void addCourse(String sport, String gym, String time){
        if(!sport.equals("select sport")) {
            DAO objDAO = this.mainApp.getDAO();
            objDAO.updateDB(
                    "INSERT INTO `course` (`sport`, `gym`, `time`) VALUES ('" +
                            sport + "', '" +
                            gym + "', '" +
                            time + "');");
            loadingGymName(gym);
        } else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert all value.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
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
        graphicController.cleanReview();
        try {
            while (rs.next()) {
                Label labelTitle = new Label(rs.getString("writer") + " " + rs.getTimestamp("timestamp").toString());
                String string = rs.getString("writer") + ";" + rs.getTimestamp("timestamp").toString();
                labelTitle.setStyle(FONT);
                Label labelReview = new Label(rs.getString("review"));
                Label blankSpace = new Label();
                VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
                if (this.user != null && Objects.equals(this.user.getRole(), "gym") && Objects.equals(this.user.getGymName(), this.gym)) {
                    Label cancel = new Label("⮿");
                    cancel.setStyle("-fx-text-fill: red; ");
                    cancel.setEllipsisString(string);
                    cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::cancelReview);
                    cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                    cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                    HBox hbox = new HBox(vbox, new Label(), new Label(), new Label(), new Label(), cancel);
                    graphicController.setReview(hbox);
                } else {
                    graphicController.setReview(vbox);
                }
            }
        }catch (SQLException e){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    /** Download the review*/
    private void downloadReview(){
        //DAO objDAO = this.mainApp.getDAO();
        /*ResultSet rs = objDAO.checkData(
                SELECT +
                        "FROM review " +
                        "WHERE review.gym = \"" + this.gym + "\"");
        loadReview(rs);*/
            Connection connection = new DBConnection().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try{
                ps = connection.prepareStatement(SELECT +
                        "FROM course " +
                        "WHERE course.gym = \"" + this.gym + "\"");
                rs = ps.executeQuery();
                while(rs.next()){
                    loadReview(rs);
                }
            }catch (SQLException e) {
                Logger logger = Logger.getLogger(DAO.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        if(graphicController.getSizeReview()<1) {
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle(FONT);
            graphicController.setReview(labelNotFound);
        }

    }

    /** Load the course of gym*/
    private void loadCourse(ResultSet rs){
        graphicController.cleanCourse();
        try {
            Label label = new Label(rs.getString("sport") + " " + rs.getTime("time").toString().substring(0, 5));
            String s = rs.getString("sport") + ";" + rs.getTime("time").toString();
            label.setStyle("-fx-text-fill: white;");
            if (this.user != null && Objects.equals(this.user.getGymName(), this.gym)) {
                Label cancel = new Label("⮿");
                cancel.setStyle("-fx-text-fill: red;");
                cancel.setEllipsisString(s);
                cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::cancelCourse);
                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                HBox hbox = new HBox(label, new Label(), new Label(), cancel);
                graphicController.setCourse(hbox);
            } else {
                graphicController.setCourse(label);
            }
        } catch (SQLException e){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    /** Download Course*/
    private void downloadCourse(){
        Connection connection = new DBConnection().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(SELECT +
                    "FROM course " +
                    "WHERE course.gym = \"" + this.gym + "\"");
            rs = ps.executeQuery();
            while(rs.next()){
                loadCourse(rs);
            }
        }catch (SQLException e) {
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(graphicController.getSizeCourse()<2) {
            Label label = new Label("There are no course");
            label.setStyle(FONT);
            graphicController.setCourse(label);
        }
    }

    /** Set Gym View*/
    private void setGym(String name){

        // Handlers
        setupEventHandlers();

        // Window Title
        graphicController.setGym_name(name);

        // Set visible or not area new review and new course
        settingPage();

        // set ComboBox
        Runnable task0 = () -> Platform.runLater(() -> {
            this.sport = getSport();
            graphicController.setComboSport(this.sport);
        });
        Task<Void> task00 = createTask(task0);
        task00.setOnRunning(e -> graphicController.comboSport_setCursor(Cursor.WAIT));
        task00.setOnSucceeded(e -> graphicController.comboSport_setCursor(Cursor.DEFAULT));
        task00.setOnFailed(e -> graphicController.comboSport_setCursor(Cursor.DEFAULT));

        // Set gym_description
        graphicController.setGymDescription(
                """
                        ADDRESS:\s

                        TELEPHONE:\s""");
        Runnable task1 = () -> Platform.runLater(() -> {

                    DAO objDAO = this.mainApp.getDAO();
                    String rs = objDAO.checkData(
                            SELECT +
                                    "FROM gym " +
                                    "WHERE gym.name = \"" + name + "\"", "phone");
                /*if (rs.next()) {
                    if(Objects.equals(rs.getString("phone"), "null")){
                        graphicController.setGymDescription(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: \\\\");
                    } else {
                        graphicController.setGymDescription(
                                "ADDRESS: " + rs.getString("address") +
                                        "\n\nTELEPHONE: " + rs.getString("phone"));
                    }
                }
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(GymInfoController.class.getName());
                logger.log(Level.SEVERE, e.getMessage());            }
        });*/
                    if (Objects.equals(rs, "null")) {
                        DAO objDAO1 = this.mainApp.getDAO();
                        String rs1 = objDAO1.checkData(
                                SELECT +
                                        "FROM gym " +
                                        "WHERE gym.name = \"" + name + "\"", "address");
                        graphicController.setGymDescription(
                                "ADDRESS: " + rs1 +
                                        "\n\nTELEPHONE: \\\\");
                    }
                });
        Task<Void> task4 = createTask(task1);
        task4.setOnRunning(e -> graphicController.gymDescription_setCursor(Cursor.WAIT));
        task4.setOnSucceeded(e -> graphicController.gymDescription_setCursor(Cursor.DEFAULT));
        task4.setOnFailed(e -> graphicController.gymDescription_setCursor(Cursor.DEFAULT));

        // Set review
        Runnable task2 = () -> Platform.runLater(this::downloadReview);
        Task<Void> task5 = createTask(task2);
        task5.setOnRunning(e -> graphicController.review_setCursor(Cursor.WAIT));
        task5.setOnSucceeded(e -> graphicController.review_setCursor(Cursor.DEFAULT));
        task5.setOnFailed(e -> {
            graphicController.review_setCursor(Cursor.DEFAULT);
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle(FONT);
            graphicController.setReview(labelNotFound);
        });

        // Set course
        Runnable task3 = () -> Platform.runLater(this::downloadCourse);
        Task<Void> task6 = createTask(task3);
        task6.setOnRunning(e -> graphicController.course_setCursor(Cursor.WAIT));
        task6.setOnSucceeded(e -> graphicController.course_setCursor(Cursor.DEFAULT));
        task6.setOnFailed(e -> {
            graphicController.course_setCursor(Cursor.DEFAULT);
            Label label = new Label("There are no course");
            label.setStyle(FONT);
            graphicController.setCourse(label);
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
            GymInfoGraphicController gymInfoGraphicController = loader.getController();
            this.setGraphicController(gymInfoGraphicController);
            gymInfoGraphicController.setController(this);
            this.gym = name;
            this.mainApp.setSearchCache(this.searchCache);
            setGym(name);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (GymInfoGraphicController) graphicController;
    }
}
