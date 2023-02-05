package sportify.controller;

import sportify.DAO;
import sportify.Observer;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymInfoGraphicController;
import sportify.controller.graphic.MenuGraphicController;
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
import sportify.errorlogic.DAOException;
import sportify.user.User;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GymInfoController extends Controller implements Observer {

    /** Reference to graphicController*/
    private GymInfoGraphicController graphicController;

    private static final String SELECT = "SELECT * ";
    private static final String FONT = "-fx-font-weight: bold;";

    /** The name of the gym*/
    private String gym;

    private ObservableList<String> sport;

    /** The constructor.*/
    public GymInfoController(){
        this.type = ControllerType.GYM_INFO;
    }

    /* Method that set up the gymEditController*/
    private void setReview(){
        User user = getUser();
        String role;
        String gymName;
        if(getUser() != null){
            role = user.getRole();
            if(Objects.equals(role, "user")){
                graphicController.reviewPaneIsVisible(true);
            }
            else {
                gymName = user.getGymName();
                graphicController.reviewPaneIsVisible(!Objects.equals(gymName, this.gym));
            }
        } else {
            graphicController.reviewPaneIsVisible(false);
        }
    }
    private void setCourse(){
        User user = getUser();
        String role;
        String gymName;
        if(getUser() != null){
            role = user.getRole();
            if(Objects.equals(role, "user")){
                graphicController.coursePaneIsVisible(false);
            }
            else {
                gymName = user.getGymName();
                graphicController.coursePaneIsVisible(Objects.equals(gymName, this.gym));
            }
        } else {
            graphicController.coursePaneIsVisible(false);
        }
    }
    private void settingPage(){
        this.user = this.menu.getUser();
        Runnable task = () -> Platform.runLater(
                () -> {
            setReview();
            setCourse();
        }
        );
        Task<Void> task1 = createTask(task);
        task1.setOnRunning(e ->
                this.mainApp.getPrimaryPane().setCursor(Cursor.WAIT)
        );
        task1.setOnSucceeded(e ->
                this.mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT)
        );
        task1.setOnFailed(e ->
                this.mainApp.getPrimaryPane().setCursor(Cursor.DEFAULT)
        );
        new Thread(task1).start();
    }

    public String getGym() {
        return gym;
    }

    private ObservableList<String> getSport(){
        assert this.mainApp != null;
        String query = SELECT +
                "FROM sport ";
        DAO objDAO = this.mainApp.getDAO();
        ObservableList<String> result = null;
        try {
            List<String> list = objDAO.checkData(query, "name");
            result = FXCollections.observableArrayList(list);
        }
        catch(DAOException e){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }

    /** Cancel a review of gym*/
    private void cancelReview(MouseEvent e){
        Label event = (Label) e.getSource();
        String query00 = event.getEllipsisString();
        String[] query0 = query00.split(";");
        String query1 = query0[0];
        String query2 = query0[1];
        String gym = getGym();
        String query = "DELETE FROM `review` " +
                "WHERE `review`.`writer` = '" +
                 query1+
                "' AND `review`.`gym` = '" +
                gym +
                "' AND `review`.`timestamp` = '" +
                query2+
                "'";
        DAO objDAO = this.mainApp.getDAO();
        try {
            objDAO.updateDB(query);
        }
        catch(DAOException ex){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, ex.getMessage());
        }
        loadingGymName(getGym());
    }

    /** Cancel a course of gym*/
    private void cancelCourse(MouseEvent e){
        Label event = (Label) e.getSource();
        String query00 = event.getEllipsisString();
        String[] query0 = query00.split(";");
        String query1 = query0[0];
        String query2 = query0[1];
        String query = "DELETE FROM `course`" +
                " WHERE `course`.`sport` = '" +
                query1 +
                "' AND `course`.`gym` = '" +
                getGym() +
                "' AND `course`.`time` = '" +
                query2 +
                "'";
        DAO objDAO = this.mainApp.getDAO();
        try {
            objDAO.updateDB(query);
        }
        catch(DAOException ex){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, ex.getMessage());
        }
        loadingGymName(this.gym);
    }

    /** Share a review of gym*/
    public void shareReview(String gym, StringBuilder review){
        String str = gym + ": " + review;
        Logger logger = Logger.getLogger(GymInfoController.class.getName());
        logger.log(Level.INFO, str);
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
            String query = "INSERT INTO `review` " +
                    "(`gym`, `review`, `writer`, " +
                    "`timestamp`) " +
                    "VALUES ('"
                    + gym + "', '"
                    + review + "', '"
                    + user + "', " +
                    "CURRENT_TIMESTAMP);";
            try {
                objDAO.updateDB(query);
            }
            catch(DAOException e){
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Review is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        if(mainApp.isNotMobile()) {
            loadingGymName(gym);
        }
        else {
            settingPhoneReview(gym);
        }
    }

    /** Add a course of gym*/
    public void addCourse(String sport, String gym, String time){
        String observerState = graphicController.getState();
        String query = "INSERT INTO `course`" +
                " (`sport`, `gym`," +
                " `time`) VALUES ('" +
                sport + "', '" +
                gym + "', '" +
                time + "');";
        if(observerState.equals("Unchanged")){
            return;
        }
        if(!sport.equals("select sport")) {
            DAO objDAO = this.mainApp.getDAO();
            try {
                objDAO.updateDB(query);
            }
            catch(DAOException e){
                Logger logger = Logger.getLogger(GymInfoController.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
            loadingGymName(gym);
        }
        else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert all value.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** initializes the event handlers.*/
    private void setupEventHandlers() {
        this.menu.getSignOut()
                .addEventHandler(
                        MouseEvent.MOUSE_CLICKED,
                        event -> settingPage()
                );
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
    private void loadReview(List<String> writer, List<String> time, List<String> review){
        graphicController.cleanReview();
        User user = getUser();
        String role = user.getRole();
        String gymName = user.getGymName();
        int i = 0;
        while (i != review.size()) {
            Label labelTitle = new Label(writer.get(i) + " " + time.get(i));
            String string = writer.get(i) + ";" + time.get(i);
            labelTitle.setStyle(FONT);
            Label labelReview = new Label(review.get(i));
            Label blankSpace = new Label();
            VBox vbox = new VBox(labelTitle, labelReview, blankSpace);
            if (getUser() != null &&
                    Objects.equals(role, "gym") &&
                    Objects.equals(gymName, getGym())) {
                Label cancel = new Label("⮿");
                cancel.setStyle("-fx-text-fill: red; ");
                cancel.setEllipsisString(string);
                cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::cancelReview);
                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> cancel.setCursor(Cursor.HAND));
                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cancel.setCursor(Cursor.DEFAULT));
                HBox hbox = new HBox(vbox, new Label(), new Label(), new Label(), new Label(), cancel);
                graphicController.setReview(hbox);
            }
            else {
                graphicController.setReview(vbox);
            }
            i++;
        }
    }

    /** Download the review*/
    private void downloadReview(String gym){
        DAO dao = mainApp.getDAO();
        List<String> review = new ArrayList<>();
        List<String> writer = new ArrayList<>();
        List<String> time = new ArrayList<>();
        String query = SELECT +
                "FROM review " +
                "WHERE review.gym = \""+ gym +"\"";
        try {
            review = dao.checkData(query, "review");
            writer = dao.checkData(query, "writer");
            time = dao.checkData(query, "timestamp");
        }
        catch(DAOException e){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert review != null;
        loadReview(writer, time, review);
        if(graphicController.getSizeReview()<1) {
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle(FONT);
            graphicController.setReview(labelNotFound);
        }

    }

    /** Load the course of gym*/
    private void loadCourse(String sport, String time){
        graphicController.cleanCourse();
        Label label = new Label(sport + " " + time.substring(0, 5));
        String s = sport + ";" + time;
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
    }

    /** Download Course*/
    private void downloadCourse(){
        DAO dao = mainApp.getDAO();
        String query = SELECT +
                "FROM course " +
                "WHERE course.gym =" +
                " \""+ this.gym +"\"";
        List<String> sportList = null;
        List<String> time = null;
        try {
            sportList = dao.checkData(query, "sport");
            time = dao.checkData(query, "time");
        }
        catch(DAOException e){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        int i = 0;
        while(true){
            assert sportList != null;
            if (i == sportList.size()){
                break;
            }
            assert time != null;
            loadCourse(sportList.get(i), time.get(i));
            i++;
        }
        if(graphicController.getSizeCourse()<2) {
            Label label = new Label("There are no course");
            label.setStyle(FONT);
            graphicController.setCourse(label);
        }
    }

    /** Set course*/
    private void setInfoCourse(){
        // set ComboBox
        Runnable task = () -> Platform.runLater(() ->
        {
            this.sport = getSport();
            graphicController.setComboSport(this.sport);
        }
        );
        Task<Void> task0 = createTask(task);
        task0.setOnRunning(e ->
                graphicController.comboSportSetCursor(Cursor.WAIT)
        );
        task0.setOnSucceeded(e ->
                graphicController.comboSportSetCursor(Cursor.DEFAULT)
        );
        task0.setOnFailed(e ->
                graphicController.comboSportSetCursor(Cursor.DEFAULT)
        );

        // Set course
        Runnable task1 = () -> Platform.runLater(this::downloadCourse);
        Task<Void> task2 = createTask(task1);
        task2.setOnRunning(e ->
                graphicController.courseSetCursor(Cursor.WAIT)
        );
        task2.setOnSucceeded(e ->
                graphicController.courseSetCursor(Cursor.DEFAULT)
        );
        task2.setOnFailed(e ->
        {
            graphicController.courseSetCursor(Cursor.DEFAULT);
            Label label = new Label("There are no course");
            label.setStyle(FONT);
            graphicController.setCourse(label);
        }
        );

        // Run Thread and set DEFAULT review and course
        new Thread(task0).start();
        new Thread(task2).start();
    }

    /** Is called to set phone course window*/
    public void settingPhoneCourse(){
        // Set visible or not area new course
        setCourse();

        // Set info course
        setInfoCourse();
    }

    /** Set Gym View*/
    private void setGym(String name){

        // Window Title
        graphicController.setGymName(name);

        // Set gym_description
        graphicController.setGymDescription(
                """
                        ADDRESS:\s

                        TELEPHONE:\s""");
        Runnable task1 = () -> Platform.runLater(() -> {

                    DAO objDAO = this.mainApp.getDAO();
                    String rs = null;
                    List<String> list1 = null;
                    String query = SELECT +
                            "FROM gym " +
                            "WHERE gym.name = \"" + name + "\"";
                    try {
                        List<String> list = objDAO.checkData(query, "phone");
                        rs = list.get(0);
                        DAO objDAO1 = this.mainApp.getDAO();
                        query = SELECT +
                                "FROM gym " +
                                "WHERE gym.name = \"" + name + "\"";
                        list1 = objDAO1.checkData(query, "address");
                    }
                    catch(DAOException e){
                        Logger logger = Logger.getLogger(GymInfoController.class.getName());
                        logger.log(Level.SEVERE, e.getMessage());
                    }
            assert list1 != null;
            String rs1 = list1.get(0);
                    graphicController.setGymDescription(
                            "ADDRESS: " + rs1 +
                                    "\n\nTELEPHONE: " + rs);
                });
        Task<Void> task4 = createTask(task1);
        task4.setOnRunning(e ->
                graphicController.gymDescriptionSetCursor(Cursor.WAIT)
        );
        task4.setOnSucceeded(e ->
                graphicController.gymDescriptionSetCursor(Cursor.DEFAULT)
        );
        task4.setOnFailed(e ->
                graphicController.gymDescriptionSetCursor(Cursor.DEFAULT)
        );

        if(mainApp.isNotMobile()) {
            // Handlers
            setupEventHandlers();

            // Set visible or not area new review and new course
            settingPage();

            // Set info course
            setInfoCourse();

            // Set review
            setInfoReview(name);
        }

        // Run Thread and set DEFAULT review and course
        new Thread(task4).start();
    }

    /** It's called to set the View*/
    public void loadingGymName(String name) {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            Pane paneTopScreen = null;
            MenuGraphicController graphicMenuController = null;
            URL url;
            if(mainApp.isNotMobile()) {
                url = this.mainApp.getClass().getResource("DesktopView/GymInfo.fxml");
                loader.setLocation(url);
            }
            else {
                url = this.mainApp.getClass().getResource("SmartphoneView/GymInfoPhone1.fxml");
                loader.setLocation(url);
                FXMLLoader loaderTopScreen = new FXMLLoader();
                url = this.mainApp.getClass().getResource("SmartphoneView/topScreen1.fxml");
                loaderTopScreen.setLocation(url);
                paneTopScreen = loaderTopScreen.load();
                graphicMenuController = loaderTopScreen.getController();
            }
            Pane pane = loader.load();

            // Set test result overview into the center of root layout.
            this.mainApp.getPrimaryPane().setCenter(pane);
            if(!mainApp.isNotMobile()){
                mainApp.getPrimaryPane().setTop(paneTopScreen);
                assert graphicMenuController != null;
                graphicMenuController.setController(menu);
            }

            // Give the gymEditController access to the main app.
            GymInfoGraphicController gymInfoGraphicController = loader.getController();
            this.setGraphicController(gymInfoGraphicController);
            gymInfoGraphicController.setController(this);
            this.gym = name;
            this.mainApp.setSearchCache(this.searchCache);
            setGym(name);
            if(!this.getMainApp().isNotMobile()) {
                graphicController.getComboGymInfo().getItems().add("Course");
                graphicController.getComboGymInfo().getItems().add("Review");
            }
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to set graphicController*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (GymInfoGraphicController) graphicController;
    }

    /** Is called to set top menu*/
    public Pane setTopMenu(){
        FXMLLoader loaderTopScreen = new FXMLLoader();
        loaderTopScreen.setLocation(this.mainApp.getClass().getResource("SmartphoneView/topScreen0.fxml"));
        Pane paneTopScreen = null;
        try {
            paneTopScreen = loaderTopScreen.load();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        MenuGraphicController graphicMenuController = loaderTopScreen.getController();
        graphicMenuController.setController(this.menu);
        return paneTopScreen;
    }

    /** Is called to set phone review window*/
    public void settingPhoneReview(String gymName) {
        // Set visible or not area new review
        setReview();

        //Set review
        setInfoReview(gymName);
    }

    private void setInfoReview(String gym) {
        Runnable task2 = () -> Platform.runLater(() -> downloadReview(gym));
        Task<Void> task5 = createTask(task2);
        task5.setOnRunning(e -> graphicController.reviewSetCursor(Cursor.WAIT));
        task5.setOnSucceeded(e -> graphicController.reviewSetCursor(Cursor.DEFAULT));
        task5.setOnFailed(e -> {
            graphicController.reviewSetCursor(Cursor.DEFAULT);
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle(FONT);
            graphicController.setReview(labelNotFound);
        });

        // Run Thread and set DEFAULT review and course
        new Thread(task5).start();
    }
}
