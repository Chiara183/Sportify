package com.example.sportify;

import java.sql.*;

public class DB_Connection {

    /**
     * It's called to create a new connection to the DB.
     * @return null if it gives an error
     */
    public Connection get_connection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11462781",
                    "sql11462781", "SK44uxGzTJ");
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
