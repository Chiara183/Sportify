package sportify;

import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnection {

    private static final String ERROR = "Error";
    private static final String ERROR_DETECTED = "Error detected!";
    private int count = 0;
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    /** Implementing Singleton pattern*/

    private DBConnection(){}

    private static DBConnection instance = null;

    public static DBConnection getSingletonInstance() {
        if (DBConnection.instance == null)
            DBConnection.instance = new DBConnection();
        return instance;
    }

    /**
     * It's called to create a new connection to the DB.
     * @return null if it gives an error
     */
    public Connection getConnection() {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties prop = new Properties();
            String propFileName = "server.properties";

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            String url = prop.getProperty("url");
            inputStream.close();
            connection = DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText(ERROR_DETECTED);
            alert.setContentText("No JDBC driver found. Please check class name.");
            alert.showAndWait();
        }catch (SQLException e){
            if(this.count == 0){
                this.count++;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText(ERROR_DETECTED);
                alert.setContentText("Connection to DB failed. I'll retry one more time... ");
                alert.showAndWait();
                instance.getConnection();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText(ERROR_DETECTED);
                alert.setContentText("Connection to DB failed again. Check if there is an error in url, user or password.");
                alert.showAndWait();
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        } catch (IOException e) {
            LOGGER.info(e.toString());
        }
        return connection;
    }
}
