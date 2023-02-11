package sportify.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sportify.MainApp;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.GymInfoController;
import sportify.controller.MenuController;
import sportify.controller.graphic.phone.SportQuizPhoneGraphicController;

public class MenuGraphicController implements GraphicController{

    /** Reference to controller*/
    protected MenuController controller;

    /* All the button of the menu*/
    @FXML
    protected Button sportQuiz;
    @FXML
    protected Button findGym;
    @FXML
    protected Button signIn;
    @FXML
    protected Button signUp;
    @FXML
    protected Button signOut;
    @FXML
    protected Button gymInfo;

    // Pane
    @FXML
    protected Pane userIcon;

    // Label
    @FXML
    protected Label username;

    /* The action of the buttons*/
    @FXML
    public void back(){
        if(controller.getView()==ControllerType.LOGIN) {
            homeAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ) {
            homeAction();
        } else if(controller.getView()==ControllerType.FIND_GYM) {
            homeAction();
        } else if(controller.getView()==ControllerType.USER_KIND) {
            signAction();
        } else if(controller.getView()==ControllerType.SPORT_INFO) {
            homeAction();
        } else if(controller.getView()==ControllerType.GYM_INFO) {
            findGymAction();
        } else if(controller.getView()==ControllerType.USER_EDIT) {
            helpMethod();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            signLoginAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
            sportQuizAction();
        } else if(controller.getView()==ControllerType.COURSE_GYM || controller.getView()==ControllerType.REVIEW_GYM){
            setGymInfo(controller.getGym());
            GymInfoGraphicController gymInfoGraphicController = new GymInfoGraphicController();
            helpMethod4(gymInfoGraphicController);
        }
    }

    public void helpMethod4(GymInfoGraphicController gymInfoGraphicController){
        GymInfoController gym = new GymInfoController();
        gym.setGraphicController(gymInfoGraphicController);
        gymInfoGraphicController.setController(gym);
        gym.setUser(this.controller.getUser());
        gym.setMenu(this.controller);
        gym.setSearchCache(MainApp.getSearchCache());
        gym.loadingGymName(controller.getGym());
    }

    public void helpMethod(){
        EditGraphicController edit = (EditGraphicController) controller.getGraphicInstance();
        if(edit.controller.getView()==ControllerType.FIND_GYM) {
            findGymAction();
        } else if(edit.controller.getView()==ControllerType.GYM_INFO) {
            setGymInfo(controller.getGym());
        } else if(edit.controller.getView()==ControllerType.SPORT_QUIZ) {
            sportQuizAction();
        } else if(edit.controller.getView()==ControllerType.SPORT_QUIZ_TYPE) {
            sportQuizAction();
        } else if(edit.controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
            sportQuizAction();
        } else {
            homeAction();
        }
    }

    @FXML
    public void signAction(){
        if(controller.getUser()==null){
            signLoginAction();
        } else {
            openUserInterface();
        }
    }
    @FXML
    protected void signOutMethod() {
        controller.setUser(null);
        MainApp.setUser(null);
    }
    @FXML
    public void loadGymInfo(){
        controller.setButton(gymInfo, findGym, sportQuiz, signOut, signIn, signUp);
        GymInfoGraphicController graphicController = new GymInfoGraphicController();
        GymInfoController gym = new GymInfoController();
        graphicController.setController(gym);
        helpMethod1(gym, graphicController);
    }

    public void helpMethod1(GymInfoController gym, GymInfoGraphicController graphicController){
        controller.setView(ControllerType.GYM_INFO);
        gym.setGraphicController(graphicController);
        gym.setUser(controller.getUser());
        gym.setMenu(controller);
        gym.setSearchCache(MainApp.getSearchCache());
        gym.loadingGymName(controller.getUser().getGymName());
        controller.setGym(controller.getUser().getGymName());
    }

