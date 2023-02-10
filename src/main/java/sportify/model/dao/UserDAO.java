package sportify.model.dao;

import sportify.errorlogic.DAOException;
import sportify.model.domain.User;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to manage updates to user
 * information in the database
 */
public class UserDAO {

    /**
     * private constructor.
     */
    private UserDAO(){}

    /**
     * Updates the information of a user in the database
     *
     * @param user the user to update
     */
    public static void updateUser(User user) {
        String query = "UPDATE `user` " +
                "SET `first_name` = '" + user.getFirstName() + "', " +
                "`last_name` = '" + user.getLastName() + "', " +
                "`username` = '" + user.getUserName() + "', " +
                "`password` = '" + user.getPassword() + "', " +
                "`email` = '" + user.getEmail() + "', " +
                "`birthday` = '" + user.getBirthday().toString() +
                "' WHERE `user`.`username` = '" + user.getUserName() + "'";
        try {
            update(query);
        } catch (DAOException e) {
            logError(e.getMessage());
        }
        updateUserInFileSystem(user);
    }

    private static void updateUserInFileSystem(User user) {
        boolean checkFS = UserDAOFileSystem.updateUser(user.getUserName(), user.getPassword());
        if (checkFS) {
            logInfo(String.valueOf(true));
        }
    }

    private static void logError(String message) {
        Logger logger = Logger.getLogger(UserDAO.class.getName());
        logger.log(Level.SEVERE, message);
    }

    private static void logInfo(String message) {
        Logger logger = Logger.getLogger(UserDAO.class.getName());
        logger.log(Level.INFO,message);
    }

    /**
     * Updates the information of a gym user in the database
     *
     * @param user the gym user to update
     */
    public static void updateGymUser(User user){
        String query = "UPDATE `gym` " +
                "SET `name` = '" + user.getGymName() + "', " +
                "`address` = '" + user.getAddress() + "', " +
                "`latitude` = '" + user.getLatitude() + "', " +
                "`longitude` = '" + user.getLongitude() + "', " +
                "`phone` = '" + user.getPhone() + "', " +
                "' WHERE `gym`.`owner` = '" + user.getUserName() + "'";
        try {
            update(query);
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(UserDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Executes a database update query
     *
     * @param query the update query to execute
     *
     * @throws DAOException if the update fails
     * and no rows are affected
     */
    private static void update(String query) throws DAOException{
        try {
            int rowsAffected = DAO.updateDB(query);
            if (rowsAffected == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(UserDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}