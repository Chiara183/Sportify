package sportify;

import sportify.errorlogic.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows you to talk to the DB
 */
public class DAO {

    /**
     * The main connection of the project
     */
    Connection connection = null;


    /**
     * It's called to set connection
     *
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * It's called to get data from DB
     *
     * @param column the column to be checked
     * @param query the query to run in DB
     *
     * @return a list of results
     */
    public List<String> checkData(String query, String column) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs;
        List<String> result = new ArrayList<>();
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getString(column));
            }
            if(!result.isEmpty() && result.get(0) == null){
                result.remove(0);
            }
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
            throw new DAOException("Check error: " + e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(DAO.class.getName());
                logger.info(e.toString());
            }
        }
        return result;
    }

    /**
     * It's called to update data in DB
     *
     * @param query the query to run in DB
     */
    public void updateDB(String query) throws DAOException {
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e){
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.log(Level.WARNING, e.getMessage());
            throw new DAOException("Update error: " + e.getMessage());
        }
        finally{
            try {
                Objects.requireNonNull(ps).close();
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(DAO.class.getName());
                logger.info(e.toString());
            }
        }
    }
}
