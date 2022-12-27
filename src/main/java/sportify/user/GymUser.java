package sportify.user;

import sportify.DAO;
import javafx.beans.property.SimpleStringProperty;

public class GymUser extends User {

    private static final String where = "' WHERE `gym`.`owner` = '";

    /** The constructor.*/
    public GymUser() {
        this(null, null);
    }
    public GymUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("gym");
        this.gymName = new SimpleStringProperty(null);
        this.address = new SimpleStringProperty(null);
        this.latitude = new SimpleStringProperty(null);
        this.longitude = new SimpleStringProperty(null);
        this.phone = new SimpleStringProperty(null);
    }

    /** The method that set up gym variable*/
    public void setGymName(String name) {
        this.gymName.set(name);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `name` = '"
                        + name + where
                        + this.userName.getValue() +"'");
    }
    public void setAddress(String address) {
        this.address.set(address);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `address` = '"
                        + address + where
                        + this.userName.getValue() +"'");
    }
    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `latitude` = '"
                        + latitude + where
                        + this.userName.getValue() +"'");
    }
    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `longitude` = '"
                        + longitude + where
                        + this.userName.getValue() +"'");
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
        DAO objDAO = mainApp.getDAO();
        objDAO.updateDB(
                "UPDATE `gym` SET `phone` = '"
                        + phone + where
                        + this.userName.getValue() +"'");
    }
}
