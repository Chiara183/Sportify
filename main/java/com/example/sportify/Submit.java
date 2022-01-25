package com.example.sportify;

import com.example.sportify.user.ClassicUser;
import com.example.sportify.user.User;
import com.example.sportify.user.gymUser;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Submit{

    private final IO dB;
    private final MainApp mainApp;
    private static final String RUOLO = "ruolo";

    /** The constructor.*/
    public Submit(MainApp mainApp){
        this.mainApp = mainApp;
        this.dB = new IO();
        this.dB.setMainApp(mainApp);
    }

    /** Login and SignUp method*/
    public boolean login(String userValue, String passValue) {
        Map<String, Map<String, String>> account = this.dB.read();
        return !account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"));
    }
    public void signUp(Map<String, String> userAccount) {
        this.dB.write(userAccount);
    }

    /** The 'exists' method*/
    public boolean exist(String username){
        Map<String, Map<String, String>> account = this.dB.read();
        return !account.isEmpty() && account.containsKey(username);
    }
    public boolean existEmail(String email){
        Map<String, Map<String, String>> account = this.dB.read();
        AtomicBoolean exist = new AtomicBoolean(false);
        account.forEach( (key, value) -> {
            if(Objects.equals(account.get(key).get("email"), email)){
                exist.set(true);
            }
        });
        return exist.get();
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
            user = new gymUser();
        }
        user.setMainApp(mainApp);
        user.setUserName(account.get(username).get("username"));
        user.setPassword(account.get(username).get("password"));
        user.setFirstName(account.get(username).get("firstName"));
        user.setLastName(account.get(username).get("lastName"));
        user.setEmail(account.get(username).get("email"));
        if (!Objects.equals(account.get(username).get("birthday"), "")) {
            user.setBirthday(DateUtil.parse(account.get(username).get("birthday")));
        }
        user.setRole(account.get(username).get(RUOLO));
        if (Objects.equals(account.get(username).get(RUOLO), "gym")) {
            assert user instanceof gymUser;
            ((gymUser) user).setGymName(account.get(username).get("gymName"));
            ((gymUser) user).setAddress(account.get(username).get("address"));
            ((gymUser) user).setLatitude(account.get(username).get("latitude"));
            ((gymUser) user).setLongitude(account.get(username).get("longitude"));
            ((gymUser) user).setPhone(account.get(username).get("phone"));
        }
        return user;
    }

    /** Method to generate a random alphanumeric password of a specific length*/
    public String generateStrongPassword(int passwordLength) {

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
        Logger logger = Logger.getLogger(DAO.class.getName());
        logger.log(Level.INFO, "Final Password: [%s][%n]", shuffleString(password));
        return password;
    }
    private String generateRandomString(String input, int size) {

        if (input == null || input.length() <= 0) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if (size < 1){
            throw new IllegalArgumentException("Invalid size.");
        }

        StringBuilder result = new StringBuilder(size);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < size; i++) {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }
    private String shuffleString(String input) {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);
        return String.join("", result);
    }
}