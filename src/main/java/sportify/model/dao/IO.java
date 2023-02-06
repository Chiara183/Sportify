package sportify.model.dao;

import sportify.MainApp;
import sportify.errorlogic.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The IO class handles the input and output
 * operations for the MainApp to the DB.
 */
public class IO {
    /**
     * Reference to MainApp.
     */
    private MainApp mainApp;

    /**
     * Constants for the different fields in the application.
     */
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FIRSTNAME = "first_name";
    private static final String LASTNAME = "last_name";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";
    private static final String RUOLO = "ruolo";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";


    /**
     * Sets a reference to the MainApp.
     *
     * @param mainApp the reference to the MainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Writes the user data to the database.
     *
     * @param map the map containing the user data to be written
     */
    public void write(Map<String, String> map) {
        DAO objDAO = mainApp.getDAO();
        String username = map.get(USERNAME);
        String password = map.get(PASSWORD);
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String email = map.get(EMAIL);
        String birthday = map.get(BIRTHDAY);
        String ruolo = map.get(RUOLO);
        String query = "INSERT INTO `user` (`username`, `email`, `password`, `first_name`, `last_name`, `ruolo`, `birthday`) VALUES ('"
                + username + "', '"
                + email + "', '"
                + password + "', '"
                + firstName + "', '"
                + lastName + "', '"
                + ruolo + "', '"
                + birthday + "')";
        try {
            objDAO.updateAndGetDB(query);
            if (Objects.equals(ruolo, "gym")) {
                String gymName = map.get("gymName");
                String address = map.get(ADDRESS);
                String latitude = map.get(LATITUDE);
                String longitude = map.get(LONGITUDE);
                String phone = map.get(PHONE);
                query = "INSERT INTO `gym` (`name`, `owner`, `address`, `latitude`, `longitude`, `phone`) VALUES ('"
                        + gymName + "', '"
                        + username + "', '"
                        + address + "', '"
                        + latitude + "', '"
                        + longitude + "', '"
                        + phone + "')";
                objDAO.updateAndGetDB(query);
            }
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(IO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * It's called to read users in the DB.
     *
     * @return returns a map of the users' account
     */
    public Map<String, Map<String, String>> read(){
        HashMap<String, Map<String, String>> map = new HashMap<>();
        PreparedStatement ps = null;
        ResultSet rs;
        String className = IO.class.getName();
        String query;
        DBConnection db = DBConnection.getSingletonInstance();
        Connection connection = null;
        try{
           connection = db.getConnection();
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(IO.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
        try{
            assert connection != null;
            query = "SELECT * FROM user LEFT JOIN gym ON gym.owner = user.username";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> gymAccount = getInfoUser(rs);
                String userValue = rs.getString(USERNAME);                    //get user username
                map.put(userValue, gymAccount);
            }
        }
        catch (SQLException e) {
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(className);
                logger.info(e.toString());
            }
        }
        return map;
    }

    /**
     * It's called to get info of given user.
     *
     * @param rs the ResultSet that returned the DB
     *
     * @return returns a map of the user's account
     */
    public Map<String, String> getInfoUser(ResultSet rs){
        String className = IO.class.getName();
        HashMap<String, String> gymAccount = new HashMap<>();
        try {
                String userValue = rs.getString(USERNAME);                    //get user username
                String passValue = rs.getString(PASSWORD);                    //get user password
                String nameValue = rs.getString(FIRSTNAME);                  //get user first name
                String lastNameValue = rs.getString(LASTNAME);               //get user last name
                String email = rs.getString(EMAIL);                           //get user email
                String birthday = "";
                if(rs.getDate(BIRTHDAY) != null) {
                    birthday = rs.getDate(BIRTHDAY).toString();               //get user birthday
                }
                String ruolo = rs.getString(RUOLO);                           //get user ruolo
                String gymName = rs.getString("name");                         //get user gym_name
                String address = rs.getString(ADDRESS);                       //get user gym_address
                String latitude = String.valueOf(rs.getDouble(LATITUDE));     //get user gym_latitude
                String longitude = String.valueOf(rs.getDouble(LONGITUDE));   //get user gym_longitude
                String phone = rs.getString(PHONE);                           //get user gym_phone
                gymAccount.put(USERNAME, userValue);                                     //put userValue in userAccount
                gymAccount.put(PASSWORD, passValue);                                     //put user password in userAccount
                gymAccount.put("firstName", nameValue);                                    //put user firstName in userAccount
                gymAccount.put("lastName", lastNameValue);                                 //put user lastName in userAccount
                gymAccount.put(EMAIL, email);                                            //put user email in userAccount
                gymAccount.put(BIRTHDAY, birthday);                                      //put user birthday in userAccount
                gymAccount.put("gymName", gymName);                                       //put user gym_name in userAccount
                gymAccount.put(ADDRESS, address);                                        //put user address in userAccount
                gymAccount.put(LATITUDE, latitude);                                      //put user latitude in userAccount
                gymAccount.put(LONGITUDE, longitude);                                    //put user longitude in userAccount
                gymAccount.put(PHONE, phone);                                            //put user phone in userAccount
                gymAccount.put(RUOLO, ruolo);                                            //put user ruolo in userAccount
        }
        catch (SQLException e){
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
        return gymAccount;
    }
}
