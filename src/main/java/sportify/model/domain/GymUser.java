package sportify.model.domain;

import javafx.beans.property.SimpleStringProperty;
import sportify.model.dao.DAO;
import sportify.errorlogic.DAOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class that represents
 * the gym user
 *
 * @see User
 */
public class GymUser extends User {

    private static final String WHERE = "' WHERE `gym`.`owner` = '";

    /**
     * Constructs a new GymUser instance.
     */
    public GymUser() {
        this(null, null);
    }

    /**
     * Constructs a new GymUser instance
     * with the given username and password.
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
        String query = "UPDATE `gym`" +
                " SET `name` = '"
                + name + WHERE
                + getUserName() +"'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(GymUser.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
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
                + getUserName() +"'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(GymUser.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
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
                + getUserName() +"'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(GymUser.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * The method that set up gym longitude
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO objDAO = mainApp.getDAO();
        String query = "UPDATE `gym` " +
                "SET `longitude` = '"
                + longitude + WHERE
                + getUserName() + "'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(GymUser.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
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
                + getUserName() +"'";
        try {
            objDAO.updateAndGetDB(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(GymUser.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
