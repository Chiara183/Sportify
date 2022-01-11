package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.user.User;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {

    // Reference to the main application.
    protected MainApp mainApp;

    // User
    protected User user = null;

    // MenuController
    protected MenuController menu;

    // String
    protected String[] search_cache;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called to set user.
     *
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Is called to set menu.
     */
    public void setMenu(MenuController menu){
        this.menu = menu;
    }

    /**
     * Is called to set search_cache.
     */
    public void setSearchCache(String[] search) {
        this.search_cache = search;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
