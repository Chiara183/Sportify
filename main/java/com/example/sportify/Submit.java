package com.example.sportify;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Submit implements Initializable {

    public static boolean login(String userValue, String passValue) throws IOException {
        HashMap<String, HashMap<String, String>> account = readWriteFile.readFile();

        //if authentic, navigate user to a new page
        return !account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"));
    }

    public static void signUp(String userValue, HashMap<String, String> userAccount, String... deleteKey) throws Exception {

        // Create HashMap
        HashMap<String, HashMap<String, String>> account = readWriteFile.readFile();
        if(deleteKey.length != 0){
            account.remove(deleteKey[0]);
        }
        readWriteFile.clearFile();
        account.put(userValue, userAccount);                                //add userAccount
        readWriteFile.saveOnFile(account);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}