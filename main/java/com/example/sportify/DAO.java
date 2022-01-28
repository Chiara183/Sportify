package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    private static final Logger LOGGER  =   Logger.getLogger(DAO.class.getName());
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
    public String checkData(String query, String column){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                result = rs.getString(column);
            }
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                LOGGER.info(e.toString());            }
        }
        return result;
    }

    /** It's called to update data in DB*/
    public void updateDB(String query){
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, e.getMessage());
        }finally{
            try {
                Objects.requireNonNull(ps).close();
            } catch (SQLException e) {
                LOGGER.info(e.toString());            }
        }
    }
}
