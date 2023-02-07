package sportify.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sportify.MainApp;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymInfoGraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.model.dao.GymInfoDAO;
import sportify.model.domain.User;
import sportify.pattern.Observer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The `GymInfoController` class extends the `Controller`
 * class and implements the `Observer` interface.
 * It provides a variety of functions related to retrieving
 * and setting information about gyms.
 *
 * @see Observer
 * @see Controller
 */
public class GymInfoController extends Controller implements Observer {

    /**
     * A reference to the `GymInfoGraphicController` object
     */
    private GymInfoGraphicController graphicController;

    /**
     * A constant string used to set font weight
     */
    private static final String FONT = "-fx-font-weight: bold;";

    /**
     * The name of the gym
     */
    private String gym;

    /**
     * The DAO for the class
     */
    private GymInfoDAO dao;

    /**
     * A list of sports available at the gym
     */
    private ObservableList<String> sport;

    /**
     * The default constructor for the `GymInfoController` class.
     */
    public GymInfoController(){
        this.type = ControllerType.GYM_INFO;
    }

    /**
     * Sets the main application for the controller.
     *
     * @param mainApp The reference to the main application.
     */
    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.dao = new GymInfoDAO(mainApp.getDAO());
    }

    /* Method that set up the gymEditController*/
    /**
     * A private method used to set the review information.
     */
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

    /**
     * This method sets the course details
     * for the selected gym.
     */
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

    /**
     * This method sets up the information
     * page for the selected gym.
     */
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

    /**
     * This method returns the name of
     * the selected gym.
     *
     * @return the name of the selected
     * gym as a string
     */
    public String getGym() {
        return gym;
    }

    /**
     * This method cancels the user's review for
     * the selected gym.
     *
     * @param e the mouse event that triggered
     *          the cancellation
     */
    private void cancelReview(MouseEvent e){
        Label event = (Label) e.getSource();
        String query00 = event.getEllipsisString();
        String[] query0 = query00.split(";");
        String writer = query0[0];
        String timestamp = query0[1];
        String gymName = getGym();
        try {
            dao.cancelReview(writer, gymName, timestamp);
        } catch (SQLException ex){
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, ex.getMessage());
        }
        loadingGymName(getGym());
    }

    /**
     * This method cancels the user's course
     * booking for the selected gym.
     *
     * @param e the mouse event that triggered
     *          the cancellation
     */
    private void cancelCourse(MouseEvent e){
        Label event = (Label) e.getSource();
        String query00 = event.getEllipsisString();
        String[] query0 = query00.split(";");
        String course = query0[0];
        String time = query0[1];
        dao.cancelCourse(course, getGym(), time);
        loadingGymName(this.gym);
    }

    /**
     * This method shares a review for the selected gym.
     *
     * @param gym the name of the selected gym
     * @param review the review to be shared, represented
     *              as a StringBuilder object
     */
    public void shareReview(String gym, StringBuilder review){
        String str = gym + ": " + review;
        Logger logger = Logger.getLogger(GymInfoController.class.getName());
        logger.log(Level.INFO, str);
        dao.shareReview(gym, review, this.user);
        if(mainApp.isNotMobile()) {
            loadingGymName(gym);
        }
        else {
            settingPhoneReview(gym);
        }
    }

    /**
     * This method adds a course booking for the selected gym.
     *
     * @param sport the name of the sport for which
     *              the booking is being made
     * @param gym the name of the selected gym
     * @param time the time of the booking
     */
    public void addCourse(String sport, String gym, String time){
        String observerState = graphicController.getState();
        if(observerState.equals("Unchanged")){
            return;
        }
        if(!sport.equals("select sport")) {
            dao.addCourse(sport, gym, time);
            loadingGymName(gym);
        }
        else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert all value.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method sets up event handlers
     * for various UI elements on the gym
     * information page.
     */
    private void setupEventHandlers() {
        this.menu.getSignOut()
                .addEventHandler(
                        MouseEvent.MOUSE_CLICKED,
                        event -> settingPage()
                );
    }

    /**
     * Creates a new task and returns it.
     *
     * @param task The task to be executed.
     *
     * @return The created task.
     */
    private Task<Void> createTask(Runnable task){
        return new Task<>() {
            @Override
            protected Void call() {
                task.run();
                return null;
            }
        };
    }

    /**
     * Loads reviews for a gym.
     *
     * @param writer List of writers of the reviews.
     * @param time List of timestamps for the reviews.
     * @param review List of reviews for the gym.
     */
    private void loadReview(List<String> writer, List<String> time, List<String> review){
        User user = getUser();
        String role = user.getRole();
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
                    Objects.equals(user.getGymName(), getGym())) {
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

    /**
     * Downloads review for the specified gym
     *
     * @param gym Name of the gym to download
     *            the review for
     */
    private void downloadReview(String gym){
        graphicController.cleanReview();
        List<String> review;
        List<String> writer;
        List<String> time;
        String rev = "review";
        review = dao.checkDataColumnGymInfo(gym, rev, rev);
        writer = dao.checkDataColumnGymInfo(gym, rev, "writer");
        time = dao.checkDataColumnGymInfo(gym, rev, "timestamp");
        assert review != null;
        loadReview(writer, time, review);
        if(graphicController.getSizeReview()<1) {
            Label labelNotFound = new Label("There are no reviews");
            labelNotFound.setStyle(FONT);
            graphicController.setReview(labelNotFound);
        }

    }

    /**
     * Loads information about a specific sport and time
     *
     * @param sport The sport to load information for
     * @param time The time the sport takes place
     */
    private void loadCourse(String sport, String time){
        Label label = new Label(sport + " " + time.substring(0, 5));
        String s = sport + ";" + time;
        label.setStyle("-fx-text-fill: white;");
        if (this.user != null && Objects.equals(this.user.getRole(), "gym") && Objects.equals(this.user.getGymName(), this.gym)) {
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

    /**
     * Downloads information about a course
     */
    private void downloadCourse(){
        graphicController.cleanCourse();
        List<String> sportList;
        List<String> time;
        sportList = dao.checkDataColumnGymInfo(this.gym, "course", "sport");
        time = dao.checkDataColumnGymInfo(this.gym, "course", "time");
        int i = 0;
        while (i != sportList.size()) {
            loadCourse(sportList.get(i), time.get(i));
            i++;
        }
        if(graphicController.getSizeCourse()<2) {
            Label label = new Label("There are no course");
            label.setStyle(FONT);
            graphicController.setCourse(label);
        }
    }

    /**
     * Sets information about a course
     */
    private void setInfoCourse(){
        // set ComboBox
        Runnable task = () -> Platform.runLater(() ->
        {
            this.sport = dao.getSportList(mainApp);
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

    /**
     * Sets information about a phone course
     */
    public void settingPhoneCourse(){
        // Set visible or not area new course
        setCourse();

        // Set info course
        setInfoCourse();
    }

    /**
     * Sets the gym information
     *
     * @param name The name of the gym
     */
    private void setGym(String name){

        // Window Title
        graphicController.setGymName(name);

        // Set gym_description
        graphicController.setGymDescription(
                """
                        ADDRESS:\s

                        TELEPHONE:\s""");
        Runnable task1 = () -> Platform.runLater(() -> {

            String rs;
            List<String> list1;
            List<String> list = dao.checkDataColumnGymInfo(name, "gym", "phone");
            rs = list.get(0);
            list1 = dao.checkDataColumnGymInfo(name, "gym", "address");
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

    /**
     * Loads information about the gym with the specified name
     *
     * @param name The name of the gym to load information for
     */
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
            if(mainApp.isMobile()){
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

    /**
     * Sets the graphic controller for this class
     *
     * @param graphicController The graphic controller
     *                          to set
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (GymInfoGraphicController) graphicController;
    }

    /**
     * Is called to set the top menu
     *
     * @return A Pane representing the top menu
     */
    public Pane setTopMenu(){
        FXMLLoader loaderTopScreen = new FXMLLoader();
        loaderTopScreen.setLocation(this.mainApp.getClass().getResource("SmartphoneView/topScreen0.fxml"));
        Pane paneTopScreen = null;
        try {
            paneTopScreen = loaderTopScreen.load();
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(GymInfoController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        MenuGraphicController graphicMenuController = loaderTopScreen.getController();
        graphicMenuController.setController(this.menu);
        return paneTopScreen;
    }

    /**
     * Is called to set the phone review window
     *
     * @param gymName The name of the gym to set the review for
     */
    public void settingPhoneReview(String gymName) {
        // Set visible or not area new review
        setReview();

        //Set review
        setInfoReview(gymName);
    }

    /**
     * Sets information about a review for the specified gym
     *
     * @param gym The name of the gym to set the review for
     */
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
