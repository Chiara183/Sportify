package it.uniroma2.dicii.ispw.sportify.pattern;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.controller.MenuController;
import it.uniroma2.dicii.ispw.sportify.controller.SignUp;
import it.uniroma2.dicii.ispw.sportify.controller.SignUpController;
import it.uniroma2.dicii.ispw.sportify.controller.SignUpGymController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.SignUpGraphicController;
import it.uniroma2.dicii.ispw.sportify.model.dao.Submit;

import javax.swing.*;
import java.util.Map;

/**
 * The `Adapter` class implements the `SignUp` interface.
 * It provides the functionality to handle the user sign-up
 * process, depending on the user type selected by the user.
 *
 * @see SignUp
 */
public class Adapter implements SignUp{

    /**
     * A private final instance of the `SignUpController` class.
     */
    private final SignUpController signUpController;

    /**
     * A private final instance of the `SignUpGymController` class.
     */
    private final SignUpGymController signUpGymController;

    /**
     * A private instance of the `SignUpGraphicController` class.
     */
    private SignUpGraphicController userGraphicController;

    /**
     * A getter method that returns the user controller.
     *
     * @return An instance of the `SignUpController` class.
     */
    public SignUpController getUserController() {
        return signUpController;
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
        return signUpGymController;
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
        this.signUpController = user;
        this.signUpGymController = gym;
    }

    /**
     * A constructor that takes in one parameter:
     * a user controller.
     *
     * @param user An instance of the
     *            `SignUpController` class.
     */
    public Adapter(SignUpController user) {
        this.signUpController = user;
        this.signUpGymController = new SignUpGymController();
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
     */
    public Adapter(SignUpController user, SignUpGraphicController graphicController) {
        this.signUpController = user;
        this.signUpGymController = new SignUpGymController();
        MenuController menu = user.getMenu();
        this.signUpGymController.setMenu(menu);
        setUserGraphicController(graphicController);
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
            Submit.signUp(userAccount);
            if(MainApp.isNotMobile()) {
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
            }
            getUserController().login();
        } else {
            userAccount.put("ruolo", "gym");
            Submit.signUp(userAccount);
            getGymController().signUpGymAction();
        }
    }
}


