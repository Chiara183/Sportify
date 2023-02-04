package sportify.user;

import sportify.DAO;
import javafx.beans.property.SimpleStringProperty;

/**
 * The class that represents
 * the gym user
 */
public class GymUser extends User {

    private static final String WHERE = "' WHERE `gym`.`owner` = '";

    /**
     * The constructor.
     */
    public GymUser() {
        this(null, null);
    }

    /**
     * The constructor.
     *
     * @param userName the username of the user
     * @param password the password of the user
     */
    public GymUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("gym");
        this.gymName = new SimpleStringProperty(null);
        this.address = new SimpleStringProperty(null);
        this.latitude = new SimpleStringProperty(null);
        this.longitude = new SimpleStringProperty(null);
        this.phone = new SimpleStringProperty(null);
    }

    /**
     * The method that set up gym name
     *
     * @param name the gym name to set
     */
    public void setGymName(String name) {
        this.gymName.set(name);
        DAO objDAO = mainApp.getDAO();
        String query = "UPDATE `gym` SET `name` = '"
                + name + WHERE
                + this.userName.getValue() +"'";
        objDAO.updateDB(query);
    }

    /**
     * The method that set up gym address
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
        DAO objDAO = mainApp.getDAO();
        String query ="UPDATE `gym` SET `address` = '"
                + address + WHERE
                + this.userName.getValue() +"'";
        objDAO.updateDB(query);
    }

    /**
     * The method that set up gym latitude
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
        DAO objDAO = mainApp.getDAO();
        String query = "UPDATE `gym` SET `latitude` = '"
                + latitude + WHERE
                + this.userName.getValue() +"'";
        objDAO.updateDB(query);
    }

    /**
     * The method that set up gym longitude
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `longitude` = '"
                        + longitude + WHERE
                        + this.userName.getValue() +"'");
    }

    /**
     * The method that set up gym phone
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
        DAO objDAO = mainApp.getDAO();
        String query = "UPDATE `gym` SET `phone` = '"
                + phone + WHERE
                + this.userName.getValue() +"'";
        objDAO.updateDB(query);
    }
}
