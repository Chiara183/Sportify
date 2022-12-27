package sportify.controller;

import sportify.controller.graphic.*;
import sportify.controller.graphic.phone.SportQuizPhoneGraphicController;
import sportify.user.User;
import javafx.scene.control.Button;

public class MenuController extends Controller{

    /** Reference to graphic gymEditController*/
    private MenuGraphicController graphicController;

    /** The variable that identify the name of the view*/
    private ControllerType view;

    /** Instance of the view.*/
    private LoginGraphicController login;
    private EditGraphicController edit;
    private SignUpGraphicController signUp;
    private SignUpGymGraphicController signUpGym;
    private SportQuizPhoneGraphicController quiz;

    /** String that identify the name of last gym loaded*/
    private String gym;

    /** The constructor.*/
    public MenuController() {
        this.type = ControllerType.MENU;
    }

    public MenuGraphicController getGraphicController() {
        return this.graphicController;
    }

    /** Is called to set the name of the view.*/
    public void setView(ControllerType view) {
        this.view = view;
    }

    /** Method to set the instance of the view.*/
    private void setLogin(LoginGraphicController login) {
        this.login = login;
        this.signUp = null;
        this.signUpGym = null;
        this.edit = null;
        this.quiz = null;
    }
    private void setSignUp(SignUpGraphicController signUp) {
        this.signUp = signUp;
        this.login = null;
        this.signUpGym = null;
        this.edit = null;
        this.quiz = null;
    }
    private void setSignUpGym(SignUpGymGraphicController signUpGym) {
        this.signUpGym = signUpGym;
        this.signUp = null;
        this.login = null;
        this.edit = null;
        this.quiz = null;
    }
    private void setEdit(EditGraphicController edit) {
        this.edit = edit;
        this.signUp = null;
        this.signUpGym = null;
        this.login = null;
        this.quiz = null;
    }
    private void setQuiz(SportQuizPhoneGraphicController quiz) {
        this.quiz = quiz;
        this.signUp = null;
        this.signUpGym = null;
        this.edit = null;
        this.login = null;
    }
    public <T extends GraphicController> void setInstance(T instance){
        if (instance.getGraphicType() == ControllerType.LOGIN){
            setLogin((LoginGraphicController) instance);
        } else if (instance.getGraphicType() == ControllerType.SIGN_UP){
            setSignUp((SignUpGraphicController) instance);
        } else if (instance.getGraphicType() == ControllerType.SIGN_UP_GYM || instance.getGraphicType() == ControllerType.SIGN_UP_GYM2){
            setSignUpGym((SignUpGymGraphicController) instance);
        } else if (instance.getGraphicType() == ControllerType.USER_EDIT){
            setEdit((EditGraphicController) instance);
        } else if (instance.getGraphicType() == ControllerType.SPORT_QUIZ || instance.getGraphicType() == ControllerType.SPORT_QUIZ_TYPE || instance.getGraphicType() ==ControllerType.SPORT_QUIZ_ENV){
            setQuiz((SportQuizPhoneGraphicController) instance);
        }
    }

    /** Is called to get the name of the view.*/
    public ControllerType getView() {
        return this.view;
    }

    /** Method to get the instance of the view.*/
    private LoginGraphicController getLogin() {
        return this.login;
    }
    private SignUpGraphicController getSignUp() {
        return this.signUp;
    }
    private SignUpGymGraphicController getSignUpGym() {
        return this.signUpGym;
    }
    private EditGraphicController getEdit() {
        return this.edit;
    }
    private SportQuizPhoneGraphicController getQuiz() {
        return this.quiz;
    }
    public GraphicController getInstance(){
        if (login!=null){
            return getLogin();
        } else if (signUp!=null){
           return getSignUp();
        } else if (signUpGym!=null){
            return getSignUpGym();
        } else if (edit!=null){
            return getEdit();
        } else if (quiz!= null){
            return getQuiz();
        } else {
            return null;
        }
    }

    /** Is called to get the name of gym.*/
    public String getGym() {
        return this.gym;
    }

    /** Is called to set the name of gym.*/
    public void setGym(String gym) {
        this.gym = gym;
    }

    /** Is called to set user.*/
    @Override
    public void setUser(User user) {
        this.user = user;
        if(mainApp.isNotMobile()) {
            graphicController.setUser(this.user != null);
        }
    }

    /** Is called to set graphic gymEditController*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (MenuGraphicController) graphicController;
    }

    /** Is called to get signOut button.*/
    public Button getSignOut(){
        return this.graphicController.getSignOut();
    }

    /** Is called to load gym info interface*/
    public void loadGymInfo(){
        graphicController.loadGymInfo();
    }

    /** The method called to set the view*/
    public void setSportQuiz() {
        graphicController.setSportQuiz();
    }
    public void setFindGym() {
        graphicController.setFindGym();
    }
    public void setLogin() {
        graphicController.setLogin();
    }
    public void setGymInfo(String gym){
        graphicController.setGymInfo(gym);
    }
    public void setMenuDisable(boolean bool){
        mainApp.getPrimaryPane().getTop().setDisable(bool);
    }

    /** It's called to set the color and disable/able of the buttons*/
    public void setButton(Button button1, Button button2, Button button3, Button button4, Button button5, Button buttonOff){
        button1.setStyle("");
        button1.setDisable(false);
        button2.setStyle("");
        button2.setDisable(false);
        button3.setStyle("");
        button3.setDisable(false);
        button4.setStyle("");
        button4.setDisable(false);
        button5.setStyle("");
        button5.setDisable(false);
        buttonOff.setDisable(true);
        buttonOff.setStyle("-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%),#16704a,#119a60, radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    public void back() {
        graphicController.back();
    }
}

