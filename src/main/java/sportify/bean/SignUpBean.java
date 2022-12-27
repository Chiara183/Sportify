package sportify.bean;

import javafx.scene.control.TextField;


public class SignUpBean {

    public boolean checkEmpty(TextField str){
        return str.getText().equals("");
    }

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
