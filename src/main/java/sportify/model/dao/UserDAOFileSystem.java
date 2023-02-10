package sportify.model.dao;


import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to manage updates to user
 * information in the database
 */
public class UserDAOFileSystem {

    /**
     * private constructor.
     */
    private UserDAOFileSystem() {
    }

    /**
     * Updates the information of a user in the database
     *
     * @param userValue the username of the user to update
     * @param passValue the new password for the user to update
     */
    public static boolean updateUser(String userValue, String passValue) {
        String f = "./src/main/resources/users.csv";
        String tmpF = "./src/main/resources/tempUsers.csv";
        URI uri;
        URI uri1;
        File file;
        File tempFile;
        Path path1 = Paths.get(f);
        uri = path1.toUri();
        uri1 = Paths.get(tmpF).toUri();
        file = new File(uri);
        tempFile = new File(uri1);
        boolean result;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file)); BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile))) {
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(userValue)) {
                    tempArr[1] = passValue;
                    line = tempArr[0] + "," + tempArr[1];
                }
                wr.write(line + "\n");

            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(UserDAOFileSystem.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }

        file.delete();
        result = tempFile.renameTo(file);
        return result;
    }
}