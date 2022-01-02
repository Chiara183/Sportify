package com.example.sportify;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class readWriteFile implements Initializable {

    private final IO file;

    public readWriteFile (){
        this.file = new IO();
    }

    public HashMap<String, HashMap<String, String>> readFile(){
        return (this.file.read());
    }

    public void saveOnFile(HashMap<String, String> map) {
        this.file.write(map);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
