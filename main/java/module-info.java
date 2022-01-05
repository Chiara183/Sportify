module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires json.simple;
    requires com.sothawo.mapjfx;
    requires mysql.connector.java;
    requires javafx.web;
    //requires json;
    requires android.json;
    requires commons.lang3;

    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;
    exports com.example.sportify.controller;
    opens com.example.sportify.controller to javafx.fxml;
    exports com.example.sportify.OAuth;
    opens com.example.sportify.OAuth to javafx.fxml;
}