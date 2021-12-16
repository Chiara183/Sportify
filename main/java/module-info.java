module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires javafx.graphics;
    requires java.desktop;
    requires com.dlsc.gmapsfx;
    requires org.apache.log4j;
    requires json.simple;
    requires org.apache.logging.log4j;

    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;
    exports com.example.sportify.controller;
    opens com.example.sportify.controller to javafx.fxml;
}