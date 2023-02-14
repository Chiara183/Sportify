package it.uniroma2.dicii.ispw.sportify.model.dao;

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
     * private constructor.
     */
    private GymDAO(){}

    /**
     * Retrieves the gym account information from the database.
     *
     * @return a Map containing the gym account information
     */
    public static Map<String, String> getGymAccount() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> gymAccount = null;
        try {
            ps = DAO.getConnection().prepareStatement("SELECT * " +
                    "FROM user " +
                    "LEFT JOIN gym " +
                    "ON gym.owner = user.username " +
                    "WHERE user.ruolo = \"gym\"" +
                    "ORDER BY gym.owner IS NULL DESC, user.username DESC");
            rs = ps.executeQuery();
            rs.next();
            gymAccount = IO.getInfoUser(rs);
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
    public static void submitGymAccount(String gymValue, String address, Map<String, Double> coords, Map<String, String> gymAccount) {
        gymAccount.put("gymName", gymValue);
        gymAccount.put("address", address);
        gymAccount.put("latitude", String.valueOf(coords.get("lat")));
        gymAccount.put("longitude", String.valueOf(coords.get("lon")));
        Submit.signUp(gymAccount);
    }
}

