package it.uniroma2.dicii.ispw.sportify.model.dao;

import it.uniroma2.dicii.ispw.sportify.errorlogic.DAOException;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows connection with the DB
 */
public class DBConnection {

    /**
     * Error message string constant
     */
    private static final String ERROR = "Error";

    /**
     * Error detected message string constant
     */
    private static final String ERROR_DETECTED = "Error detected!";

    /**
     * Counter for number of connections made
     */
    private int count = 0;

    /**
     * Singleton instance of this class
     */
    private static DBConnection instance = null;

    /**
     * Private constructor for Singleton pattern
     */
    private DBConnection(){}

    /**
     * Returns the singleton instance of the class
     *
     * @return The singleton instance of the class
     */
    public static DBConnection getSingletonInstance() {
        if (DBConnection.instance == null)
            DBConnection.instance = new DBConnection();
        return instance;
    }

    /**
     * Creates a new connection to the database.
     *
     * @return The new database connection,
     * or null if an error occurs
     *
     * @throws DAOException If there is a problem with the connection.
     */
    public Connection getConnection() throws DAOException {
        Connection connection = null;
        String className = DBConnection.class.getName();
        String error;
        String driver = "com.mysql.cj.jdbc.Driver";
        try{
            Class.forName(driver);

            Properties prop = new Properties();
            String propFileName = "server.properties";
            ClassLoader classLoader = DBConnection.class.getClassLoader();
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
            if(count == 0){
                count++;
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
            throw new DAOException("Connection error: " + e.getMessage());
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(className);
            logger.info(e.toString());
        }
        return connection;
    }
}
