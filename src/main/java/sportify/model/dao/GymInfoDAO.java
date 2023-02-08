package sportify.model.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sportify.errorlogic.DAOException;
import sportify.model.domain.User;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GymInfoDAO {

    /**
     * private constructor.
     */
    private GymInfoDAO(){}

    /**
     * A constant string used in SQL statements
     */
    private static final String SELECT = "SELECT name ";

    /**
     * This method returns the list of sports available
     * at the selected gym.
     *
     * @return an ObservableList of strings representing
     * the sports available at the gym
     */
    public static ObservableList<String> getSportList() {
        String query = SELECT + "FROM sport ";
        ObservableList<String> sportList;
        List<String> sportNameList = DAO.checkDataColumn(query, "name");
        sportList = FXCollections.observableArrayList(sportNameList);
        return sportList;
    }

    /**
     * Method to cancel a review in the database.
     *
     * @param writer the username of the reviewer
     * @param gym the name of the gym
     * @param timestamp the timestamp of the review
     */
    public static void cancelReview(String writer, String gym, String timestamp){
        String query = "DELETE FROM `review` " +
                "WHERE `review`.`writer` = '" + writer +
                "' AND `review`.`gym` = '" + gym +
                "' AND `review`.`timestamp` = '" + timestamp + "'";
        try {
            DAO.updateDB(query);
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
    public static void cancelCourse(String sport, String gym, String time){
        String query = "DELETE FROM `course`" +
                " WHERE `course`.`sport` = '" + sport +
                "' AND `course`.`gym` = '" + gym +
                "' AND `course`.`time` = '" + time + "'";
        try {
            DAO.updateDB(query);
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
    public static void addCourse(String sport, String gym, String time){
        String query = "INSERT INTO `course` " +
                "VALUES ('" + sport + "', '" + gym + "', '" + time + "');";
        try {
            DAO.updateDB(query);
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
    public static void shareReview(String gym, StringBuilder review, User user) {
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

            try {
                DAO.updateDB(query);
            } catch (DAOException e) {
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
    public static List<String> checkDataColumnGymInfo(String gym, String table, String column){
        List<String> data;
        String query;
        if(Objects.equals(table, "course") || Objects.equals(table, "review")) {
            query = "SELECT * " +
                    "FROM " + table +
                    " WHERE " + table + ".gym = \"" + gym + "\"";
        } else {
            query = "SELECT * " +
                    "FROM " + table +
                    " WHERE " + table + ".name = \"" + gym + "\"";
        }
        data = DAO.checkDataColumn(query, column);
        return data;
    }
}
