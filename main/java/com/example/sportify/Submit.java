package com.example.sportify;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Submit implements Initializable {

    private final readWriteFile file;

    public Submit(){
        this.file = new readWriteFile();
    }

    public boolean login(String userValue, String passValue) {
        HashMap<String, HashMap<String, String>> account = this.file.readFile();

        //if authentic, navigate user to a new page
        return !account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"));
    }

    public void signUp(String userValue, HashMap<String, String> userAccount, String... deleteKey) {
        // Create HashMap
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        if (account == null){
            account = new HashMap<>();
        }
        if(deleteKey.length != 0){
            account.remove(deleteKey[0]);
        }
        readWriteFile.clearFile();
        account.put(userValue, userAccount);                                //add userAccount
        this.file.saveOnFile(account);
    }

    public User setUser(String username){
        HashMap<String, HashMap<String, String>> account = this.file.readFile();
        User user = new User();
        user.setUserName(account.get(username).get("username"));
        user.setPassword(account.get(username).get("password"));
        user.setFirstName(account.get(username).get("firstName"));
        user.setLastName(account.get(username).get("lastName"));
        user.setEmail(account.get(username).get("email"));
        if (account.get(username).get("birthday") != ""){
            user.setBirthday(DateUtil.parse(account.get(username).get("birthday")));
        }
        return user;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}