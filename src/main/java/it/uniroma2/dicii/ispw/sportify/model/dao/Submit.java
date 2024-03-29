package it.uniroma2.dicii.ispw.sportify.model.dao;

import it.uniroma2.dicii.ispw.sportify.model.domain.ClassicUser;
import it.uniroma2.dicii.ispw.sportify.model.domain.GymUser;
import it.uniroma2.dicii.ispw.sportify.model.domain.User;
import it.uniroma2.dicii.ispw.sportify.util.DateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class that interfaces
 * with the IO class
 */
public class Submit{

    /**
     * private constructor.
     */
    private Submit(){}

    /**
     * String that define RUOLO
     */
    private static final String RUOLO = "ruolo";

    /**
     * String that define USER
     */
    private static final String USER = "username";

    /**
     * String that define PASS
     */
    private static final String PASS = "password";

    /**
     * The method used to do the Login
     *
     * @param userValue the username that try to access
     * @param passValue the password of the account
     *
     * @return If the procedure was a success
     */
    public static boolean login(String userValue, String passValue) {
        String className = Submit.class.getName();
        String f = "trunk/src/main/resources/users.csv";
        URI uri;
        File file;
        Map<String, Map<String, String>> account = IO.read();
        Map<String, String> map = account.get(userValue);
        boolean resultDB = false;
        boolean resultFile = false;
        boolean result;
        if (!account.isEmpty() &&
                account.containsKey(userValue) &&
                userValue.equals(map.get(USER)) &&
                passValue.equals(map.get(PASS))) {
            resultDB = true;
        }
        uri = Paths.get(f).toUri();
        file = new File(uri);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String[] tempArr;
            line = br.readLine();
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(userValue) &&
                        tempArr[1].equals(passValue)) {
                    resultFile = true;
                }
                line = br.readLine();
            }
            FileManagement.cleanUp(br);
        }
        catch (IOException ioe) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, ioe.getMessage());
        }
        if(resultDB){
            result = true;
        } else {
            result = resultFile;
        }
        return result;
    }

    /**
     * The method used to do the SignUp
     *
     * @param userAccount the account you want to
     *                    create on the application
     */
    public static void signUp(Map<String, String> userAccount) {
        IO.write(userAccount);
        String usr = userAccount.get(USER);
        String pw = userAccount.get(PASS);
        String str = usr + "," + pw;
        FileManagement.writeFile(str);
    }

    /**
     * The 'exists' method
     *
     * @param username The user you want to
     *                 check if it already exists
     *
     * @return Returns true if the user exists or
     * false if the user does not already exist
     */
    public static boolean exist(String username){
        boolean result;
        Map<String, Map<String, String>> account = IO.read();
        result = !account.isEmpty() &&
                account.containsKey(username);
        return result;
    }

    /**
     * The 'existsEmail' method
     *
     * @param email The email you want to
     *                check if it already exists
     *
     * @return Returns true if the email exists or
     * false if the email does not already exist
     */
    public static boolean existEmail(String email){
        boolean result;
        Map<String, Map<String, String>> account = IO.read();
        AtomicBoolean exist = new AtomicBoolean(false);
        account.forEach(
                (key, value) ->
        {
            if(Objects.equals(account.get(key).get("email"), email)){
                exist.set(true);
            }
        }
        );
        result = exist.get();
        return result;
    }

    /**
     * It's called to set user in the app
     *
     * @param username The username of the user
     *                 who wants to log in
     *
     * @return Return logged-in user
     */
    public static User setUser(String username){
        Map<String, Map<String, String>> account = IO.read();
        User user = null;
        if (account.containsKey(username)) {
            user = writeUser((HashMap<String, Map<String, String>>) account,username);
        }
        return user;
    }

    /**
     * writes the user on the view
     *
     * @param account the users retrieved from the DB
     * @param username the user you want to write on the view
     *
     * @return returns the user just written to the view
     */
    private static User writeUser(HashMap<String, Map<String, String>> account, String username){
        User user;
        String s;
        Map<String, String> map = account.get(username);
        s = map.get(RUOLO);
        if (Objects.equals(s, "user")) {
            user = new ClassicUser(map.get(USER), map.get(PASS));
        }
        else {
            user = new GymUser(map.get(USER), map.get(PASS));
        }
        s = map.get("firstName");
        user.setFirstName(s);
        s = map.get("lastName");
        user.setLastName(s);
        s = map.get("email");
        user.setEmail(s);
        s = map.get("birthday");
        if (!Objects.equals(s, "")) {
            String d = map.get("birthday");
            LocalDate date = DateUtil.parse(d);
            user.setBirthday(date);
        }
        s = map.get(RUOLO);
        user.setRole(s);
        if (Objects.equals(s, "gym")) {
            assert user instanceof GymUser;
            s = map.get("gymName");
            ((GymUser) user).setGymName(s);
            s = map.get("address");
            ((GymUser) user).setAddress(s);
            s = map.get("latitude");
            ((GymUser) user).setLatitude(s);
            s = map.get("longitude");
            ((GymUser) user).setLongitude(s);
            s = map.get("phone");
            ((GymUser) user).setPhone(s);
        }
        return user;
    }

    /**
     * Method to generate a random alphanumeric
     * password of a specific length
     *
     * @param passwordLength the length of the
     *                       password you want to create
     *
     * @return the password created
     */
    public static String generateStrongPassword(int passwordLength) {
        String className = Submit.class.getName();
        String msg = "Final Password: {}";

        String charLowercase = "abcdefghijklmnopqrstuvwxyz";
        String charUppercase = charLowercase.toUpperCase();
        String digit = "0123456789";
        String otherPunctuation = "!@#&()–[{}]:;',?/*";
        String otherSymbol = "~$^+=<>";
        String otherSpecial = otherPunctuation + otherSymbol;
        String passwordAllow = charLowercase + charUppercase + digit + otherSpecial;

        StringBuilder result = new StringBuilder(passwordLength);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(charLowercase, 2);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(charUppercase, 2);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(digit, 2);
        result.append(strDigit);

        // at least 2 special characters (punctuation + symbols)
        String strSpecialChar = generateRandomString(otherSpecial, 2);
        result.append(strSpecialChar);

        // remaining, just random
        String strOther = generateRandomString(passwordAllow, passwordLength - 8);
        result.append(strOther);

        String password = result.toString();
        // shuffle again
        Logger logger = Logger.getLogger(className);
        logger.log(Level.INFO, msg, shuffleString(password));
        return password;
    }

    /**
     * Generates random strings.
     *
     * @param input The string from which you want
     *              to extract a random
     * @param size the length of the final string
     *
     * @return Returns a random string
     */
    private static String generateRandomString(String input, int size) {
        String finalResult;
        String error;
        int length;
        char ch;
        if (input == null ||
                input.length() == 0) {
            error = "Invalid input.";
            throw new IllegalArgumentException(error);
        }
        if (size < 1){
            error = "Invalid size.";
            throw new IllegalArgumentException(error);
        }

        StringBuilder result = new StringBuilder(size);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < size; i++) {
            // produce a random order
            length = input.length();
            int index = random.nextInt(length);
            ch = input.charAt(index);
            result.append(ch);
        }
        finalResult = result.toString();
        return finalResult;
    }

    /**
     * Mixes the strings it receives.
     *
     * @param input The string you want to mix
     *
     * @return The mixed string
     */
    private static String shuffleString(String input) {
        String[] sList = input.split("");
        String finalResult;
        List<String> result = Arrays.asList(sList);
        Collections.shuffle(result);
        finalResult = String.join("", result);
        return finalResult;
    }
}