package com.example.sportify;

import com.example.sportify.controller.graphic.LoginGraphicController;

public class AdapterLogin implements Login{

    private LoginGraphicController controller;
    public boolean google;

    public AdapterLogin(){}

    public AdapterLogin(LoginGraphicController controller, boolean google){
        this.google = google;
        this.controller = controller;
    }

    @Override
    public void doLogin() {
        if(this.google){
            this.controller.loginWithGoogle();
        }else{
            this.controller.submitActionLogin();
        }

    }
}
