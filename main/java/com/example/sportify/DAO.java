package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    Connection connection = null;

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

    public void updateDB(String query){
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
