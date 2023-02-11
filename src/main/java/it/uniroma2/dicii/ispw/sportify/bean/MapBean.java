package it.uniroma2.dicii.ispw.sportify.bean;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Bean class for handling map related operations.
 */
public class MapBean {

    /**
     * Checks if the input search text field is empty.
     *
     * @param search The search text field to be checked.
     *
     * @return True if the text field is not empty, false otherwise.
     */
    public boolean checkSearch(TextField search){
        return !search.getText().equals("");
    }

    /**
     * Checks if the input km combo box has a selected value.
     *
     * @param km The km combo box to be checked.
     *
     * @return True if the combo box has a selected value, false otherwise.
     */
    public boolean checkKm(ComboBox<String> km){
        return !km.getValue().equals("");
    }

}
