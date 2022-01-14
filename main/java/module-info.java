module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires json.simple;
    requires com.sothawo.mapjfx;
    requires javafx.web;
    requires org.apache.commons.lang3;
    requires mysql.connector.java;
    requires org.json;

    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;
    exports com.example.sportify.controller;
    opens com.example.sportify.controller to javafx.fxml;
    exports com.example.sportify.OAuth;
    opens com.example.sportify.OAuth to javafx.fxml;
    exports com.example.sportify.user;
    opens com.example.sportify.user to javafx.fxml;
    exports com.example.sportify.controller.graphic;
    opens com.example.sportify.controller.graphic to javafx.fxml;
}