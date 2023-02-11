package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.SportQuizController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SportQuizGraphicController implements GraphicController{

    /** Reference to controller*/
    private SportQuizController controller;

    /* All the button of the interface*/
    @FXML
    private Button backQuiz;
    @FXML
    private Button backQuizEnv;
    @FXML
    private Button backQuizType;


    /* The action of the buttons*/
    @FXML
    public void backQuizAction(ActionEvent event) {
        Button b = (Button) event.getSource();
        if(b == backQuiz){
            controller.home();
        }else if(b == backQuizEnv){
            controller.sportQuiz();
        }else if(b == backQuizType){
            controller.sportQuizEnv();
        }
    }
    @FXML
    public void takeQuiz(ActionEvent event) {
        Button b = (Button) event.getSource();
        controller.takeQuiz(b.getId());
    }

    @Override
    public void setController(Controller controller) {
        this.controller = (SportQuizController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
