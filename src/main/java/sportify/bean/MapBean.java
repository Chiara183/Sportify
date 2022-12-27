package sportify.bean;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class MapBean {

    public boolean checkSearch(TextField search){
        return !search.getText().equals("");
    }

    public boolean checkKm(ComboBox<String> km){
        return !km.getValue().equals("");
    }

}
