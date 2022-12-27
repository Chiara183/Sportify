package sportify.controller.graphic;

import sportify.Subject;
import sportify.bean.GymInfoBean;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.GymInfoController;
import sportify.controller.MenuController;
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

    /** Reference to controller*/
    protected GymInfoController controller;

    /** Reference to bean*/
    protected final GymInfoBean bean = new GymInfoBean();

    /**Attribute of concrete subject*/
    protected String subjectState = "Unchanged";

    /** All the label of interface*/
    @FXML
    protected Label gymName;
    @FXML
    protected Label gymDescription;

    // TextArea
    @FXML
    protected TextArea reviewArea;

    /** All the text field of interface*/
    @FXML
    protected TextField hour;
    @FXML
    protected TextField min;

    /** All the border pane of interface*/
    @FXML
    protected BorderPane reviewPane;
    @FXML
    protected BorderPane coursePane;

    /** All the vbox of interface*/
    @FXML
    protected VBox course;
    @FXML
    protected VBox review;

    // ComboBox
    @FXML
    protected ComboBox<String> comboSport;

    /** All the slider of interface*/
    @FXML
    protected Slider hourSlider;
    @FXML
    protected Slider minSlider;

    @FXML
    public void comboActivity(){
        Object selectedItem = comboGymInfo.getSelectionModel().getSelectedItem();
        String choice = selectedItem.toString();
        if(choice.equals("Course")){
            courseAction();
        }else if(choice.equals("Review")){
            reviewAction(gymName.getText());
        }
    }


    /** The action of the button*/
    @FXML
    protected void shareReview(){
        if(!this.bean.checkReview(this.gymName, this.reviewArea)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("A field is empty");
            alert.setContentText("Please fill gym name field and review field");
            alert.showAndWait();
        }
        String gym;
        gym = this.gymName.getText();
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
    protected void notifyAddCourse(){
        String gym;
        gym = this.gymName.getText();
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
        controller.addCourse(sport, gym, time);
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
    protected void courseAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            helpMethod(loader);

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

    public void helpMethod(FXMLLoader loader) throws IOException {
        // Load test result overview.
        loader.setLocation(this.controller.getMainApp().getClass().getResource("SmartphoneView/GymInfoCoursePhone0.fxml"));
        Pane paneTopScreen = controller.setTopMenu();
        Pane pane = loader.load();

        // Set test result overview into the center of root layout.
        this.controller.getMainApp().getPrimaryPane().setCenter(pane);
        controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
        controller.getMenu().setView(ControllerType.COURSE_GYM);
    }

    @FXML
    protected void reviewAction(String gymName) {
        try {
            // Load test result overview.
            FXMLLoader loader = new FXMLLoader();
            helpMethod1(loader);

            // Give the controller access to the main app.
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
        loader.setLocation(this.controller.getMainApp().getClass().getResource("SmartphoneView/GymInfoReviewPhone0.fxml"));
        Pane paneTopScreen = controller.setTopMenu();
        Pane pane = loader.load();

        // Set test result overview into the center of root layout.
        this.controller.getMainApp().getPrimaryPane().setCenter(pane);
        controller.getMainApp().getPrimaryPane().setTop(paneTopScreen);
        controller.getMenu().setView(ControllerType.REVIEW_GYM);
    }

    public ComboBox<String> getComboGymInfo() {
        return this.comboGymInfo;
    }
}
