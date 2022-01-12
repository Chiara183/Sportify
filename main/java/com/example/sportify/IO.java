package com.example.sportify;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class IO {

    /** Reference to MainApp.*/
    private MainApp mainApp;

    /** It's called to give a reference to MainApp.*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /** It's called to write user in the DB.*/
    public void write(HashMap<String, String> map) {
        DAO obj_DAO = mainApp.getDAO();
        String username = map.get("username");
        String password = map.get("password");
        String first_name = map.get("firstName");
        String last_name = map.get("lastName");
        String email = map.get("email");
        String birthday = map.get("birthday");
        String ruolo = map.get("ruolo");
        obj_DAO.updateDB(
                "INSERT INTO `user` (`username`, `email`, `password`, `first_name`, `last_name`, `ruolo`, `birthday`) VALUES ('"
                        + username +"', '"
                        + email + "', '"
                        + password + "', '"
                        + first_name + "', '"
                        + last_name + "', '"
                        + ruolo + "', '"
                        + birthday + "')");
        if (Objects.equals(ruolo, "gym")) {
            String gymName = map.get("gymName");
            String address = map.get("address");
            String latitude = map.get("latitude");
            String longitude = map.get("longitude");
            String phone = map.get("phone");
            obj_DAO.updateDB(
                    "INSERT INTO `gym` (`name`, `owner`, `address`, `latitude`, `longitude`, `phone`) VALUES ('"
                            + gymName +"', '"
                            + username + "', '"
                            + address + "', '"
                            + latitude + "', '"
                            + longitude + "', '"
                            + phone + "')");
        }
    }

    /** It's called to read users in the DB.*/
    public HashMap<String, HashMap<String, String>> read(){
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        try {
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data("SELECT * FROM user LEFT JOIN gym ON gym.owner = user.username");
            while (rs.next()) {
                HashMap<String, String> gymAccount = getInfoUser(rs);
                String userValue = rs.getString("username");                    //get user username
                map.put(userValue, gymAccount);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return map;
    }

    /** It's called to get info of given user.*/
    public HashMap<String, String> getInfoUser(ResultSet rs){
        HashMap<String, String> gymAccount = new HashMap<>();
        try {
                String userValue = rs.getString("username");                    //get user username
                String passValue = rs.getString("password");                    //get user password
                String nameValue = rs.getString("first_name");                  //get user first name
                String lastNameValue = rs.getString("last_name");               //get user last name
                String email = rs.getString("email");                           //get user email
                String birthday = "";
                if(rs.getDate("birthday") != null) {
                    birthday = rs.getDate("birthday").toString();               //get user birthday
                }
                String ruolo = rs.getString("ruolo");                           //get user ruolo
                String gym_name = rs.getString("name");                         //get user gym_name
                String address = rs.getString("address");                       //get user gym_address
                String latitude = String.valueOf(rs.getDouble("latitude"));     //get user gym_latitude
                String longitude = String.valueOf(rs.getDouble("longitude"));   //get user gym_longitude
                String phone = rs.getString("phone");                           //get user gym_phone
                gymAccount.put("username", userValue);                                     //put userValue in userAccount
                gymAccount.put("password", passValue);                                     //put user password in userAccount
                gymAccount.put("firstName", nameValue);                                    //put user firstName in userAccount
                gymAccount.put("lastName", lastNameValue);                                 //put user lastName in userAccount
                gymAccount.put("email", email);                                            //put user email in userAccount
                gymAccount.put("birthday", birthday);                                      //put user birthday in userAccount
                gymAccount.put("gymName", gym_name);                                       //put user gym_name in userAccount
                gymAccount.put("address", address);                                        //put user address in userAccount
                gymAccount.put("latitude", latitude);                                      //put user latitude in userAccount
                gymAccount.put("longitude", longitude);                                    //put user longitude in userAccount
                gymAccount.put("phone", phone);                                            //put user phone in userAccount
                gymAccount.put("ruolo", ruolo);                                            //put user ruolo in userAccount
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return gymAccount;
    }
}
