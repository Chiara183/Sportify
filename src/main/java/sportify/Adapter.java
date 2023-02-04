package sportify;

import sportify.controller.MenuController;
import sportify.controller.SignUpController;
import sportify.controller.SignUpGymController;
import sportify.controller.graphic.SignUpGraphicController;

import javax.swing.*;
import java.util.Map;

/**
 * This class implements the Adapter pattern on the SignUp
 */
public class Adapter implements SignUp{

    private final SignUpController userController;
    private final SignUpGymController gymController;
    private SignUpGraphicController userGraphicController;
    private Submit submit;

    /**
     * This method returns the user controller
     *
     * @return the controller
     */
    public SignUpController getUserController() {
        return userController;
    }

    /**
     * This method return the graphic controller
     *
     * @return the user graphic controller
     */
    public SignUpGraphicController getUserGraphicController() {
        return userGraphicController;
    }

    /**
     * This method return the gym controller
     *
     * @return the gym controller
     */
    public SignUpGymController getGymController() {
        return gymController;
    }

    /**
     * This method return the gym graphic controller
     *
     * @return the gym graphic controller
     */
    public Submit getSubmit() {
        return submit;
    }

    /**
     * This method allows to set submit value
     *
     * @param submit the value to set
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * This method allows to set user
     * graphic controller value
     *
     * @param userGraphicController the value
     *                              to set
     */
    public void setUserGraphicController(SignUpGraphicController userGraphicController) {
        this.userGraphicController = userGraphicController;
    }

    /**
     * This is a constructor of the class
     *
     * @param user the user controller to set
     * @param gym the gym controller to set
     */
    public Adapter(SignUpController user, SignUpGymController gym){
        this.userController = user;
        this.gymController  = gym;
    }

    /**
     * This is a constructor of the class
     *
     * @param user the user controller to set
     */
    public Adapter(SignUpController user) {
        this.userController = user;
        this.gymController = new SignUpGymController(userController.getMainApp());
    }

    /**
     * This is a constructor of the class
     *
     * @param user the user controller to set
     * @param graphicController the gym controller to set
     * @param submit the value of submit to set
     */
    public Adapter(SignUpController user, SignUpGraphicController graphicController, Submit submit) {
        this.userController = user;
        MainApp mainApp = userController.getMainApp();
        this.gymController = new SignUpGymController(mainApp);
        MenuController menu = user.getMenu();
        this.gymController.setMenu(menu);
        setUserGraphicController(graphicController);
        setSubmit(submit);
    }

    /**
     * The method that sign up user
     *
     * @param userAccount The account
     *                   to be registered
     *                    on the application
     */
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


