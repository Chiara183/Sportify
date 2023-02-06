package sportify.controller;

import sportify.controller.graphic.EditGraphicController;
import sportify.controller.graphic.GraphicController;
import sportify.model.domain.User;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * The `EditController` class extends the `Controller`
 * class and serves as a base class for all edit controllers.
 * It provides methods for setting and getting the name of the view,
 * setting the user to be edited, and setting the graphic controller.
 *
 * @see Controller
 */
public abstract class EditController extends Controller{

    /**
     * Reference to the `EditGraphicController` class.
     */
    protected EditGraphicController graphicController;

    /**
     * The variable that holds the name of the view.
     */
    protected ControllerType view;

    /**
     * Sets the name of the view.
     *
     * @param view The name of the view.
     */
    public void setView(ControllerType view) {
        this.view = view;
    }

    /**
     * Gets the name of the view.
     *
     * @return The name of the view.
     */
    public ControllerType getView() {
        return this.view;
    }

    /**
     * Overrides the `setUser` method of the `Controller`
     * class to set the user to be edited in the dialog.
     * Also sets the user in the graphic controller.
     *
     * @param user The user to be edited.
     */
    @Override
    public void setUser(User user){
        this.user = user;
        graphicController.setUser(user);
    }

    /**
     * Toggles the visibility of password fields.
     *
     * @param passToggle The `CheckBox` that controls the visibility.
     * @param passText The `Label` for the password field.
     * @param password The `TextField` for the password.
     */
    public void togglevisible(CheckBox passToggle, Label passText, TextField password) {
        if (!passToggle.isSelected()) {
            passText.setText(password.getText());
            passText.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(passText.getText());
        password.setVisible(true);
        passText.setVisible(false);
    }

    /**
     * Toggles the visibility of birthday fields.
     *
     * @param passToggle The `CheckBox` that controls the visibility.
     * @param passText The `Label` for the birthday field.
     * @param password The `Pane` that holds the birthday fields.
     * @param birthDay The `TextField` for the day of the birth.
     * @param birthMonth The `TextField` for the month of the birth.
     * @param birthYear The `TextField` for the year of the birth.
     */
    public void togglevisibleBirthday(CheckBox passToggle, Label passText, Pane password, TextField birthDay, TextField birthMonth, TextField birthYear) {
        if (!passToggle.isSelected()) {
            passText.setText(birthDay.getText() +
                    "/" + birthMonth.getText() +
                    "/" + birthYear.getText());
            passText.setVisible(true);
            password.setVisible(false);
            return;
        }
        String birth = passText.getText();
        String[] birth1 = birth.split("/");
        birthDay.setText(birth1[0]);
        birthMonth.setText(birth1[1]);
        birthYear.setText(birth1[2]);
        password.setVisible(true);
        passText.setVisible(false);
    }

    /**
     * Overrides the `setGraphicController` method of the `Controller`
     * class to set the graphic controller.
     *
     * @param graphicController The `GraphicController` to be set.
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (EditGraphicController) graphicController;
    }
}
