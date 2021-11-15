package com.example.sportify;

import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.ResourceBundle;

public class readWriteFile implements Initializable {

    public static HashMap<String, HashMap<String, String>> readFile() throws IOException {
        // File path is passed as parameter
        File file = new File(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login");
        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));
        // Declaring a string variable
        String st;
        // Declaring a HashMap variable
        HashMap<String, HashMap<String, String>> hashMap = new HashMap<>();

        while ((st = br.readLine()) != null){
            st = st.substring(1, st.length() - 1);
            String[] parts = st.split("=");
            String key = parts[0].trim();
            StringBuilder s = new StringBuilder();

            for( int i = 1; i <= parts.length - 1; i++){
                s.append(parts[i].trim());
                s.append("=");
            }

            String str;
            str = s.toString().substring(1, s.length() - 1);
            String[] subParts = str.split(", ");
            HashMap<String, String> subHashMap = new HashMap<>();
            String[] arr;

            for(int i = 0; i <= subParts.length - 1; i++){
                arr = subParts[i].split("=");
                String subKey = arr[0];
                String element = arr[1].substring(1, arr[1].length() - 1);
                subHashMap.put(subKey, element);
            }
            // Add to map
            hashMap.put(key, subHashMap);
        }
        return (hashMap);
    }

    public static void saveOnFile(HashMap<String, HashMap<String, String>> map) {
        String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login";
        Path path = Path.of(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login");
        StringBuilder mapAsString = new StringBuilder();
        for (String key : map.keySet()){
            HashMap<String, String> subMap = map.get(key);
            mapAsString = new StringBuilder("{" + key + "={");
            for (String subKey : subMap.keySet()){
               mapAsString.append(subKey).append("={").append(subMap.get(subKey)).append("}, ");}
            mapAsString = new StringBuilder(mapAsString.substring(0, mapAsString.length() - 2));
            mapAsString.append("}\n");}
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.length() == 0) {
                    Files.writeString(path, mapAsString.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    Files.writeString(path, "\n" + mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            } else if (file.createNewFile()) {
                Files.writeString(path, mapAsString.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                System.out.println("The file cannot be created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFile(){
        try{
            String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login";
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
