package sportify.model.dao;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the class that allows you to read
 * and write the database file
 */
public class FileManagement {

    /**
     * The private method that instantiates the class
     */
    private FileManagement() {}

    /**
     * The method that get the instance of the class
     *
     * @return an instance of the class
     */
    public static FileManagement getInstance() {
        return new FileManagement();
    }

    /**
     * the method that permits writing to the
     * database file
     *
     * @param string the value to be written
     *               to the file
     */
    public static void writeFile(String string){
        FileWriter fstream = null;
        String f = "trunk/src/main/resources/users.csv";
        Path path = Paths.get(f);
        URI uri = path.toUri();
        File file = new File(uri);
        try {
            fstream = new FileWriter(file, true);
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(FileManagement.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert fstream != null;
        try(BufferedWriter out = new BufferedWriter(fstream)) {
            out.write(string);
            out.newLine();
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(FileManagement.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * the method that closes the BufferReader passed
     *
     * @param br the BufferReader to be closed
     */
    public static void cleanUp(BufferedReader br){
        try {
            br.close();
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(FileManagement.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
