package it.uniroma2.dicii.ispw.sportify.controller;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.*;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.phone.SportQuizPhoneGraphicController;
import it.uniroma2.dicii.ispw.sportify.model.domain.User;
import javafx.scene.control.Button;

/**
 * The MenuController class is a child
 * class of the Controller class.
 * It is responsible for managing the
 * menu and its associated views.
 *
 * @see Controller
 */
public class MenuController extends Controller{

    /**
     * The graphicController instance variable
     * is a reference to the MenuGraphicController class.
     */
    private MenuGraphicController graphicController;

    /**
     * The view instance variable
     * is an enumeration of ControllerType.
     */
    private ControllerType view;

    /* Instance of the view.*/
    /**
     * The login instance variable is a reference
     * to the LoginGraphicController class.
     */
    private LoginGraphicController login;

    /**
     * The edit instance variable is a reference
     * to the EditGraphicController class.
     */
    private EditGraphicController edit;

    /**
     * The signUp instance variable is a reference
     * to the SignUpGraphicController class.
     */
    private SignUpGraphicController signUp;

    /**
     * The signUpGym instance variable is a reference
     * to the SignUpGymGraphicController class.
     */
    private SignUpGymGraphicController signUpGym;

    /**
     * The quiz instance variable is a reference
     * to the SportQuizPhoneGraphicController class.
     */
    private SportQuizPhoneGraphicController quiz;

    /**
     * The gym instance variable is a
     * string representing the gym name.
     */
    private String gym;

    /**
     * Singleton instance of this class
     */
    private static MenuController instance = null;

    /**
     * Private constructor for Singleton pattern
     */
    private MenuController() {}

    /**
     * Returns the singleton instance of the class
     *
     * @return The singleton instance of the class
     */
    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    /**
     * The getGraphicController method returns
     * the graphicController instance variable.
     *
     * @return graphicController The
     * MenuGraphicController instance.
     */
    public MenuGraphicController getGraphicController() {
        return this.graphicController;
    }

    /**
     * The setView method sets the value
     * of the view instance variable.
     *
     * @param view An enumeration of ControllerType
     *             representing the current view.
     */
    public void setView(ControllerType view) {
        this.view = view;
    }

    /**
     * The setLogin method sets the value
     * of the login instance variable.
     *
     * @param login The LoginGraphicController instance.
     */
    private void setLogin(LoginGraphicController login) {
        this.login = login;
        this.signUp = null;
        this.signUpGym = null;
        this.edit = null;
        this.quiz = null;
    }

    /**
     * The setSignUp method sets the value
     * of the signUp instance variable.
     *
     * @param signUp The SignUpGraphicController instance.
     */
    private void setSignUp(SignUpGraphicController signUp) {
        this.signUp = signUp;
        this.login = null;
        this.signUpGym = null;
        this.edit = null;
        this.quiz = null;
    }

    /**
     * The setSignUpGym method sets the value
     * of the signUpGym instance variable.
     *
     * @param signUpGym The SignUpGymGraphicController instance.
     */
    private void setSignUpGym(SignUpGymGraphicController signUpGym) {
        this.signUpGym = signUpGym;
        this.signUp = null;
        this.login = null;
        this.edit = null;
        this.quiz = null;
    }

    /**
     * The setEdit method sets the value
     * of the edit instance variable.
     *
     * @param edit The EditGraphicController instance.
     */
    private void setEdit(EditGraphicController edit) {
        this.edit = edit;
        this.signUp = null;
        this.signUpGym = null;
        this.login = null;
        this.quiz = null;
    }

    /**
     * The setQuiz method sets the value of
     * the quiz instance variable.
     *
     * @param quiz The SportQuizPhoneGraphicController instance.
     */
    private void setQuiz(SportQuizPhoneGraphicController quiz) {
        this.quiz = quiz;
        this.signUp = null;
        this.signUpGym = null;
        this.edit = null;
        this.login = null;
    }

    /**
     * Sets an instance of a GraphicController as the current instance.
     *
     * @param instance an instance of a GraphicController.
     * @param <T> the type of the instance, which extends
     *           the GraphicController class.
     */
    public <T extends GraphicController> void setGraphicInstance(T instance){
        if (instance.getGraphicType() == ControllerType.LOGIN){
            setLogin((LoginGraphicController) instance);
        }
        else if (instance.getGraphicType() == ControllerType.SIGN_UP){
            setSignUp((SignUpGraphicController) instance);
        }
        else if (instance.getGraphicType() == ControllerType.SIGN_UP_GYM ||
                instance.getGraphicType() == ControllerType.SIGN_UP_GYM2){
            setSignUpGym((SignUpGymGraphicController) instance);
        }
        else if (instance.getGraphicType() == ControllerType.USER_EDIT){
            setEdit((EditGraphicController) instance);
        }
        else if (instance.getGraphicType() == ControllerType.SPORT_QUIZ ||
                instance.getGraphicType() == ControllerType.SPORT_QUIZ_TYPE ||
                instance.getGraphicType() ==ControllerType.SPORT_QUIZ_ENV){
            setQuiz((SportQuizPhoneGraphicController) instance);
        }
    }

