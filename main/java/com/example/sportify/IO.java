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
        //RandomAccessFile out2;
        try {
            File file = new File(this.filenameDataStream);
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
            /* out2 = new RandomAccessFile(this.filenameDataStream, "rw");*/
            // out2.writeUTF(string);
            output.writeObject(map);

            output.flush();
            output.close();
            //out2.close();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public HashMap<String, HashMap<String, String>> read(){
        /*String str = "";*/
        //RandomAccessFile in5;
        HashMap<String, HashMap<String, String>> map = null;
        try {
            /*in5 = new RandomAccessFile(this.filenameDataStream, "r");*/
            /*str = in5.readUTF();*/
            //in5.close();
            File file = new File(this.filenameDataStream);
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            //Reads the first object in
            Object readObject = input.readObject();
            input.close();

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
        }

        return map;
    }
}
