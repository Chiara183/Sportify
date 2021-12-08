package com.example.sportify;

import java.io.*;

public class IO {

    private String filenameDataStream;

    public IO (){
        this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
    }

    public void write(String string) {
        try {
            DataOutputStream out2 = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(this.filenameDataStream)));

            out2.writeBytes(string);

            out2.close();
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String read(){
        String str = "";
        try {
            DataInputStream in5 = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(this.filenameDataStream)));
            str = new String(in5.readAllBytes());
            System.out.println(str);
        } catch (EOFException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            this.filenameDataStream = System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "login.dat";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }

/*    public static void main(String[] args) {
        IO currentInstance = new IO();
// --------------- This is a first set of examples
        currentInstance.dataStreamExample();
        System.out.println("*****");
        currentInstance.dataStreamExampleWrongTypeInReading();

// --------------- This is the last example
        currentInstance.charsFormInputExample();
    }*/

}
