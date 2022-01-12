package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.user.User;

public abstract class Controller{

    /** Type of Controller*/
    protected ControllerType type;

    /** Reference to the main application.*/
    protected MainApp mainApp;

    /** User*/
    protected User user = null;

    /** Reference to the menu*/
    protected MenuController menu;

    /** The cache from last gym search*/
    protected String[] search_cache;

    /** Is called by the main application to give a reference back to itself.*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /** Is called to set user.*/
    public void setUser(User user) {
        this.user = user;
    }

    /** Is called to set menu.*/
    public void setMenu(MenuController menu){
        this.menu = menu;
    }

    /** Is called to set search_cache.*/
    public void setSearchCache(String[] search) {
        this.search_cache = search;
    }

    /** Is called to get type.*/
    public ControllerType getType() {
        return this.type;
    }
}
