package sportify.model.dao;

import sportify.errorlogic.DAOException;
public interface DAOInterface {
    /**
     * Method to update the database with the given query.
     *
     * @param query the query to execute
     * @return
     * @throws DAOException if a DAO error occurs
     */
    int updateDB(String query) throws DAOException;
}
