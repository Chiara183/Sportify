package sportify.model.dao;

import sportify.errorlogic.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAuthAuthenticator {
    private final DAO dao;

    public DAOAuthAuthenticator(DAO dao) {
        this.dao = dao;
    }

    public String getUsernameByEmail(String email){
        String username = null;
        String query = "SELECT username " +
                "FROM user " +
                "WHERE email = \"" + email + "\"";
        try {
            ResultSet resultSet = dao.checkData(query);

            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (DAOException | SQLException e){
            Logger logger = Logger.getLogger(DAOAuthAuthenticator.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }

        return username;
    }
}
