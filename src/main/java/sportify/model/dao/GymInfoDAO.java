package sportify.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sportify.MainApp;
import sportify.errorlogic.DAOException;
import sportify.model.domain.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GymInfoDAO {

    private final Connection conn;
    private final DAO dao;

    /**
     * A constant string used in SQL statements
     */
    private static final String SELECT = "SELECT name ";

    public GymInfoDAO(DAO dao) {
        this.dao = dao;
        this.conn = dao.getConnection();
    }

    /**
     * This method returns the list of sports available
     * at the selected gym.
     *
     * @return an ObservableList of strings representing
     * the sports available at the gym
     */
    public ObservableList<String> getSportList(MainApp mainApp) {
        String query = SELECT + "FROM sport ";
        DAO objDAO = mainApp.getDAO();
        ObservableList<String> sportList = null;
        try {
            List<String> sportNameList = objDAO.checkDataColumn(query, "name");
            sportList = FXCollections.observableArrayList(sportNameList);
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(GymInfoDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        return sportList;
    }

    /**
     * Method to cancel a review in the database.
     *
     * @param writer the username of the reviewer
     * @param gym the name of the gym
     * @param timestamp the timestamp of the review
     *
     * @throws SQLException if a database error occurs
     */
    public void cancelReview(String writer, String gym, String timestamp) throws SQLException {
        String query = "DELETE FROM `review` " +
                "WHERE `review`.`writer` = '" + writer +
                "' AND `review`.`gym` = '" + gym +
                "' AND `review`.`timestamp` = '" + timestamp + "'";
        try {
            dao.updateDB(query);
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(GymInfoDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Method to cancel a course in the database.
     *
     * @param sport the name of the sport
     * @param gym the name of the gym
     * @param time the time of the course
     */
    public void cancelCourse(String sport, String gym, String time){
        String query = "DELETE FROM `course`" +
                " WHERE `course`.`sport` = '" + sport +
                "' AND `course`.`gym` = '" + gym +
                "' AND `course`.`time` = '" + time + "'";
        try {
            dao.updateDB(query);
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(GymInfoDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Method to add a course in the database.
     *
     * @param sport the name of the sport
     * @param gym the name of the gym
     * @param time the time of the course
     */
    public void addCourse(String sport, String gym, String time){
        String query = "INSERT INTO `course` (`sport`, `gym`, `time`) " +
                "VALUES ('" + sport + "', '" + gym + "', '" + time + "');";
        try {
            dao.updateDB(query);
        } catch (DAOException e) {
            Logger logger = Logger.getLogger(GymInfoDAO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * This method shares a review for the selected gym.
     *
     * @param gym the name of the selected gym
     * @param review the review to be shared, represented
     *              as a StringBuilder object
     */
    public void shareReview(String gym, StringBuilder review, User user) {
        String[] reviewList = review.toString().split("'");
        review = new StringBuilder();
        int i = 0;
        while (i != reviewList.length) {
            review.append(reviewList[i]);
            if (i != reviewList.length - 1) {
                review.append("\\'");
            }
            i++;
        }

        String username = user.getUserName();
        if (!review.toString().equals("")) {
            String query = "INSERT INTO `review` " +
                    "(`gym`, `review`, `writer`, " + "`timestamp`) " +
                    "VALUES ('" + gym + "', '" + review + "', '" + username + "', " + "CURRENT_TIMESTAMP);";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(GymInfoDAO.class.getName());
                logger.log(Level.SEVERE, e.getMessage());
            }
        } else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Review is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It's called to get data from DB
     *
     * @param gym the gym
     * @param table the table to catch data
     * @param column the column to catch
     *
     * @return a list of results
     */
    public List<String> checkDataColumnGymInfo(String gym, String table, String column) throws DAOException {
        List<String> data;
        String query = SELECT +
                "FROM " + table +
                " WHERE "+ table + ".gym = \""+ gym +"\"";
        data = dao.checkDataColumn(query, column);
        return data;
    }
}
