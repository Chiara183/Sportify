package sportify.controller;

import sportify.pattern.Adapter;
import sportify.MainApp;
import sportify.model.dao.Submit;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.SignUpGraphicController;
import javafx.scene.control.Alert;

import java.util.Map;

/**
 * The SignUpController class extends the AccessController and is
 * responsible for handling user registration.
 * It contains a reference to the SignUpGraphicController, which
 * handles the graphical representation of the 'sign up' process,
 * and a reference to the Submit object, which is used to submit
 * the user's information.
 *
 * @see AccessController
 */
public class SignUpController extends AccessController {

    /**
     * A reference to the SignUpGraphicController,
     * which is responsible for handling the graphical
     * representation of the 'sign up' process.
     */
    private SignUpGraphicController graphicController;

    /**
     * A reference to the Submit object, which is
     * used to submit the user's information.
     */
    private Submit submitSignUp;

    /**
     * The default constructor for the SignUpController class.
     */
    public SignUpController() {
        this.type = ControllerType.SIGN_UP;
        this.submitSignUp = new Submit(null);
    }

    /**
     * Overrides the setMainApp method in the
     * AccessController class to set the main application.
     *
     * @param mainApp  the main application to be set
     */
    @Override
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        this.submitSignUp = new Submit(mainApp);
    }


    /**
     * A method that is called when the user submits
     * their information during the 'sign up' process.
     *
     * @param userValue  the username entered by the user
     * @param passValue  the password entered by the user
     * @param nameValue  the first name entered by the user
     * @param lastNameValue  the last name entered by the user
     * @param email  the email entered by the user
     * @param date  the date entered by the user
     */
    public void submitActionSignUp(String userValue, String passValue, String nameValue, String lastNameValue, String email, String date) {
        Map<String, String> userAccount =
                mainApp.createAccount(userValue, passValue, nameValue, lastNameValue, email, date);
        if (!email.equals("") &&
                !userValue.equals("") &&
                !passValue.equals("") &&
                !submitSignUp.exist(userValue) &&
                !submitSignUp.existEmail(email)) { //if authentic, navigate user to a new page
              Adapter adapter = new Adapter(this, this.graphicController, this.submitSignUp);
              adapter.userKind(userAccount);
        }
        else {
            if (submitSignUp.exist(userValue)){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("User already exists");
                alert.setHeaderText("The user already exists");
                alert.setContentText("Please enter a different username or login.");
                alert.showAndWait();
            }
            else if (submitSignUp.existEmail(email)){
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("User already exists");
                alert.setHeaderText("The email is already registered");
                alert.setContentText("Please enter a different email or login.");
                alert.showAndWait();
            }
            else {
                //show error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Empty field");
                alert.setHeaderText("Some obligatory value are empty");
                alert.setContentText("Please enter all obligatory value.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Overrides the setGraphicController method in the
     * AccessController class to set the graphic controller.
     *
     * @param graphicController  the graphic controller to be set
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (SignUpGraphicController) graphicController;
    }
}


