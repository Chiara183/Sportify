package sportify.model.dao;

import sportify.MainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows SignUpGymController to talk to the DB
 */
public class GymDAO {

    /**
     * The main connection of the project
     */
    private final Connection dbConnection;

    /**
     * Reference to MainApp instance
     */
    private final MainApp mainAppGym;

    /**
     * Reference to Submit instance
     */
    private final Submit submitGym;

    /**
     * The constructor.
     *
     * @param app reference to MainApp
     */
    public GymDAO(MainApp app) {
        dbConnection = app.getDAO().getConnection();
        mainAppGym = app;
        submitGym = new Submit(mainAppGym);
    }

    /**
     * Retrieves the gym account information from the database.
     *
     * @return a Map containing the gym account information
     */
    public Map<String, String> getGymAccount() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> gymAccount = null;
        IO objIO = new IO();
        objIO.setMainApp(this.mainAppGym);
        try {
            ps = dbConnection.prepareStatement("SELECT * " +
                    "FROM user " +
                    "LEFT JOIN gym " +
                    "ON gym.owner = user.username " +
                    "WHERE user.ruolo = \"gym\"");
            rs = ps.executeQuery();
            gymAccount = objIO.getInfoUser(rs);
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(GymDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(GymDAO.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
        return gymAccount;
    }

    /**
     * Submits a gym account to the system.
     *
     * @param gymValue the name of the gym
     * @param address the address of the gym
     * @param coords a Map containing the latitude and longitude coordinates of the gym
     * @param gymAccount a Map to store the gym information
     */
    public void submitGymAccount(String gymValue, String address, Map<String, Double> coords, Map<String, String> gymAccount) {
        gymAccount.put("gymName", gymValue);
        gymAccount.put("address", address);
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));
        submitGym.signUp(gymAccount);
    }
}

