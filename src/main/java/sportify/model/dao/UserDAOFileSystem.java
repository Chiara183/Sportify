package sportify.model.dao;


import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        uri = Paths.get(f).toUri();
        uri1 = Paths.get(tmpF).toUri();
        file = new File(uri);
        tempFile = new File(uri1);
        BufferedReader br = null;
        BufferedWriter wr = null;
        boolean result = false;
        try {
            br = new BufferedReader(new FileReader(file));
            wr = new BufferedWriter(new FileWriter(tempFile));
            String line;
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(userValue)) {
                    tempArr[1] = passValue;
                    String lineNew = tempArr[0] + "," + tempArr[1];
                    line = lineNew;
                }
                wr.write(line + "\n");
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                //
            }
            try {
                if(wr != null)
                    wr.close();
            } catch (IOException e) {

            }
        }
        file.delete();
        tempFile.renameTo(file);
        result = true;
        return result;
    }
}

