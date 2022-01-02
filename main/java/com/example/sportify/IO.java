package com.example.sportify;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class IO {

    //private final String filenameDataStream;

    public IO (){
        //this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\";
    }

    public void write(HashMap<String, String> map) {
        //ObjectOutputStream output = null;
        /*File fileReader = new File(this.filenameDataStream + file);
        *output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(fileReader)));
        output.writeObject(map);
        output.flush();*/
        DAO obj_DAO = new DAO();
        String username = map.get("username");
        String password = map.get("password");
        String first_name = map.get("firstName");
        String last_name = map.get("lastName");
        String email = map.get("email");
        String birthday = map.get("birthday");
        String ruolo = map.get("ruolo");
        obj_DAO.Check_Data(
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
            obj_DAO.Check_Data(
                    "INSERT INTO `gym` (`name`, `owner`, `address`, `latitude`, `longitude`, `phone`) VALUES ('"
                            + gymName +"', '"
                            + username + "', '"
                            + address + "', '"
                            + latitude + "', '"
                            + longitude + "', '"
                            + phone + "')");
        }
        /* finally {
            try {
                assert false;
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    }

    public HashMap<String, HashMap<String, String>> read(){
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        //File fileReader = new File(this.filenameDataStream + file);
        try {
            /*ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(fileReader)));
            //Reads the first object in
            *Object readObject = input.readObject();

            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            map = (HashMap<String, HashMap<String, String>>) readObject;
            input.close();*/
            DAO obj_DAO = new DAO();
            ResultSet rs = obj_DAO.Check_Data("SELECT * FROM user LEFT JOIN gym ON gym.owner = user.username");
            while (rs.next()) {
                    HashMap<String, String> gymAccount = new HashMap<>();
                    String userValue = rs.getString("username");                    //get user username
                    String passValue = rs.getString("password");                    //get user password
                    String nameValue = rs.getString("first_name");                  //get user first name
                    String lastNameValue = rs.getString("last_name");               //get user last name
                    String email = rs.getString("email");                           //get user email
                    String birthday = rs.getDate("birthday").toString();            //get user birthday
                    String ruolo = rs.getString("ruolo");                           //get user ruolo
                    String gym_name = rs.getString("name");                         //get user gym_name
                    String address = rs.getString("address");                       //get user gym_address
                    String latitude = String.valueOf(rs.getDouble("latitude"));     //get user gym_latitude
                    String longitude = String.valueOf(rs.getDouble("longitude"));   //get user gym_longitude
                    String phone = String.valueOf(rs.getInt("phone"));            //get user gym_phone
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
                    //System.out.println(userValue + ": " + passValue);
                    map.put(userValue, gymAccount);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return map;
    }
}
