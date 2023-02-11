package it.uniroma2.dicii.ispw.sportify;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;
import it.uniroma2.dicii.ispw.sportify.controller.Dialog;
import it.uniroma2.dicii.ispw.sportify.errorlogic.DAOException;
import it.uniroma2.dicii.ispw.sportify.model.dao.DAO;
import it.uniroma2.dicii.ispw.sportify.model.dao.DBConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class that launches the application
 *
 * @see Application
 */
public class MainAppLauncher extends Application {

    /**
     * The method that allows the
     * application to be launched
     *
     * @param primaryStage The first stage
     *                     of the application
     */
    @Override
    public void start(Stage primaryStage) {
        String s = "";
        Connection c = null;
        String parameter;
        String m = " modality";
        String className = MainAppLauncher.class.getName();
        String img;
        String typeM = "mobile";
        String typeD = "desktop";
        int count = 1;
        Parameters p = getParameters();
        List<String> unnamed = p.getUnnamed();
        if (!getParameters().getUnnamed().isEmpty()){
            parameter = unnamed.get(0);
            s = parameter + m;
            Logger logger = Logger.getLogger(className);
            logger.log(Level.INFO, s);
            s = parameter;
        }
        Projection projection;
        if (unnamed.contains("wgs84")) {
            projection = Projection.WGS_84;
        }
        else {
            projection = Projection.WEB_MERCATOR;
        }
        DBConnection db = DBConnection.getSingletonInstance();
        try {
            c = db.getConnection();
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(MainAppLauncher.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        DAO.setConnection(c);
        MainApp.setPrimaryStage(primaryStage);
        MainApp.setProjection(projection);

        // Set the application.
        Image image;
        img = "Images/Sportify icon.png";
        try (InputStream i = getClass().getResourceAsStream(img)){
            image = new Image(Objects.requireNonNull(i));
            Stage stage = MainApp.getPrimaryStage();
            stage.getIcons().add(image);
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.WARNING, e.getMessage());
        }
        CountDownLatch modalitySignal = new CountDownLatch(count);
        if(Objects.equals(s, typeM)){
            MainApp.setMobile(true);
        }
        else if (Objects.equals(s, typeD)){
            MainApp.setMobile(false);
        }
        else {
            new Thread(
                    () -> {
                Dialog dialog = new Dialog();
                dialog.setWait(modalitySignal);
                dialog.createAndShowGUI();
            }
            ).start();
            try {
                modalitySignal.await();
            }
            catch (InterruptedException e) {
                Logger logger = Logger.getLogger(className);
                logger.log(Level.WARNING, e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        MainApp.initRootLayout();
        MainApp.showOverview(ControllerType.HOME);
    }
}
