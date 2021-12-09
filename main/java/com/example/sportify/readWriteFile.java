package com.example.sportify;

import javafx.fxml.Initializable;

import java.io.*;
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

    public void saveOnFile(HashMap<String, HashMap<String, String>> map) {
        //StringBuilder mapAsString = new StringBuilder();
        //for (String key : map.keySet()){
        //    HashMap<String, String> subMap = map.get(key);
        //    mapAsString.append("{").append(key).append("={");
        //    for (String subKey : subMap.keySet()){
        //       mapAsString.append(subKey).append("={").append(subMap.get(subKey)).append("}, ");}
        //    mapAsString = new StringBuilder(mapAsString.substring(0, mapAsString.length() - 2));
        //    mapAsString.append("}\n");}*/
        this.file.write(map);
    }

    public static void clearFile(){
        try{
            String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        }catch(Exception exception){
            System.out.println("Exception have been caught");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
