package sportify;

import java.io.*;
import java.nio.file.Paths;

public class FileManagement {

    public FileManagement(){}

    public static void writeFile(String stringa){
        FileWriter fstream;
        try {
            fstream = new FileWriter(new File(Paths.get("./src/main/resources/users.csv").toUri()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter out = new BufferedWriter(fstream);

        try {
            out.write(stringa);
            out.newLine();
            //close buffer writer
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanUp(BufferedReader br){
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
