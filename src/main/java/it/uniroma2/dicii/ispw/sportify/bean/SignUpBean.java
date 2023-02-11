package it.uniroma2.dicii.ispw.sportify.bean;

import javafx.scene.control.TextField;

/**
 * Bean class for handling sign up operations.
 */
public class SignUpBean {

    /**
     * Checks if the input text field is empty.
     *
     * @param str The text field to be checked.
     *
     * @return True if the text field is empty, false otherwise.
     */
    public boolean checkEmpty(TextField str){
        return str.getText().equals("");
    }

    /**
     * Checks if the input text field contains only numbers.
     *
     * @param str The text field to be checked.
     *
     * @return True if the text field contains only numbers, false otherwise.
     */
    public boolean checkForNumbers(TextField str){
        String s = str.getText();
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
