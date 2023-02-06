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

    private static final DAO objDAO = new DAO();
    private static final List<Sport> sports = new ArrayList<>();

    static {
        String query = "SELECT * " +
                "FROM sport ";
        List<ResultSet> list = null;
        try {
            list = objDAO.checkData(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(SportDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert list != null;
        for (ResultSet rs : list) {
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
