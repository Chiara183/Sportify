package com.example.sportify;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class MainAppLauncher extends Application {
    @Override
    public void start(Stage primaryStage) {
        Projection projection = getParameters().getUnnamed().contains("wgs84")
                ? Projection.WGS_84 : Projection.WEB_MERCATOR;
        MainApp mainApp = new MainApp();
        mainApp.getDAO().setConnection(DBConnection.getSingletonInstance().getConnection());
        mainApp.setSubmit(new Submit(mainApp));
        mainApp.setPrimaryStage(primaryStage);
        mainApp.setProjection(projection);

        // Set the application.
        mainApp.getPrimaryStage().getIcons().add( new Image( Objects.requireNonNull(
                                getClass().getResourceAsStream("Images/Sportify icon.png"))));
        CountDownLatch modalitySignal = new CountDownLatch(1);
        new Thread(() -> {
            Dialog dialog = new Dialog();
            dialog.setMainApp(mainApp);
            dialog.setWait(modalitySignal);
            dialog.createAndShowGUI();
        }).start();
        try {
            modalitySignal.await();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        mainApp.initRootLayout();
        mainApp.showHomeOverview();
    }
}