    @FXML
    protected void homeAction() {
        controller.setView(ControllerType.HOME);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.HOME);
    }
    @FXML
    public void sportQuizAction() {
        controller.setView(ControllerType.SPORT_QUIZ);
        controller.setButton(sportQuiz, findGym, signOut, signIn, signUp, gymInfo);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.SPORT_QUIZ);
    }
    @FXML
    public void findGymAction() {
        controller.setView(ControllerType.FIND_GYM);
        controller.setButton(findGym, signOut, signIn, signUp, gymInfo, sportQuiz);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.FIND_GYM);
    }
    @FXML
    public void signLoginAction() {
        MainApp.setExternalLogin(true);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.LOGIN);
    }
    @FXML
    protected void signUpAction() {
        controller.setView(ControllerType.SIGN_UP);
        controller.setButton(signUp, signOut, signIn, gymInfo, sportQuiz, findGym);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.SIGN_UP);
    }
    @FXML
    protected void openUserInterface() {
        MainApp.setMenu(controller);
        MainApp.showOverview(ControllerType.USER_EDIT);
    }

    @FXML
    protected void submit(){
        if(controller.getView()==ControllerType.LOGIN) {
            LoginGraphicController login = (LoginGraphicController) controller.getGraphicInstance();
            login.submitActionLogin();
        } else if(controller.getView()==ControllerType.USER_EDIT) {
            EditGraphicController edit = (EditGraphicController) controller.getGraphicInstance();
            edit.okAction();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            SignUpGraphicController sU = (SignUpGraphicController) controller.getGraphicInstance();
            sU.submit(controller.getView());
        } else if (controller.getView()==ControllerType.SIGN_UP_GYM2){
            SignUpGymGraphicController gym = (SignUpGymGraphicController) controller.getGraphicInstance();
            gym.submitActionSignUpGym();
        }
    }

    @FXML
    private void next(){
        if(controller.getView()==ControllerType.SPORT_QUIZ) {
            SportQuizPhoneGraphicController quiz = (SportQuizPhoneGraphicController) controller.getGraphicInstance();
            quiz.getAge();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
            SportQuizPhoneGraphicController quiz = (SportQuizPhoneGraphicController) controller.getGraphicInstance();
            quiz.getEnvironment();
        }
    }

    /* The method called to set the view*/
    public void setSportQuiz() {
        controller.setView(ControllerType.SPORT_QUIZ);
        controller.setButton(sportQuiz, findGym, signOut, signIn, signUp, gymInfo);
    }
    public void setFindGym() {
        controller.setView(ControllerType.FIND_GYM);
        controller.setButton(findGym, signOut, signIn, signUp, gymInfo, sportQuiz);
    }
    public void setLogin() {
        if(!MainApp.isExternalLogin()) {
            controller.setView(ControllerType.LOGIN);
        }
        controller.setButton(signIn, signOut, gymInfo, sportQuiz, findGym, signUp);
    }
    public void setGymInfo(String gym){
        controller.setView(ControllerType.GYM_INFO);
        controller.setGym(gym);
        controller.setButton(gymInfo, findGym, sportQuiz, signOut, signIn, signUp);
    }
    public void setUserKind() {
        controller.setView(ControllerType.USER_KIND);
    }

    /** Is called to get signOutMethod button.*/
    public Button getSignOut(){
        return this.signOut;
    }

    /** Is called to set user view*/
    public void setUser(boolean bool) {
        if (bool) {
            signOut.setVisible(true);
            signOut.setPrefWidth(112);
            signUp.setVisible(false);
            signUp.setPrefWidth(0);
            signIn.setVisible(false);
            signIn.setPrefWidth(0);
            userIcon.setVisible(true);
            username.setText(controller.getUser().getUserName());
            if (controller.getUser().getRole().equals("gym")) {
                gymInfo.setVisible(true);
                gymInfo.setPrefWidth(112);
            }
        } else {
            signOut.setPrefWidth(0);
            signOut.setVisible(false);
            signUp.setPrefWidth(112);
            signUp.setVisible(true);
            signIn.setPrefWidth(112);
            signIn.setVisible(true);
            gymInfo.setVisible(false);
            gymInfo.setPrefWidth(0);
            userIcon.setVisible(false);
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (MenuController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
