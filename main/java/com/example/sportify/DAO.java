package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    public ResultSet Check_Data(String query){
        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = obj_DB_Connection.get_connection();
        PreparedStatement ps;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return rs;
    }

    public void updateDB(String query){
        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = obj_DB_Connection.get_connection();
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

}
