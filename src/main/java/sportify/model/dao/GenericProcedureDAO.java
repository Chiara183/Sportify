package sportify.model.dao;

import java.sql.SQLException;
import sportify.errorlogic.DAOException;

/**
 * GenericProcedureDAO is a generic interface
 * that defines the method to execute.
 *
 * @param <P> the type of the object that
 *           will be returned from the procedure.
 */
public interface GenericProcedureDAO<P> {

    /**
     * Executes a procedure with the given parameters.
     *
     * @param params the parameters that will
     *               be passed to the procedure.
     *
     * @return an object of type P that is returned
     * from the procedure.
     *
     * @throws DAOException if there is an error in the DAO layer.
     * @throws SQLException if there is an error with the SQL connection.
     */
    P execute(Object... params) throws DAOException, SQLException;

}
