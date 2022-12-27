package sportify.controller.graphic.phone;

import sportify.NewException;
import sportify.bean.SportQuizBean;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.SportQuizController;
import sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportQuizPhoneGraphicController implements GraphicController {

    private static final String INVALID = "invalid input";

    /** Reference to controller*/
    private SportQuizController controller;

    /** Reference to controller*/
    private final SportQuizBean bean = new SportQuizBean();

    /** Reference to quiz*/
    private SportQuizPhoneGraphicController graphicController;

    @FXML
    private TextField age;
    @FXML
    public TextField environment;
    @FXML
    private TextField type;

    @Override
    public void setController(Controller controller) {
        this.controller = (SportQuizController) controller;
    }

    public void setQuiz(SportQuizPhoneGraphicController graphicController) {
        this.graphicController = graphicController;
    }

    @FXML
    public void getAge(){
        String ageRange;
        String ageText = age.getText();
        if(!bean.checkIsNumeric(ageText)){
            ageRange = INVALID;
            controller.takeQuiz(ageRange);
            return;
        }
        int num = Integer.parseInt(ageText);
        if(num >= 0 && num <= 18){
            ageRange = "age1";
        }else if(num >= 19 && num <= 30){
            ageRange = "age2";
        }else if(num >= 31 && num <= 50){
            ageRange = "age3";
        }else if(num >= 51 && num <= 99) {
            ageRange = "age4";
        }else{
            Throwable cause = new Throwable("The cause of the exception is in getAge()");
            String message = "Exception rose in getAge() method";
            NewException ne = new NewException(message,cause);
            try {
                throw ne;
            } catch (NewException e) {
                ageRange = INVALID;
            }
        }
        controller.takeQuiz(ageRange);
    }

    @FXML
    public void getEnvironment() {
        String input;
        String envText = environment.getText();
        if(!bean.checkEnv(envText.toLowerCase())){
            input = INVALID;
        }else{
            input = envText.toLowerCase();
        }
        controller.takeQuiz(input);
    }

    @FXML
    public void back(){
        controller.sportQuizEnv();
    }


    @FXML
    public void end(){
        String input;
        String endText;
        if(graphicController!=null) {
            endText = graphicController.getType().getText();
        } else {
            endText = type.getText();
        }
        if(!bean.checkType(endText.toLowerCase())){
            input = INVALID;
        }else{
            input = endText.toLowerCase();
        }
        controller.takeQuiz(input);
    }

    private TextField getType() {
        return this.type;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
