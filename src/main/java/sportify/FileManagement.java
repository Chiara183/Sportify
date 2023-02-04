package sportify;

import java.io.*;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagement {

    private static final Logger LOGGER = Logger.getLogger(FileManagement.class.getName());

    public static void writeFile(String stringa){
        FileWriter fstream = null;
        try {
            fstream = new FileWriter(new File(Paths.get("./src/main/resources/users.csv").toUri()), true);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        assert fstream != null;
        try(BufferedWriter out = new BufferedWriter(fstream)) {
            out.write(stringa);
            out.newLine();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void cleanUp(BufferedReader br){
        try {
            br.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
