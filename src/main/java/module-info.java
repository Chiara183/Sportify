module sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires com.sothawo.mapjfx;
    requires javafx.web;
    requires org.apache.commons.lang3;
    requires org.json;
    requires json.simple;
    requires mysql.connector.java;
    requires commons.validator;

    opens sportify to javafx.fxml, javafx.graphics;
    exports sportify;
    exports sportify.controller;
    opens sportify.controller to javafx.fxml, javafx.graphics;
    exports sportify.auth;
    opens sportify.auth to javafx.fxml, javafx.graphics;
    exports sportify.controller.graphic;
    opens sportify.controller.graphic to javafx.fxml, javafx.graphics;
    exports sportify.bean;
    opens sportify.bean to javafx.fxml, javafx.graphics;
    exports sportify.controller.graphic.phone;
    opens sportify.controller.graphic.phone to javafx.fxml, javafx.graphics;
    exports sportify.controller.graphic.desktop;
    opens sportify.controller.graphic.desktop to javafx.fxml, javafx.graphics;
    exports sportify.errorlogic;
    opens sportify.errorlogic to javafx.fxml, javafx.graphics;
    exports sportify.util;
    opens sportify.util to javafx.fxml, javafx.graphics;
    exports sportify.model.dao;
    opens sportify.model.dao to javafx.fxml, javafx.graphics;
    exports sportify.model.domain;
    opens sportify.model.domain to javafx.fxml, javafx.graphics;
    exports sportify.pattern;
    opens sportify.pattern to javafx.fxml, javafx.graphics;
}