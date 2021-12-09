module com.example.sportify {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires javafx.graphics;
    requires java.desktop;
    requires spring.web;
    requires okhttp3;

    opens com.example.sportify to javafx.fxml;
    exports com.example.sportify;
    exports com.example.sportify.controller;
    opens com.example.sportify.controller to javafx.fxml;
}