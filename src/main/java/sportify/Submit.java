package sportify;

import sportify.user.ClassicUser;
import sportify.user.GymUser;
import sportify.user.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Submit{
    private final IO dB;
    private final MainApp mainApp;
    private static final String RUOLO = "ruolo";
    private static final String USER = "username";
    private static final String PASS = "password";

    /** The constructor.*/
    public Submit(MainApp mainApp){
        this.mainApp = mainApp;
        this.dB = new IO();
        this.dB.setMainApp(mainApp);
    }

    /** Login method*/
    public boolean login(String userValue, String passValue) {
        String className = Submit.class.getName();
        Map<String, Map<String, String>> account = this.dB.read();
        boolean resultDB = false;
        boolean resultFile = false;
        boolean result;
        if (!account.isEmpty() && account.containsKey(userValue)) {
            if(userValue.equals(account.get(userValue).get(USER))) {
                if(passValue.equals(account.get(userValue).get(PASS))) {
                    resultDB = true;
                }
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File(Paths.get("./src/main/resources/users.csv").toUri())))) {
            String line;
            String[] tempArr;
            line = br.readLine();
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(userValue)) {
                    if(tempArr[1].equals(passValue)) {
                        resultFile = true;
                    }
                }
                line = br.readLine();
            }
            FileManagement.cleanUp(br);
        } catch (IOException ioe) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, ioe.getMessage());
        }
        result = resultDB && resultFile;
        return result;
    }

    /** SignUp method*/
    public void signUp(Map<String, String> userAccount) {
        this.dB.write(userAccount);
        String usr = userAccount.get(USER);
        String pw = userAccount.get(PASS);
        String str = usr + "," + pw;
        FileManagement.writeFile(str);
    }

    /** The 'exists' method*/
    public boolean exist(String username){
        boolean result;
        Map<String, Map<String, String>> account = this.dB.read();
        result = !account.isEmpty() && account.containsKey(username);
        return result;
    }
    /** The 'existsEmail' method*/
    public boolean existEmail(String email){
        boolean result;
        Map<String, Map<String, String>> account = this.dB.read();
        AtomicBoolean exist = new AtomicBoolean(false);
        account.forEach( (key, value) -> {
            if(Objects.equals(account.get(key).get("email"), email)){
                exist.set(true);
            }
        });
        result = exist.get();
        return result;
    }

    /** It's called to set user in the app*/
    public User setUser(String username){
        Map<String, Map<String, String>> account = this.dB.read();
        User user = null;
        if (account.containsKey(username)) {
            user = writeUser((HashMap<String, Map<String, String>>) account,username);
        }
        return user;
    }
    private User writeUser(HashMap<String, Map<String, String>> account, String username){
        User user;
        if (Objects.equals(account.get(username).get(RUOLO), "user")) {
            user = new ClassicUser();
        } else {
            user = new GymUser();
        }
        user.setMainApp(mainApp);
        user.setUserName(account.get(username).get(USER));
        user.setPassword(account.get(username).get(PASS));
        user.setFirstName(account.get(username).get("firstName"));
        user.setLastName(account.get(username).get("lastName"));
        user.setEmail(account.get(username).get("email"));
        if (!Objects.equals(account.get(username).get("birthday"), "")) {
            String d = account.get(username).get("birthday");
            LocalDate date = DateUtil.parse(d);
            user.setBirthday(date);
        }
        user.setRole(account.get(username).get(RUOLO));
        if (Objects.equals(account.get(username).get(RUOLO), "gym")) {
            assert user instanceof GymUser;
            ((GymUser) user).setGymName(account.get(username).get("gymName"));
            ((GymUser) user).setAddress(account.get(username).get("address"));
            ((GymUser) user).setLatitude(account.get(username).get("latitude"));
            ((GymUser) user).setLongitude(account.get(username).get("longitude"));
            ((GymUser) user).setPhone(account.get(username).get("phone"));
        }
        return user;
    }

    /** Method to generate a random alphanumeric password of a specific length*/
    public String generateStrongPassword(int passwordLength) {
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
    private String generateRandomString(String input, int size) {
        String finalResult;
        String error;
        int length;
        char ch;
        if (input == null || input.length() == 0) {
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
    private String shuffleString(String input) {
        String[] sList = input.split("");
        String finalResult;
        List<String> result = Arrays.asList(sList);
        Collections.shuffle(result);
        finalResult = String.join("", result);
        return finalResult;
    }
}