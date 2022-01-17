package com.example.sportify.controller.graphic;

import com.example.sportify.DAO;
import com.example.sportify.controller.GymInfoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class GymInfoGraphicController extends GraphicController{
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

    /** Cancel a review of gym*/
    @FXML
    public void cancelReview(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO obj_DAO = controller.getMainApp().getDAO();
        obj_DAO.updateDB("DELETE FROM `review` WHERE `review`.`writer` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `review`.`gym` = '" +
                this.gym_name.getText() +
                "' AND `review`.`timestamp` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        controller.loadingGymName(gym_name.getText());
    }

    /** Cancel a course of gym*/
    @FXML
    public void cancelCourse(MouseEvent e){
        Label event = (Label) e.getSource();
        DAO obj_DAO = controller.getMainApp().getDAO();
        obj_DAO.updateDB("DELETE FROM `course` WHERE `course`.`sport` = '" +
                event.getEllipsisString().split(";")[0] +
                "' AND `course`.`gym` = '" +
                this.gym_name.getText() +
                "' AND `course`.`time` = '" +
                event.getEllipsisString().split(";")[1] +
                "'");
        controller.loadingGymName(gym_name.getText());
    }

    /** The action of the button*/
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
        String user = controller.getUser().getUserName();
        if(!review.toString().equals("")) {
            DAO obj_DAO = controller.getMainApp().getDAO();
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
        controller.loadingGymName(gym);
    }
    @FXML
    private void findGym(){
        controller.getMainApp().setUser(controller.getUser());
        controller.getMainApp().setSearchCache(controller.getMainApp().getSearchCache());
        controller.getMainApp().showFindGymOverview(controller.getMainApp().Menu());
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
            DAO obj_DAO = controller.getMainApp().getDAO();
            obj_DAO.updateDB(
                    "INSERT INTO `course` (`sport`, `gym`, `time`) VALUES ('" +
                            sport + "', '" +
                            gym + "', '" +
                            time + "');");
            controller.loadingGymName(gym_name.getText());
        } else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Insert all value.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
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

    /** Reference to controller*/
    private GymInfoController controller;

    /** Is called to set controller*/
    public void setController(GymInfoController controller) {
        this.controller = controller;
    }

    /** Is called to get review pane*/
    public BorderPane getReview_pane(){
        return this.review_pane;
    }

    /** Is called to get course pane*/
    public BorderPane getCourse_pane(){
        return this.course_pane;
    }

    /** Is called to get gym name label*/
    public Label getGym_name(){
        return this.gym_name;
    }

    /** Is called to get gym description label*/
    public Label getGym_description(){
        return this.gym_description;
    }

    /** Is called to get course vbox*/
    public VBox getCourse(){
        return this.course;
    }

    /** Is called to get course vbox*/
    public VBox getReview(){
        return this.review;
    }

    /** Is called to get sport comboBox*/
    public ComboBox<String> getCombo_sport(){
        return this.combo_sport;
    }
}
