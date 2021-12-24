package com.example.sportify;

import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IO {

    private String filenameDataStream;

    public IO (){
        this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\";
    }

    public void write(HashMap<String, HashMap<String, String>> map, String file) {
        ObjectOutputStream output = null;
        try {
            File fileReader = new File(this.filenameDataStream + file);
            output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(fileReader)));
            output.writeObject(map);
            output.flush();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                assert output != null;
                output.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, HashMap<String, String>> read(String file){
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        File fileReader = new File(this.filenameDataStream + file);
        try {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(fileReader)));
            //Reads the first object in
            Object readObject = input.readObject();

            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            map = (HashMap<String, HashMap<String, String>>) readObject;
            input.close();
        } catch (EOFException e) {
            if (fileReader.length() == 0) {
                System.out.println("File is empty ...");
            } else {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\";
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

        return map;
    }
}
