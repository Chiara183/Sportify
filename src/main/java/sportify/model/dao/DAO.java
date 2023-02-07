package sportify.model.dao;

import sportify.errorlogic.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Allows you to talk to the DB
 */
public class DAO implements DAOInterface{

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
     * It's called to get connection
     *
     * @return the connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * It's called to get data from DB
     *
     * @param column the column to be checked
     * @param query the query to run in DB
     *
     * @return a list of results
     */
    public List<String> checkDataColumn(String query, String column){
        PreparedStatement ps = null;
        ResultSet rs;
        List<String> result = new ArrayList<>();
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getString(column));
            }
            if(!result.isEmpty() &&
                    result.get(0) == null){
                result.remove(0);
            }
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger(DAO.class.getName());
            logger.info(e.toString());
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
     * It's called to get data from DB
     *
     * @param query the query to run in DB
     *
     * @return a list of results
     */
    public ResultSet checkData(String query) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs;
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException e) {
            throw new DAOException("Check error: " + e.getMessage());
        }
        return rs;
    }

    /**
     * It's called to update data in DB
     *
     * @param query the query to run in DB
     */
    public void updateAndGetDB(String query) throws DAOException {
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);
        }
        catch (SQLException e){
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

    /**
     * Method to update the database with the given query.
     *
     * @param query the query to execute
     *
     * @return number of row modified
     *
     * @throws DAOException if a DAO error occurs
     */
    public int updateDB(String query) throws DAOException {
        int rowsAffected;
        try (Statement stmt = connection.createStatement()) {
            rowsAffected = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new DAOException("Error updating the database", ex);
        }
        return rowsAffected;
    }

    public void close() throws DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
