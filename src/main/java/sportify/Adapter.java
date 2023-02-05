package sportify;

import sportify.controller.MenuController;
import sportify.controller.SignUpController;
import sportify.controller.SignUpGymController;
import sportify.controller.graphic.SignUpGraphicController;

import javax.swing.*;
import java.util.Map;

/**
 * The `Adapter` class implements the `SignUp` interface.
 * It provides the functionality to handle the user sign-up
 * process, depending on the user type selected by the user.
 */
public class Adapter implements SignUp{

    /**
     * A private final instance of the `SignUpController` class.
     */
    private final SignUpController userController;

    /**
     * A private final instance of the `SignUpGymController` class.
     */
    private final SignUpGymController gymController;

    /**
     * A private instance of the `SignUpGraphicController` class.
     */
    private SignUpGraphicController userGraphicController;

    /**
     * A private instance of the `Submit` class.
     */
    private Submit submit;

    /**
     * A getter method that returns the user controller.
     *
     * @return An instance of the `SignUpController` class.
     */
    public SignUpController getUserController() {
        return userController;
    }

    /**
     * A getter method that returns the graphic
     * controller for the user.
     *
     * @return An instance of the
     * `SignUpGraphicController` class.
     */
    public SignUpGraphicController getUserGraphicController() {
        return userGraphicController;
    }

    /**
     * A getter method that returns the gym controller.
     *
     * @return An instance of the `SignUpGymController` class.
     */
    public SignUpGymController getGymController() {
        return gymController;
    }

    /**
     * A getter method that returns the 'submit' instance.
     *
     * @return An instance of the `Submit` class.
     */
    public Submit getSubmit() {
        return submit;
    }

    /**
     * A setter method that sets the 'submit' instance.
     *
     * @param submit An instance of the `Submit` class.
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

    /**
     * A setter method that sets the graphic
     * controller for the user.
     *
     * @param userGraphicController An instance of the
     *                              `SignUpGraphicController`
     *                              class.
     */
    public void setUserGraphicController(SignUpGraphicController userGraphicController) {
        this.userGraphicController = userGraphicController;
    }

    /**
     * A constructor that takes in two parameters:
     * a user controller and a gym controller.
     *
     * @param user An instance of the
     *             `SignUpController` class.
     * @param gym An instance of the
     *            `SignUpGymController` class.
     */
    public Adapter(SignUpController user, SignUpGymController gym){
        this.userController = user;
        this.gymController  = gym;
    }

    /**
     * A constructor that takes in one parameter:
     * a user controller.
     *
     * @param user An instance of the
     *            `SignUpController` class.
     */
    public Adapter(SignUpController user) {
        this.userController = user;
        this.gymController = new SignUpGymController(userController.getMainApp());
    }

    /**
     * A constructor that takes in three parameters:
     * a user controller, a gym controller and a 'submit'
     * instance.
     *
     * @param user An instance of the
     *             `SignUpController` class.
     * @param graphicController An instance of the
     *             `SignUpGymController` class.
     * @param submit An instance of the `Submit` class.
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


