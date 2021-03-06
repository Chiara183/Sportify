package com.example.sportify;

import com.example.sportify.controller.SignUpController;
import com.example.sportify.controller.SignUpGymController;
import com.example.sportify.controller.graphic.SignUpGraphicController;

import javax.swing.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Adapter implements SignUp{

    private final SignUpController userController;
    private final SignUpGymController gymController;
    private SignUpGraphicController userGraphicController;
    private Submit submit;
    
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
        this.gymController = new SignUpGymController(userController.getMainApp());
        user.getMenu();
        this.gymController.setMenu(user.getMenu());
        this.userGraphicController = graphicController;
        this.submit = submit;
    }

    
    @Override
    public void userKind(Map<String, String> userAccount) {
        if (this.userGraphicController.isUser()) {
            userAccount.put("ruolo", "user");
            this.submit.signUp(userAccount);
            if(userController.getMainApp().isNotMobile()) {
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "You're registered!");
            }
            this.userController.login();
        } else {
            userAccount.put("ruolo", "gym");
            this.submit.signUp(userAccount);
            gymController.signUpGymAction();
        }
    }
}


