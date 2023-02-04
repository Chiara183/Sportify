package sportify;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManagement {

    private static final Logger LOGGER = Logger.getLogger(FileManagement.class.getName());

    private FileManagement() {}

    public static FileManagement getInstance() {
        return new FileManagement();
    }

    public static void writeFile(String stringa){
        FileWriter fstream = null;
        String f = "./src/main/resources/users.csv";
        Path path = Paths.get(f);
        URI uri = path.toUri();
        File file = new File(uri);
        try {
            fstream = new FileWriter(file, true);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        assert fstream != null;
        try(BufferedWriter out = new BufferedWriter(fstream)) {
            out.write(stringa);
            out.newLine();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void cleanUp(BufferedReader br){
        try {
            br.close();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
