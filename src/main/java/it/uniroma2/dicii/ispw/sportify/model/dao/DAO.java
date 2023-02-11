package it.uniroma2.dicii.ispw.sportify.model.dao;

import it.uniroma2.dicii.ispw.sportify.errorlogic.DAOException;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Allows you to talk to the DB
 */
public class DAO {

    /**
     * The main connection of the project
     */
    static Connection connection = null;

    /**
     * private constructor.
     */
    private DAO(){}


    /**
     * It's called to set connection
     *
     * @param connection the connection to set
     */
    public static void setConnection(Connection connection) {
        DAO.connection = connection;
    }

    /**
     * It's called to get connection
     *
     * @return the connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * It's called to get data from DB
     *
     * @param column the column to be checked
     * @param query the query to run in DB
     *
     * @return a list of results
     */
    public static List<String> checkDataColumn(String query, String column){
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
    public static List<Map<Integer, String>> checkData(String query) throws DAOException {
        List<Map<Integer, String>> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs;
        try{
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                int i = 1;
                Map<Integer, String> map = new HashMap<>();
                while(i!=rs.getMetaData().getColumnCount()+1) {
                    map.put(i, rs.getString(i));
                    i++;
                }
                list.add(map);
            }
        }
        catch (SQLException e) {
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
        return list;
    }

    /**
     * It's called to update data in DB
     *
     * @param query the query to run in DB
     */
    public static void updateAndGetDB(String query) throws DAOException {
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
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
    public static int updateDB(String query) throws DAOException {
        int rowsAffected;
        try (Statement stmt = connection.createStatement()) {
            rowsAffected = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new DAOException("Error updating the database", ex);
        }
        return rowsAffected;
    }

    public static void close() throws DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
