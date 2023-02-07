package sportify.model.dao;

import sportify.errorlogic.DAOException;
import sportify.model.domain.Sport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SportDAO class is used
 * to retrieve information about
 * sports from the database.
 */
public class SportDAO {

    private static final List<Sport> sports = new ArrayList<>();

    /**
     * The constructor.
     */
    public SportDAO(DAO dao) {
        String query = "SELECT * " +
                "FROM sport ";
        ResultSet rs = null;
        try {
            rs = dao.checkData(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(SportDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert rs != null;
        try {
            while (rs.next()) {
                Sport sport = null;
                try {
                    sport = new Sport(rs.getString("name"),
                            rs.getString("type"),
                            rs.getString("description"));
                } catch (SQLException e) {
                    Logger logger = Logger.getLogger(SportDAO.class.getName());
                    logger.log(Level.SEVERE, e.getMessage());
                }
                sports.add(sport);
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(SportDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            try {
                rs.close();
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(DAO.class.getName());
                logger.info(e.toString());
            }
        }
    }

    /**
     * This method retrieves the description
     * of a sport based on its name.
     *
     * @param sportName the name of the sport
     *
     * @return the description of the sport
     */
    public String getDescriptions(String sportName) {
        for (Sport sport : sports) {
            if (sport.getName().equals(sportName)) {
                return sport.getDescription();
            }
        }
        return null;
    }
}
