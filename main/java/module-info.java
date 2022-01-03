module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires json.simple;
    requires org.apache.commons.lang3;
    requires com.sothawo.mapjfx;
    requires spring.context;
    requires spring.security.config;
    requires spring.boot;
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires mysql.connector.java;

    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;
    exports com.example.sportify.controller;
    opens com.example.sportify.controller to javafx.fxml;
}