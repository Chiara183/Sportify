package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.bean.GymInfoBean;
import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.MenuController;
import it.uniroma2.dicii.ispw.sportify.pattern.Subject;
import it.uniroma2.dicii.ispw.sportify.controller.GymInfoController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GymInfoGraphicController extends Subject implements GraphicController{

    protected static final Logger LOGGER = Logger.getLogger(GymInfoGraphicController.class.getName());

    @FXML
    private ComboBox<String> comboGymInfo;

    private String gym = "";

    /** Reference to gymEditController*/
    protected GymInfoController controller;

    /** Reference to bean*/
    protected final GymInfoBean bean = new GymInfoBean();

    /**Attribute of concrete subject*/
    protected String subjectState = "Unchanged";

    /* All the label of interface*/
    @FXML
    protected Label gymName;
    @FXML
    protected Label gymDescription;

    // TextArea
    @FXML
    protected TextArea reviewArea;

    /* All the text field of interface*/
    @FXML
    protected TextField hour;
    @FXML
    protected TextField min;

    /* All the border pane of interface*/
    @FXML
    protected BorderPane reviewPane;
    @FXML
    protected BorderPane coursePane;

    /* All the vbox of interface*/
    @FXML
    protected VBox course;
    @FXML
    protected VBox review;

    // ComboBox
    @FXML
    protected ComboBox<String> comboSport;

    /* All the slider of interface*/
    @FXML
    protected Slider hourSlider;
    @FXML
    protected Slider minSlider;

    @FXML
    public void comboActivity(){
        Object selectedItem = comboGymInfo.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        if(choice.equals("Course")){
            courseAction(this.gym);
        }else if(choice.equals("Review")){
            reviewAction(this.gym);
        }
    }


    /* The action of the button*/
    @FXML
    protected void shareReview(){
        if(!this.bean.checkReview(this.gym, this.reviewArea)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill gym name field and review field");
            alert.showAndWait();
        }
        StringBuilder reviews = new StringBuilder(this.reviewArea.getText(0, this.reviewArea.getLength()));
        this.controller.shareReview(this.gym, reviews);
    }
    @FXML
    private void findGym(){
        MenuController menu = MainApp.menu();
        menu.setFindGym();
        MainApp.setUser(controller.getUser());
        MainApp.setSearchCache(MainApp.getSearchCache());
        MainApp.showOverview(ControllerType.FIND_GYM);

    }
    @FXML
    protected void notifyAddCourse(){
        String sport = this.comboSport.getValue();
        String hours;
        if(this.hour.getText().equals("")){
            hours = "01";
        } else {
            hours = this.hour.getText();
        }
        String mins;
        if(this.min.getText().equals("")){
            mins = "00";
        } else {
            mins = this.min.getText();
        }
        String time = hours + ':' + mins + ":00";
        this.setState("Changed");
        controller.addCourse(sport, this.gym, time);
    }

    public void setState(String newState){
        this.subjectState = newState;
    }

    public String getState(){
        return subjectState;
    }

    /** The action that change the value of text field*/
    @FXML
    private void changeHour(){
        String hourSet = bean.getHour(this.hourSlider);
        if(Integer.parseInt(hourSet)<10) {
            this.hour.setText("0" + hourSet);
        } else {
            this.hour.setText(hourSet);
        }
    }
    @FXML
    private void changeMin(){
        String minSet = bean.getMin(this.minSlider);
        if(Integer.parseInt(minSet)<10) {
            this.min.setText("0" + minSet);
        } else {
            this.min.setText(minSet);
        }
    }

    /** Is called to set review pane visible or not, true = visible*/
    public void reviewPaneIsVisible(boolean visible){
        this.reviewPane.setVisible(visible);
    }

    /** Is called to set course pane visible or not, true = visible*/
    public void coursePaneIsVisible(boolean visible){
        this.coursePane.setVisible(visible);
    }

    /** Is called to set gym name*/
    public void setGymName(String name){
        if (this.gymName != null) {
            this.gymName.setText(name);
        }
        this.gym = name;
    }

    /** Is called to set gym description*/
    public void setGymDescription(String description){
        this.gymDescription.setText(description);
    }

    /** Is called to set cursor on gym description label*/
    public void gymDescriptionSetCursor(Cursor cursor){
        this.gymDescription.setCursor(cursor);
    }

    /** Is called to clean all course on view*/
    public void cleanCourse(){
        this.course.getChildren().remove(1, this.course.getChildren().size());
    }

    /** Is called to get course vbox*/
    public void setCourse(Node node){
        this.course.getChildren().add(node);
    }

    /** Is called to get size of course vbox*/
    public int getSizeCourse(){
        return this.course.getChildren().size();
    }

    /** Is called to set cursor on course vbox*/
    public void courseSetCursor(Cursor cursor){
        this.course.setCursor(cursor);
    }

    /** Is called to clean all course on view*/
    public void cleanReview(){
        this.review.getChildren().remove(0, this.review.getChildren().size());
    }

    /** Is called to get course vbox*/
    public void setReview(Node node){
        this.review.getChildren().add(node);
    }

    /** Is called to get size of course vbox*/
    public int getSizeReview(){
        return this.review.getChildren().size();
    }

    /** Is called to set cursor on course vbox*/
    public void reviewSetCursor(Cursor cursor){
        this.review.setCursor(cursor);
    }

    /** Is called to set sport comboBox*/
    public void setComboSport(ObservableList<String> sport){
        this.comboSport.setValue("select sport");
        this.comboSport.setItems(sport);
    }

    /** Is called to set cursor on sport comboBox*/
    public void comboSportSetCursor(Cursor cursor){
        this.comboSport.setCursor(cursor);
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (GymInfoController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }

    /** Is called to show course window*/
    @FXML
    protected void courseAction(String gymName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            helpMethod(loader);

            // Give the gymEditController access to the main app.
            GymInfoGraphicController gymInfoGraphicController = loader.getController();
            this.controller.setGraphicController(gymInfoGraphicController);
            this.controller.setUser(this.controller.getMenu().getUser());
            gymInfoGraphicController.setController(this.controller);

            // Set info course
            controller.settingPhoneCourse();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void helpMethod(FXMLLoader loader) throws IOException {
        // Load test result overview.
        loader.setLocation(MainApp.class.getResource("SmartphoneView/GymInfoCoursePhone0.fxml"));
        Pane paneTopScreen = controller.setTopMenu();
        Pane pane = loader.load();

        // Set test result overview into the center of root layout.
        MainApp.getPrimaryPane().setCenter(pane);
        MainApp.getPrimaryPane().setTop(paneTopScreen);
        controller.getMenu().setView(ControllerType.COURSE_GYM);
    }

    @FXML
    protected void reviewAction(String gymName) {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            helpMethod1(loader);

            // Give the gymEditController access to the main app.
            GymInfoGraphicController gymInfoGraphicController = loader.getController();
            this.controller.setGraphicController(gymInfoGraphicController);
            this.controller.setUser(this.controller.getMenu().getUser());
            gymInfoGraphicController.setController(this.controller);

            // Set info course
            controller.settingPhoneReview(gymName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void helpMethod1(FXMLLoader loader) throws IOException {
        loader.setLocation(MainApp.class.getResource("SmartphoneView/GymInfoReviewPhone0.fxml"));
        Pane paneTopScreen = controller.setTopMenu();
        Pane pane = loader.load();

        // Set test result overview into the center of root layout.
        MainApp.getPrimaryPane().setCenter(pane);
        MainApp.getPrimaryPane().setTop(paneTopScreen);
        controller.getMenu().setView(ControllerType.REVIEW_GYM);
    }

    public ComboBox<String> getComboGymInfo() {
        return this.comboGymInfo;
    }
}
