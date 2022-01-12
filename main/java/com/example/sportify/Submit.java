package com.example.sportify;

import com.example.sportify.user.User;
import com.example.sportify.user.classicUser;
import com.example.sportify.user.gymUser;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Submit{

    private final IO DB;
    private final MainApp mainApp;

    /** The constructor.*/
    public Submit(MainApp mainApp){
        this.mainApp = mainApp;
        this.DB = new IO();
        this.DB.setMainApp(mainApp);
    }

    /** Login and SignUp method*/
    public boolean login(String userValue, String passValue) {
        HashMap<String, HashMap<String, String>> account = this.DB.read();
        return !account.isEmpty() && account.containsKey(userValue) &&
                userValue.equals(account.get(userValue).get("username")) &&
                passValue.equals(account.get(userValue).get("password"));
    }
    public void signUp(HashMap<String, String> userAccount) {
        this.DB.write(userAccount);
    }

    /** The 'exists' method*/
    public boolean exist(String username){
        HashMap<String, HashMap<String, String>> account = this.DB.read();
        return !account.isEmpty() && account.containsKey(username);
    }
    public boolean existEmail(String email){
        HashMap<String, HashMap<String, String>> account = this.DB.read();
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
        HashMap<String, HashMap<String, String>> account = this.DB.read();
        User user = null;
        if (account.containsKey(username)) {
            user = writeUser(account,username);
        }
        return user;
    }
    private User writeUser(HashMap<String, HashMap<String, String>> account, String username){
        User user;
        if (Objects.equals(account.get(username).get("ruolo"), "user")) {
            user = new classicUser();
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
        user.setRole(account.get(username).get("ruolo"));
        if (Objects.equals(account.get(username).get("ruolo"), "gym")) {
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
    public String generateStrongPassword(int PASSWORD_LENGTH) {

        String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
        String DIGIT = "0123456789";
        String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
        String OTHER_SYMBOL = "~$^+=<>";
        String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
        String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 2);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(DIGIT, 2);
        result.append(strDigit);

        // at least 2 special characters (punctuation + symbols)
        String strSpecialChar = generateRandomString(OTHER_SPECIAL, 2);
        result.append(strSpecialChar);

        // remaining, just random
        String strOther = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 8);
        result.append(strOther);

        String password = result.toString();
        // shuffle again
        System.out.format("%-20s: %s%n", "Final Password", shuffleString(password));

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