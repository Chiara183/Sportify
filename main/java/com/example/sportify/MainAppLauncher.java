package com.example.sportify;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainAppLauncher extends Application {
    @Override
    public void start(Stage primaryStage) {
        Projection projection = getParameters().getUnnamed().contains("wgs84")
                ? Projection.WGS_84 : Projection.WEB_MERCATOR;
        MainApp mainApp = new MainApp();
        mainApp.getDAO().setConnection(new DB_Connection().get_connection());
        mainApp.setSubmit(new Submit(mainApp));
        mainApp.setPrimaryStage(primaryStage);
        mainApp.setProjection(projection);

        // Set the application.
        mainApp.getPrimaryStage().getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("Images/Sportify icon.png"))));
        mainApp.initRootLayout();
        mainApp.showHomeOverview();
    }
}
