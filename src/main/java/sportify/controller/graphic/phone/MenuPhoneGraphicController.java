package sportify.controller.graphic.phone;

import javafx.fxml.FXML;
import sportify.MainApp;
import sportify.controller.ControllerType;
import sportify.controller.GymInfoController;
import sportify.controller.graphic.EditGraphicController;
import sportify.controller.graphic.LoginGraphicController;
import sportify.controller.graphic.MenuGraphicController;
import sportify.controller.graphic.SignUpGymPhoneGraphicController;

public class MenuPhoneGraphicController extends MenuGraphicController {

    @Override
    @FXML
    protected void signOutMethod() {
        controller.setUser(null);
        MainApp.setUser(null);
        homeAction();
    }

    @Override
    @FXML
    public void findGymAction() {
        controller.setView(ControllerType.FIND_GYM);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.FIND_GYM);
    }

    @Override
    @FXML
    public void loadGymInfo() {
        GymInfoPhoneGraphicController graphicController = new GymInfoPhoneGraphicController();
        GymInfoController gym = new GymInfoController();
        graphicController.setController(gym);
        helpMethod1(gym, graphicController);
    }

    @Override
    @FXML
    public void sportQuizAction() {
        controller.setView(ControllerType.SPORT_QUIZ);
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.SPORT_QUIZ);
    }

    @Override
    @FXML
    public void signLoginAction() {
        MainApp.setMenu(controller);
        MainApp.setUser(controller.getUser());
        MainApp.showOverview(ControllerType.LOGIN);
    }

    @Override
    @FXML
    public void back(){
        if(controller.getView()==ControllerType.LOGIN) {
            homeAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ) {
            homeAction();
        } else if(controller.getView()==ControllerType.GYM_INFO) {
            findGymAction();
        } else if(controller.getView()==ControllerType.FIND_GYM) {
            homeAction();
        } else if(controller.getView()==ControllerType.USER_KIND) {
            signAction();
        } else if(controller.getView()==ControllerType.SPORT_INFO) {
            homeAction();
        }else if(controller.getView()==ControllerType.USER_EDIT) {
            helpMethod();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            signLoginAction();
        } else if(controller.getView()==ControllerType.SPORT_QUIZ_ENV) {
            sportQuizAction();
        } else if(controller.getView()==ControllerType.COURSE_GYM || controller.getView()==ControllerType.REVIEW_GYM){
            setGymInfo(controller.getGym());
            GymInfoPhoneGraphicController gymInfoGraphicController = new GymInfoPhoneGraphicController();
            helpMethod4(gymInfoGraphicController);
        }
    }

    @Override
    @FXML
    protected void submit(){
        if(controller.getView()==ControllerType.LOGIN) {
            LoginGraphicController login = (LoginGraphicController) controller.getGraphicInstance();
            login.submitActionLogin();
        } else if(controller.getView()==ControllerType.USER_EDIT) {
            EditGraphicController edit = (EditGraphicController) controller.getGraphicInstance();
            edit.calledOkAction();
        } else if(controller.getView()==ControllerType.SIGN_UP || controller.getView()==ControllerType.SIGN_UP_GYM) {
            SignUpPhoneGraphicController sU = (SignUpPhoneGraphicController) controller.getGraphicInstance();
            sU.submit(controller.getView());
        } else if (controller.getView()==ControllerType.SIGN_UP_GYM2){
            SignUpGymPhoneGraphicController gym = (SignUpGymPhoneGraphicController) controller.getGraphicInstance();
            gym.calledSubmitAction();
        }
    }

    @Override
    public void setSportQuiz() {
        controller.setView(ControllerType.SPORT_QUIZ);
    }

    @Override
    public void setFindGym() {
        controller.setView(ControllerType.FIND_GYM);
    }

    @Override
    public void setLogin() {
        controller.setView(ControllerType.LOGIN);
    }

    @Override
    public void setGymInfo(String gym){
        controller.setView(ControllerType.GYM_INFO);
        controller.setGym(gym);
    }
}
