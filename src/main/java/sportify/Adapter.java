package sportify;

import sportify.controller.MenuController;
import sportify.controller.SignUpController;
import sportify.controller.SignUpGymController;
import sportify.controller.graphic.SignUpGraphicController;

import javax.swing.*;
import java.util.Map;

public class Adapter implements SignUp{

    private final SignUpController userController;
    private final SignUpGymController gymController;
    private SignUpGraphicController userGraphicController;
    private Submit submit;

    public SignUpController getUserController() {
        return userController;
    }

    public SignUpGraphicController getUserGraphicController() {
        return userGraphicController;
    }

    public SignUpGymController getGymController() {
        return gymController;
    }

    public Submit getSubmit() {
        return submit;
    }

    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    public void setUserGraphicController(SignUpGraphicController userGraphicController) {
        this.userGraphicController = userGraphicController;
    }

    public Adapter(SignUpController user, SignUpGymController gym){
        this.userController = user;
        this.gymController  = gym;
    }
    public Adapter(SignUpController user) {
        this.userController = user;
        this.gymController = new SignUpGymController(userController.getMainApp());
    }
    public Adapter(SignUpController user, SignUpGraphicController graphicController, Submit submit) {
        this.userController = user;
        MainApp mainApp = userController.getMainApp();
        this.gymController = new SignUpGymController(mainApp);
        MenuController menu = user.getMenu();
        this.gymController.setMenu(menu);
        setUserGraphicController(graphicController);
        setSubmit(submit);
    }

    
    @Override
    public void userKind(Map<String, String> userAccount) {
        if (getUserGraphicController().isUser()) {
            userAccount.put("ruolo", "user");
            getSubmit().signUp(userAccount);
            if(getUserController().getMainApp().isNotMobile()) {
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
            }
            getUserController().setSubmit(getSubmit());
            getUserController().login();
        } else {
            userAccount.put("ruolo", "gym");
            getSubmit().signUp(userAccount);
            getGymController().signUpGymAction();
        }
    }
}


