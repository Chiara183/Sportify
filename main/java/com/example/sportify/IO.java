package com.example.sportify;

import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IO {

    private String filenameDataStream;

    public IO (){
        this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
    }

    public void write(HashMap<String, HashMap<String, String>> map) {
        ObjectOutputStream output = null;
        try {
            File file = new File(this.filenameDataStream);
            output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
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

    public HashMap<String, HashMap<String, String>> read(){
        HashMap<String, HashMap<String, String>> map = null;
        ObjectInputStream input = null;
        try {
            File file = new File(this.filenameDataStream);
            input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            //Reads the first object in
            Object readObject = input.readObject();

            if(!(readObject instanceof HashMap)) throw new IOException("Data is not a hashmap");
            map = (HashMap<String, HashMap<String, String>>) readObject;
        } catch (EOFException e) {
            File file = new File(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat");
            if (file.length() == 0) {
                System.out.println("File is empty ...");
            } else {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return map;
    }
}
