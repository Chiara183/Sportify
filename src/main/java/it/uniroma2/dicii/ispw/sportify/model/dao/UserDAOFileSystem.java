package it.uniroma2.dicii.ispw.sportify.model.dao;


import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        String f = "trunk/src/main/resources/users.csv";
        String tmpF = "trunk/src/main/resources/tempUsers.csv";
        URI uri;
        URI uri1;
        File file;
        File tempFile;
        Path path2 = Paths.get(f);
        uri = path2.toUri();
        Path path = Paths.get(tmpF);
        uri1 = path.toUri();
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

        try {
            Files.delete(path2);
            Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING);
            result = true;
        } catch (IOException e) {
            Logger logger = Logger.getLogger(UserDAOFileSystem.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            result = false;
        }
        return result;
    }
}