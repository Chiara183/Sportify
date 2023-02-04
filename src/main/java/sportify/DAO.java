package sportify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    /** The main connection of the project*/
    Connection connection = null;


    /** It's called to set connection*/
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /** It's called to get data from DB*/
    public List<String> checkData(String query, String column){
        PreparedStatement ps = null;
        ResultSet rs;
        List<String> result = new ArrayList<>();
        String className = DAO.class.getName();
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
        return result;
    }

    /** It's called to update data in DB*/
    public void updateDB(String query){
        PreparedStatement ps = null;
        String className = DAO.class.getName();
        try{
            ps = connection.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e){
            Logger logger = Logger.getLogger(className);
            logger.log(Level.WARNING, e.getMessage());
        }
        finally{
            try {
                Objects.requireNonNull(ps).close();
            }
            catch (SQLException e) {
                Logger logger = Logger.getLogger(className);
                logger.info(e.toString());
            }
        }
    }
}
