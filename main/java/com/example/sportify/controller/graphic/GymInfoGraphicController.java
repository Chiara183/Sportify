package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.GymInfoController;
import com.example.sportify.controller.MenuController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GymInfoGraphicController extends GraphicController{

    /** Reference to controller*/
    private GymInfoController controller;

    /** All the label of interface*/
    @FXML
    private Label gym_name;
    @FXML
    private Label gym_description;

    // TextArea
    @FXML
    private TextArea review_area;

    /** All the text field of interface*/
    @FXML
    private TextField hour;
    @FXML
    private TextField min;

    /** All the border pane of interface*/
    @FXML
    private BorderPane review_pane;
    @FXML
    private BorderPane course_pane;

    /** All the vbox of interface*/
    @FXML
    private VBox course;
    @FXML
    private VBox review;

    // ComboBox
    @FXML
    private ComboBox<String> combo_sport;

    /** All the slider of interface*/
    @FXML
    private Slider hour_slider;
    @FXML
    private Slider min_slider;

    /** The action of the button*/
    @FXML
    private void share_review(){
        String gym = this.gym_name.getText();
        StringBuilder review = new StringBuilder(this.review_area.getText(0, this.review_area.getLength()));
        this.controller.shareReview(gym, review);
    }
    @FXML
    private void findGym(){
        MenuController menu = controller.getMainApp().Menu();
        menu.setFindGym();
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().setSearchCache(controller.getMainApp().getSearchCache());
        controller.getMainApp().showFindGymOverview(menu);

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
        controller.addCourse(sport, gym, time);
    }

    /** The action that change the value of text field*/
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

    /** Is called to set review pane visible or not, true = visible*/
    public void reviewPane_isVisible(boolean visible){
        this.review_pane.setVisible(visible);
    }

    /** Is called to set course pane visible or not, true = visible*/
    public void coursePane_isVisible(boolean visible){
        this.course_pane.setVisible(visible);
    }

    /** Is called to get gym name*/
    public void setGym_name(String name){
        this.gym_name.setText(name);
    }

    /** Is called to set gym description*/
    public void setGymDescription(String description){
        this.gym_description.setText(description);
    }

    /** Is called to set cursor on gym description label*/
    public void gymDescription_setCursor(Cursor cursor){
        this.gym_description.setCursor(cursor);
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
        this.combo_sport.setValue("select sport");
        this.combo_sport.setItems(sport);
    }

    /** Is called to set cursor on sport comboBox*/
    public void comboSport_setCursor(Cursor cursor){
        this.combo_sport.setCursor(cursor);
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (GymInfoController) controller;
    }
}
