module sportify {
    requires javafx.controls;
    requires javafx.media;

    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires com.sothawo.mapjfx;
    requires javafx.web;
    requires org.apache.commons.lang3;
    requires org.json;
    requires mysql.connector.java;
    requires json.simple;
    requires commons.validator;

    opens sportify to javafx.fxml, javafx.graphics;
    exports sportify;
    exports sportify.controller;
    opens sportify.controller to javafx.fxml, javafx.graphics;
    exports sportify.auth;
    opens sportify.auth to javafx.fxml, javafx.graphics;
    exports sportify.user;
    opens sportify.user to javafx.fxml, javafx.graphics;
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
}
