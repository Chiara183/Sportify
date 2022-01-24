package com.example.sportify;

import com.example.sportify.controller.SignUpController;
import com.example.sportify.controller.SignUpGymController;

import javax.swing.*;
import java.util.HashMap;

public class Adapter implements SignUp{

    private SignUpController userController;
    private SignUpGymController gymController;
    
    public Adapter(SignUpController user, SignUpGymController gym){
        this.userController = user;
        this.gymController  = gym;
    }
    public Adapter(SignUpController user) {
        this.userController = user;
        this.gymController = new SignUpGymController(userController.getMainApp());
    }

    
    @Override
    public void userKind(HashMap<String, String> userAccount) {
        if (this.userController.graphicController.isUser()) {
            userAccount.put("ruolo", "user");
            this.userController.submit.signUp(userAccount);
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You're registered!");
            this.userController.login();
        } else {
            userAccount.put("ruolo", "gym");
            this.userController.submit.signUp(userAccount);
            gymController.signUpGymAction();
        }
    }
}

