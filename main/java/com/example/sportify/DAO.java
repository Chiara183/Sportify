package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {

    /*public static void main(String[] args){
       *DAO obj_DAO = new DAO();
       obj_DAO.Check_Data("select * from user");
    }*/

    public ResultSet Check_Data(String query){

        DB_Connection obj_DB_Connection = new DB_Connection();

        Connection connection = obj_DB_Connection.get_connection();

        PreparedStatement ps;
        ResultSet rs = null;
        try{

            //String query = "select * from user";
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

}
