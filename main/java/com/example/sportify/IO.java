package com.example.sportify;

import java.io.*;

public class IO {

    private String filenameDataStream;

    public IO (){
        this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
    }

    public void write(String string) {
        RandomAccessFile out2;
        try {
            out2 = new RandomAccessFile(this.filenameDataStream, "rw");
            out2.writeUTF(string);

            out2.close();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String read(){
        String str = "";
        RandomAccessFile in5;
        try {
            in5 = new RandomAccessFile(this.filenameDataStream, "r");
            str = in5.readUTF();
            in5.close();
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }
}
