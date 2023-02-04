package sportify;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        Connection c;
        Submit submit;
        String parameter;
        String m = " modality";
        String className = MainAppLauncher.class.getName();
        String img;
        String typeM = "mobile";
        String typeD = "desktop";
        int count = 1;
        Parameters p = super.getParameters();
        List<String> unnamed = p.getUnnamed();
        if (!super.getParameters().getUnnamed().isEmpty()){
            parameter = unnamed.get(0);
            s = parameter + m;
            Logger logger = Logger.getLogger(className);
            logger.log(Level.INFO, s);
        }
        Projection projection;
        if (unnamed.contains("wgs84")) {
            projection = Projection.WGS_84;
        }
        else {
            projection = Projection.WEB_MERCATOR;
        }
        MainApp mainApp = new MainApp();
        DBConnection db = DBConnection.getSingletonInstance();
        c = db.getConnection();
        DAO dao = mainApp.getDAO();
        dao.setConnection(c);
        submit = new Submit(mainApp);
        mainApp.setSubmit(submit);
        mainApp.setPrimaryStage(primaryStage);
        mainApp.setProjection(projection);

        // Set the application.
        Image image;
        img = "Images/Sportify icon.png";
        try (InputStream i = getClass().getResourceAsStream(img)){
            image = new Image(Objects.requireNonNull(i));
            Stage stage = mainApp.getPrimaryStage();
            stage.getIcons().add(image);
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.WARNING, e.getMessage());
        }
        CountDownLatch modalitySignal = new CountDownLatch(count);
        if(Objects.equals(s, typeM)){
            mainApp.setMobile(true);
        }
        else if (Objects.equals(s, typeD)){
            mainApp.setMobile(false);
        }
        else {
            new Thread(
                    () -> {
                Dialog dialog = new Dialog();
                dialog.setMainApp(mainApp);
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
        mainApp.initRootLayout();
        mainApp.showHomeOverview();
    }
}
