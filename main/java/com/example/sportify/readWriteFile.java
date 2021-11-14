package com.example.sportify;

import javafx.fxml.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class readWriteFile implements Initializable {

    public static HashMap<String, String[]> readFile() throws IOException {
        // File path is passed as parameter
        File file = new File(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login");

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st;
        // Declaring a HashMap variable
        HashMap<String, String[]> hashMap = new HashMap<>();
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null){
            // Remove first and last char from st
            st = st.substring(1, st.length() - 1);
            // split the String by a comma
            String[] parts = st.split("=");
            // Create HashMap key
            String key = parts[0].trim();
            // Create a list string
            String list = parts[1].substring(1, parts[1].length() - 1);
            // split the String by a comma
            String[] listParts = list.split(", ");
            // Add to map
            hashMap.put(key, listParts);
        }
        return (hashMap);
    }

    public static void saveOnFile(HashMap<String, String[]> map) {
        String filePath = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login";
        Path path = Path.of(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login");
        String mapAsString = map.keySet().stream().map(key -> key + "=" + Arrays.toString(map.get(key))).collect(Collectors.joining(", ", "{", "}"));
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.length() == 0) {
                    Files.writeString(path, mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } else {
                    Files.writeString(path, "\n" + mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
            } else if (file.createNewFile()) {
                Files.writeString(path, mapAsString, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                System.out.println("The file cannot be created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
}
