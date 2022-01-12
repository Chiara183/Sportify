package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    /** The main connection of the project*/
    Connection connection = null;

    /** It's called to set connection*/
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /** It's called to get data from DB*/
    public ResultSet Check_Data(String query){
        PreparedStatement ps;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return rs;
    }

    /** It's called to update data in DB*/
    public void updateDB(String query){
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
