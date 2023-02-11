package it.uniroma2.dicii.ispw.sportify.model.dao;

import it.uniroma2.dicii.ispw.sportify.errorlogic.DAOException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAuthAuthenticator {

    private DAOAuthAuthenticator(){}

    public static String getUsernameByEmail(String email){
        String username = null;
        String query = "SELECT username " +
                "FROM user " +
                "WHERE email = \"" + email + "\"";
        try {
            List<Map<Integer, String>> resultSet = DAO.checkData(query);

            if (!resultSet.isEmpty()) {
                username = resultSet.get(0).get(1);
            }
        } catch (DAOException e){
            Logger logger = Logger.getLogger(DAOAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }

        return username;
    }
}
