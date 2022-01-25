package com.example.sportify;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnection {
    private static final String ERROR = "Error";
    private static final String ERROR_DETECTED = "Error detected!";
    private int count = 0;

    /**
     * It's called to create a new connection to the DB.
     * @return null if it gives an error
     */
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11462781",
                    "sql11462781", "SK44uxGzTJ");
        }catch (ClassNotFoundException e){
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
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
                this.getConnection();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText(ERROR_DETECTED);
                alert.setContentText("Connection to DB failed again. Check if there is an error in url, user or password.");
                alert.showAndWait();
                Logger logger = Logger.getLogger(DBConnection.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        return connection;
    }
}
