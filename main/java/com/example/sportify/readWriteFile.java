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

    public void saveOnFile(HashMap<String, String> map) {
        this.file.write(map);
    }

    /*public static void clearFile(String file){
        *FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try{
            String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + file;
            File fileReader = new File(filePath);
            fileWriter = new FileWriter(fileReader, false);
            printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                assert printWriter != null;
                printWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
