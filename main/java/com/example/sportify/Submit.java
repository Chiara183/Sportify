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
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        return !account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"));
    }

    public void signUp(HashMap<String, String> userAccount) {
        this.file.saveOnFile(userAccount);
    }

    public boolean exist(String username){
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        return !account.isEmpty() && account.containsKey(username);
    }

    public User setUser(String username){
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        User user = new User();
        if (account.containsKey(username)) {
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
        if (Objects.equals(account.get(username).get("ruolo"), "gym")){
            user.setGymName(account.get(username).get("gymName"));
            user.setAddress(account.get(username).get("gymAddress"));
            user.setLatitude(account.get(username).get("latitude"));
            user.setLongitude(account.get(username).get("longitude"));
            user.setPhone(account.get(username).get("phone"));
        }
        return user;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}