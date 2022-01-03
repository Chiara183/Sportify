package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {

    public ResultSet Check_Data(String query){

        DB_Connection obj_DB_Connection = new DB_Connection();

        Connection connection = obj_DB_Connection.get_connection();

        PreparedStatement ps;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

}
