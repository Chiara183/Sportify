package it.uniroma2.dicii.ispw.sportify.model.dao;

import it.uniroma2.dicii.ispw.sportify.errorlogic.DAOException;
import it.uniroma2.dicii.ispw.sportify.model.domain.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SportDAO class is used
 * to retrieve information about
 * sports from the database.
 */
public class SportDAO {

    private static final List<Sport> SPORTS = new ArrayList<>();

    /**
     * The constructor.
     */
    public SportDAO() {
        String query = "SELECT * " +
                "FROM sport ";
        List<Map<Integer, String>> rs = null;
        try {
            rs = DAO.checkData(query);
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(SportDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert rs != null;
        int i = 0 ;
        while (i != rs.size()) {
            Sport sport;
            sport = new Sport(rs.get(i).get(1),
                    rs.get(i).get(2),
                    rs.get(i).get(3));
            SPORTS.add(sport);
            i++;
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
        for (Sport sport : SPORTS) {
            if (sport.getName().equals(sportName)) {
                return sport.getDescription();
            }
        }
        return null;
    }
}
