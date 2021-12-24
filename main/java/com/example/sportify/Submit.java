package com.example.sportify;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class Submit implements Initializable {

    private final readWriteFile file;

    public Submit(){
        this.file = new readWriteFile();
    }

    public boolean login(String userValue, String passValue) {
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");

        //if authentic, navigate user to a new page
        if (!account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"))){
            return true;
        } else {
            account = this.file.readFile("gym.dat");

            //if authentic, navigate user to a new page
            if (!account.isEmpty() && account.containsKey(userValue) &&
                    userValue.equals(account.get(userValue).get("username")) &&
                    passValue.equals(account.get(userValue).get("password"))){
                return true;
            }
            return false;
        }
    }

    public void signUp(String userValue, HashMap<String, String> userAccount, String... deleteKey) {
        if (userAccount.containsKey("gymName")){
            // Create HashMap
            HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
            if (account == null){
                account = new HashMap<>();
            }
            if(deleteKey.length != 0) {
                account.remove(deleteKey[0]);
            }
            readWriteFile.clearFile("login.dat");
            this.file.saveOnFile(account, "login.dat");
            account = this.file.readFile("gym.dat");
            if (account == null){
                account = new HashMap<>();
            }
            readWriteFile.clearFile("gym.dat");
            account.put(userValue, userAccount);                                //add userAccount
            this.file.saveOnFile(account, "gym.dat");
        } else {
            // Create HashMap
            HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
            if (account == null){
                account = new HashMap<>();
            }
            readWriteFile.clearFile("login.dat");
            account.put(userValue, userAccount);                                //add userAccount
            this.file.saveOnFile(account, "login.dat");
        }
    }

    public boolean exist(String username){
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");

        if (!account.isEmpty() && account.containsKey(username)){
            return true;
        } else {
            account = this.file.readFile("gym.dat");

            if (!account.isEmpty() && account.containsKey(username)){
                return true;
            }
            return false;
        }
    }

    public User setUser(String username){
        HashMap<String, HashMap<String, String>> account = this.file.readFile("login.dat");
        User user = new User();
        if (account.containsKey(username)) {
            user = writeUser(account,username);
        } else {
            account = this.file.readFile("gym.dat");
            user = writeUser(account,username);
        }
        return user;
    }

    private User writeUser(HashMap<String, HashMap<String, String>> account, String username){
        User user = new User();
        user.setUserName(account.get(username).get("username"));
        user.setPassword(account.get(username).get("password"));
        user.setFirstName(account.get(username).get("firstName"));
        user.setLastName(account.get(username).get("lastName"));
        user.setEmail(account.get(username).get("email"));
        if (!Objects.equals(account.get(username).get("birthday"), "")) {
            user.setBirthday(DateUtil.parse(account.get(username).get("birthday")));
        }
        if (account.get(username).containsKey("gymName")){
            user.setGymName(account.get(username).get("gymName"));
            user.setAddress(account.get(username).get("gymAddress"));
        }
        return user;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}