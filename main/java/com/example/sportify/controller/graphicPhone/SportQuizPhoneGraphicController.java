package com.example.sportify.controller.graphicPhone;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.SportQuizController;
import com.example.sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SportQuizPhoneGraphicController implements GraphicController {

    public TextField type;
    /** Reference to controller*/
    private SportQuizController controller;

    @FXML
    private TextField age;
    @FXML
    public TextField environment;

    @Override
    public void setController(Controller controller) {
        this.controller = (SportQuizController) controller;
    }

    @FXML
    public void getAge(){
        String ageRange;
        String ageText = age.getText();
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
            ageRange = "invalid input";
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
            input = "invalid input";
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
        String endText = type.getText();
        if(endText.equals("single") || endText.equals("outdoor")){
            input = endText;
        }else{
            input = "invalid input";
        }
        controller.takeQuiz(input);
    }
}
