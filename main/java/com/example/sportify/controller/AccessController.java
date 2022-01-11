package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.Submit;

abstract class AccessController extends Controller{

    // Reference to submit.
    protected Submit submit;

    /**
     * Is called by the main application to give a reference back to itself.
     */
    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.submit = new Submit(mainApp);
    }

    /**
     * Is called to set submit.
     */
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }
}
