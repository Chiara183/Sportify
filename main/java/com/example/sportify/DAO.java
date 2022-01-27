package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    /** The main connection of the project*/
    Connection connection = null;

    /** Implementing Singleton pattern*/
    private static DAO instance = null;

    public static DAO getSingletonInstance() {
        if (DAO.instance == null)
            DAO.instance = new DAO();
        return instance;
    }

    /** It's called to set connection*/
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /** It's called to get data from DB*/
    public ResultSet checkData(String query){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
        }catch (SQLException e) {
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /** It's called to update data in DB*/
    public void updateDB(String query){
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }catch (SQLException e){
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.WARNING, e.getMessage());
        }finally{
            try {
                Objects.requireNonNull(ps).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
