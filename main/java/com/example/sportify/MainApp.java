package com.example.sportify;

import com.example.sportify.controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * The data as an HashMap of User.
     */
    private final HashMap<String, HashMap<String, String>> userData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        // Set the application.
        this.primaryStage.setTitle("Sportify - Home");
        this.primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/Sportify icon.png"))));

        initRootLayout();

        showHomeOverview();
    }

    /**
     * Constructor
     */
    public MainApp() throws IOException {
        userData = readWriteFile.readFile();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 830, 600);
            primaryStage.setScene(scene);
            //primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showHomeOverview() {
        try {
            // Load home overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Home.fxml"));
            Pane personOverview = loader.load();

            // Set home overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            HomeController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the primary pane.
     */
    public BorderPane getPrimaryPane() {
        return rootLayout;
    }

    /**
     * Returns the data as an HashMap of user.
     *
     * public HashMap<String, HashMap<String, String>> getUserData() {
     *     return userData;
     * }
     */

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     *
     * public File getFilePath() {
     *     Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
     *     String filePath = prefs.get("filePath", null);
     *     if (filePath != null) {
     *         return new File(filePath);
     *     } else {
     *         return null;
     *     }
     * }
     */

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     *
     * public void setFilePath(File file) {
     *    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
     *    if (file != null) {
     *        prefs.put("filePath", file.getPath());
     *
     *        // Update the stage title.
     *        primaryStage.setTitle("Sportify - " + file.getName());
     *    } else {
     *        prefs.remove("filePath");
     *
     *        // Update the stage title.
     *        primaryStage.setTitle("Sportify");
     *    }
     * }
     */
}
