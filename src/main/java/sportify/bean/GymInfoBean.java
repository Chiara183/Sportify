package sportify.bean;

import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

/**
 * Bean class for handling gym information operations.
 */
public class GymInfoBean {

    /**
     * Gets the hour value from the hour slider.
     *
     * @param hour The hour slider to get the value from.
     *
     * @return A string representation of the hour value.
     */
    public String getHour(Slider hour){
        return String.valueOf((int)hour.getValue());
    }

    /**
     * Gets the minute value from the minute slider.
     *
     * @param min The minute slider to get the value from.
     *
     * @return A string representation of the minute value.
     */
    public String getMin(Slider min){
        return String.valueOf((int)min.getValue());
    }

    /**
     * Checks if both the gym name and review area input fields are not empty.
     *
     * @param gymName The name of the gym.
     * @param reviewArea The review area text field.
     *
     * @return True if both inputs are not empty, false otherwise.
     */
    public boolean checkReview(String gymName, TextArea reviewArea){
        return !gymName.equals("") && !reviewArea.getText().equals("");
    }

    /**
     * Checks if both the gym name and review area input fields are not empty.
     *
     * @param gymName The name of the gym.
     * @param reviewArea The review area text field.
     *
     * @return True if both inputs are not empty, false otherwise.
     */
    public boolean checkPhoneReview(String gymName, TextArea reviewArea){
        return !gymName.equals("") && !reviewArea.getText().equals("");
    }
}
