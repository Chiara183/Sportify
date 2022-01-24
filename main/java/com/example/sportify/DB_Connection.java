package com.example.sportify;

import javafx.scene.control.Alert;

import java.sql.*;

public class DB_Connection {

    /**
     * It's called to create a new connection to the DB.
     * @return null if it gives an error
     */
    public Connection get_connection(){
        int count = 0;
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11462781",
                    "sql11462781", "SK44uxGzTJ");
        }catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Error detected!");
            alert.setContentText("No JDBC driver found. Please check class name.");
            alert.showAndWait();
        }catch (SQLException e){
            if(count == 0){
                count++;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error detected!");
                alert.setContentText("Connection to DB failed. I'll retry one more time... ");
                alert.showAndWait();
                this.get_connection();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error detected!");
                alert.setContentText("Connection to DB failed again. Check if there is an error in url, user or password.");
                alert.showAndWait();
                System.err.println("Error Code = " + e.getErrorCode());
                System.err.println("SQL state = " + e.getSQLState());
                System.err.println("Message = " + e.getMessage());
                System.err.println("Print Stack Trace/n");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
