package com.example.sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IO {

    private static final Logger LOGGER = Logger.getLogger(IO.class.getName());
    /** Reference to MainApp.*/
    private MainApp mainApp;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";
    private static final String RUOLO = "ruolo";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";


    /** It's called to give a reference to MainApp.*/
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /** It's called to write user in the DB.*/
    public void write(Map<String, String> map) {
        DAO objDAO = mainApp.getDAO();
        String username = map.get(USERNAME);
        String password = map.get(PASSWORD);
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String email = map.get(EMAIL);
        String birthday = map.get(BIRTHDAY);
        String ruolo = map.get(RUOLO);
        objDAO.updateDB(
                "INSERT INTO `user` (`username`, `email`, `password`, `first_name`, `last_name`, `ruolo`, `birthday`) VALUES ('"
                        + username +"', '"
                        + email + "', '"
                        + password + "', '"
                        + firstName + "', '"
                        + lastName + "', '"
                        + ruolo + "', '"
                        + birthday + "')");
        if (Objects.equals(ruolo, "gym")) {
            String gymName = map.get("gymName");
            String address = map.get(ADDRESS);
            String latitude = map.get(LATITUDE);
            String longitude = map.get(LONGITUDE);
            String phone = map.get(PHONE);
            objDAO.updateDB(
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
    public Map<String, Map<String, String>> read(){
        HashMap<String, Map<String, String>> map = new HashMap<>();
            PreparedStatement ps = null;
            ResultSet rs;
        Connection connection = DBConnection.getSingletonInstance().getConnection();
        try{
            assert connection != null;
            ps = connection.prepareStatement("SELECT * FROM user LEFT JOIN gym ON gym.owner = user.username");
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> gymAccount = getInfoUser(rs);
                String userValue = rs.getString(USERNAME);                    //get user username
                map.put(userValue, gymAccount);
            }
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                LOGGER.info(e.toString());
            }
        }
        return map;
    }

    /** It's called to get info of given user.*/
    public Map<String, String> getInfoUser(ResultSet rs){
        HashMap<String, String> gymAccount = new HashMap<>();
        try {
                String userValue = rs.getString(USERNAME);                    //get user username
                String passValue = rs.getString(PASSWORD);                    //get user password
                String nameValue = rs.getString("first_name");                  //get user first name
                String lastNameValue = rs.getString("last_name");               //get user last name
                String email = rs.getString(EMAIL);                           //get user email
                String birthday = "";
                if(rs.getDate(BIRTHDAY) != null) {
                    birthday = rs.getDate(BIRTHDAY).toString();               //get user birthday
                }
                String ruolo = rs.getString(RUOLO);                           //get user ruolo
                String gymName = rs.getString("name");                         //get user gym_name
                String address = rs.getString(ADDRESS);                       //get user gym_address
                String latitude = String.valueOf(rs.getDouble(LATITUDE));     //get user gym_latitude
                String longitude = String.valueOf(rs.getDouble(LONGITUDE));   //get user gym_longitude
                String phone = rs.getString(PHONE);                           //get user gym_phone
                gymAccount.put(USERNAME, userValue);                                     //put userValue in userAccount
                gymAccount.put(PASSWORD, passValue);                                     //put user password in userAccount
                gymAccount.put("firstName", nameValue);                                    //put user firstName in userAccount
                gymAccount.put("lastName", lastNameValue);                                 //put user lastName in userAccount
                gymAccount.put(EMAIL, email);                                            //put user email in userAccount
                gymAccount.put(BIRTHDAY, birthday);                                      //put user birthday in userAccount
                gymAccount.put("gymName", gymName);                                       //put user gym_name in userAccount
                gymAccount.put(ADDRESS, address);                                        //put user address in userAccount
                gymAccount.put(LATITUDE, latitude);                                      //put user latitude in userAccount
                gymAccount.put(LONGITUDE, longitude);                                    //put user longitude in userAccount
                gymAccount.put(PHONE, phone);                                            //put user phone in userAccount
                gymAccount.put(RUOLO, ruolo);                                            //put user ruolo in userAccount
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, e.getMessage());        }
        return gymAccount;
    }
}