    /**
     * Gets the type of the current view.
     *
     * @return the type of the current view.
     */
    public ControllerType getView() {
        return this.view;
    }

    /**
     * Gets the LoginGraphicController.
     *
     * @return the LoginGraphicController.
     */
    private LoginGraphicController getLogin() {
        return this.login;
    }

    /**
     * Gets the SignUpGraphicController.
     *
     * @return the SignUpGraphicController.
     */
    private SignUpGraphicController getSignUp() {
        return this.signUp;
    }

    /**
     * Gets the SignUpGymGraphicController.
     *
     * @return the SignUpGymGraphicController.
     */
    private SignUpGymGraphicController getSignUpGym() {
        return this.signUpGym;
    }

    /**
     * Gets the EditGraphicController.
     *
     * @return the EditGraphicController.
     */
    private EditGraphicController getEdit() {
        return this.edit;
    }

    /**
     * Gets the SportQuizPhoneGraphicController.
     *
     * @return the SportQuizPhoneGraphicController.
     */
    private SportQuizPhoneGraphicController getQuiz() {
        return this.quiz;
    }

    /**
     * Gets the current instance of a GraphicController.
     *
     * @return the current instance of a GraphicController.
     */
    public GraphicController getGraphicInstance(){
        if (login!=null){
            return getLogin();
        }
        else if (signUp!=null){
           return getSignUp();
        }
        else if (signUpGym!=null){
            return getSignUpGym();
        }
        else if (edit!=null){
            return getEdit();
        }
        else if (quiz!= null){
            return getQuiz();
        }
        else {
            return null;
        }
    }

    /**
     * Gets the name of the gym.
     *
     * @return the name of the gym.
     */
    public String getGym() {
        return this.gym;
    }

    /**
     * Sets the name of the gym.
     *
     * @param gym the name of the gym.
     */
    public void setGym(String gym) {
        this.gym = gym;
    }

    /**
     * Sets the user.
     *
     * @param user the user.
     */
    @Override
    public void setUser(User user) {
        this.user = user;
        if(MainApp.isNotMobile()) {
            graphicController.setUser(this.user != null);
        }
    }

    /**
     * setGraphicController sets the graphic controller for the menu.
     *
     * @param graphicController the graphic controller for the menu.
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (MenuGraphicController) graphicController;
    }

    /**
     * getSignOut returns the 'sign out' button.
     *
     * @return the 'sign out' button.
     */
    public Button getSignOut(){
        return this.graphicController.getSignOut();
    }

    /**
     * loadGymInfo loads the information of the gym.
     */
    public void loadGymInfo(){
        graphicController.loadGymInfo();
    }

    /* The method called to set the view*/
    /**
     * setSportQuiz sets the sport quiz in the menu.
     */
    public void setSportQuiz() {
        graphicController.setSportQuiz();
    }

    /**
     * setFindGym sets the find gym option in the menu.
     */
    public void setFindGym() {
        graphicController.setFindGym();
    }

    /**
     * setLogin sets the login option in the menu.
     */
    public void setLogin() {
        graphicController.setLogin();
    }

    /**
     * setGymInfo sets the information of
     * the gym in the menu.
     *
     * @param gym the information of
     *            the gym to be set.
     */
    public void setGymInfo(String gym){
        graphicController.setGymInfo(gym);
    }

    /**
     * setMenuDisable sets the disable
     * status of the menu.
     *
     * @param bool the disable status of the menu.
     */
    public void setMenuDisable(boolean bool){
        MainApp.getPrimaryPane().getTop().setDisable(bool);
    }

    /**
     * setButton sets the buttons in the menu.
     *
     * @param buttons the button to be set.
     * @param buttonOff the off button to be set.
     */
    public void setButton(Button buttonOff, Button... buttons){
        for(Button button: buttons) {
            button.setStyle("");
            button.setDisable(false);
        }
        buttonOff.setDisable(true);
        buttonOff.setStyle("-fx-background-color: " +
                "linear-gradient(from 0% 93% to 0% 100%, #194432 0%, #16704a 100%)," +
                "#16704a," +
                "#119a60, " +
                "radial-gradient(center 50% 50%, radius 100%, #119a60, #25b97b);");
    }

    /**
     * back performs the back operation in the menu.
     */
    public void back() {
        graphicController.back();
    }
}

