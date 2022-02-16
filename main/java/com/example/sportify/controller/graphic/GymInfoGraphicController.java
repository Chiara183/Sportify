package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.GymInfoController;
import com.example.sportify.controller.MenuController;
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

public class GymInfoGraphicController implements GraphicController{

    private static final Logger LOGGER = Logger.getLogger(GymInfoController.class.getName());

    public ComboBox comboGymInfo;

    /** Reference to controller*/
    private GymInfoController controller;

    /** All the label of interface*/
    @FXML
    private Label gymName;
    @FXML
    private Label gymDescription;

    // TextArea
    @FXML
    private TextArea reviewArea;

    /** All the text field of interface*/
    @FXML
    private TextField hour;
    @FXML
    private TextField min;

    /** All the border pane of interface*/
    @FXML
    private BorderPane reviewPane;
    @FXML
    private BorderPane coursePane;

    /** All the vbox of interface*/
    @FXML
    private VBox course;
    @FXML
    private VBox review;

    // ComboBox
    @FXML
    private ComboBox<String> comboSport;

    /** All the slider of interface*/
    @FXML
    private Slider hourSlider;
    @FXML
    private Slider minSlider;

    @FXML
    public void comboActivity(){
        Object selectedItem = comboGymInfo.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        if(choice.equals("Course")){
            courseAction();
        }else if(choice.equals("Review")){
            reviewAction();
        }
    }


    /** The action of the button*/
    @FXML
    private void shareReview(){
        String gym;
        if(controller.getMainApp().isNotMobile()) {
            gym = this.gymName.getText();
        } else {
            gym = this.controller.getMenu().getGym();
        }
        StringBuilder reviews = new StringBuilder(this.reviewArea.getText(0, this.reviewArea.getLength()));
        this.controller.shareReview(gym, reviews);
    }
    @FXML
    private void findGym(){
        MenuController menu = controller.getMainApp().menu();
        menu.setFindGym();
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().setSearchCache(controller.getMainApp().getSearchCache());
        controller.getMainApp().showFindGymOverview(menu);

    }
    @FXML
    private void addCourse(){
        String gym;
        if(controller.getMainApp().isNotMobile()) {
            gym = this.gymName.getText();
        } else {
            gym = this.controller.getMenu().getGym();
        }
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
        controller.addCourse(sport, gym, time);
    }

    /** The action that change the value of text field*/
    @FXML
    private void changeHour(){
        String hourSet = String.valueOf((int) hourSlider.getValue());
        if(Integer.parseInt(hourSet)<10) {
            this.hour.setText("0" + hourSet);
        } else {
            this.hour.setText(hourSet);
        }
    }
    @FXML
    private void changeMin(){
        String minSet = String.valueOf((int) minSlider.getValue());
        if(Integer.parseInt(minSet)<10) {
            this.min.setText("0" + minSet);
        } else {
            this.min.setText(minSet);
        }
    }

    /** Is called to set review pane visible or not, true = visible*/
    public void reviewPane_isVisible(boolean visible){
        this.reviewPane.setVisible(visible);
    }

    /** Is called to set course pane visible or not, true = visible*/
    public void coursePane_isVisible(boolean visible){
        this.coursePane.setVisible(visible);
    }

    /** Is called to set gym name*/
    public void setGym_name(String name){
        this.gymName.setText(name);
    }

    /** Is called to set gym description*/
    public void setGymDescription(String description){
        this.gymDescription.setText(description);
    }

    /** Is called to set cursor on gym description label*/
    public void gymDescription_setCursor(Cursor cursor){
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
    public void course_setCursor(Cursor cursor){
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
    public void review_setCursor(Cursor cursor){
        this.review.setCursor(cursor);
    }

    /** Is called to set sport comboBox*/
    public void setComboSport(ObservableList<String> sport){
        this.comboSport.setValue("select sport");
        this.comboSport.setItems(sport);
    }

    /** Is called to set cursor on sport comboBox*/
    public void comboSport_setCursor(Cursor cursor){
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
    private void courseAction() {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.controller.getMainApp().getClass().getResource("SmartphoneView/GymInfoCoursePhone0.fxml"));
            Pane paneTopScreen = controller.setTopMenu();
            Pane pane = loader.load();

            // Set test result overview into the center of root layout.
            this.controller.getMainApp().getPrimaryPane().setCenter(pane);
            controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
            controller.getMenu().setView(ControllerType.COURSE_GYM);

            // Give the controller access to the main app.
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

    @FXML
    private void reviewAction() {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.controller.getMainApp().getClass().getResource("SmartphoneView/GymInfoReviewPhone0.fxml"));
            Pane paneTopScreen = controller.setTopMenu();
            Pane pane = loader.load();

            // Set test result overview into the center of root layout.
            this.controller.getMainApp().getPrimaryPane().setCenter(pane);
            controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
            controller.getMenu().setView(ControllerType.REVIEW_GYM);

            // Give the controller access to the main app.
            GymInfoGraphicController gymInfoGraphicController = loader.getController();
            this.controller.setGraphicController(gymInfoGraphicController);
            this.controller.setUser(this.controller.getMenu().getUser());
            gymInfoGraphicController.setController(this.controller);

            // Set info course
            controller.settingPhoneReview();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
