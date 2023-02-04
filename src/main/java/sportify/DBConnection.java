package sportify;

import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows connection with the DB
 */
public class DBConnection {

    private static final String ERROR = "Error";
    private static final String ERROR_DETECTED = "Error detected!";
    private int count = 0;

    /**
     * The constructor.
     */
    private DBConnection(){}

    private static DBConnection instance = null;

    /**
     * Implementing Singleton pattern
     */
    public static DBConnection getSingletonInstance() {
        if (DBConnection.instance == null)
            DBConnection.instance = new DBConnection();
        return instance;
    }

    /**
     * It's called to create a new connection to the DB.
     *
     * @return null if it gives an error
     */
    public Connection getConnection() {
        Connection connection = null;
        String className = DBConnection.class.getName();
        String error;
        String driver = "com.mysql.cj.jdbc.Driver";
        try{
            Class.forName(driver);

            Properties prop = new Properties();
            String propFileName = "server.properties";
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            }
            else {
                error = "property file '" + propFileName + "' not found in the classpath";
                throw new FileNotFoundException(error);
            }

            // get the property value and print it out
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String url = prop.getProperty("url");
            inputStream.close();
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e){
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText(ERROR_DETECTED);
            error = "No JDBC driver found. Please check class name.";
            alert.setContentText(error);
            alert.showAndWait();
        }
        catch (SQLException e){
            if(this.count == 0){
                this.count++;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText(ERROR_DETECTED);
                error = "Connection to DB failed. I'll retry one more time... ";
                alert.setContentText(error);
                alert.showAndWait();
                instance.getConnection();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText(ERROR_DETECTED);
                error = "Connection to DB failed again. Check if there is an error in url, user or password.";
                alert.setContentText(error);
                alert.showAndWait();
                Logger logger = Logger.getLogger(className);
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.info(e.toString());
        }
        return connection;
    }
}
