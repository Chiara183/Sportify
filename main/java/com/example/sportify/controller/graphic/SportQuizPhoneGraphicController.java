package com.example.sportify.controller.graphic;

import com.example.sportify.NewException;
import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;
import com.example.sportify.controller.SportQuizController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportQuizPhoneGraphicController implements GraphicController {

    private static final String INVALID = "invalid input";

    /** Reference to controller*/
    private SportQuizController controller;

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
        int num;
        try{
            num = Integer.parseInt(ageText);
        }catch(NumberFormatException e){
            ageRange = INVALID;
            controller.takeQuiz(ageRange);
            return;
        }
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
        if(envText.equals("indoor")){
            input = envText;
        }else if(envText.equals("outdoor")){
            input = envText;
        }else{
            input = INVALID;
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
        if(endText.equals("single") || endText.equals("group")){
            input = endText;
        }else{
            input = INVALID;
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
