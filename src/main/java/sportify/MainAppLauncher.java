package sportify;

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainAppLauncher extends Application {

    private static final Logger LOGGER = Logger.getLogger(MainAppLauncher.class.getName());

    @Override
    public void start(Stage primaryStage) {
        System.out.println(super.getParameters().getUnnamed());
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
        if(Objects.equals(super.getParameters().getUnnamed().get(0), "mobile")){
            mainApp.setMobile(true);
        } else if (Objects.equals(super.getParameters().getUnnamed().get(0), "desktop")){
            mainApp.setMobile(false);
        } else {
            new Thread(() -> {
                Dialog dialog = new Dialog();
                dialog.setMainApp(mainApp);
                dialog.setWait(modalitySignal);
                dialog.createAndShowGUI();
            }).start();
            try {
                modalitySignal.await();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        mainApp.initRootLayout();
        mainApp.showHomeOverview();
    }
}
