package sportify.util;

import com.sothawo.mapjfx.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class that implements the useful
 * methods for an OpenStreetMap
 */
public class OpenStreetMapUtils {

    /**
     * The instance of the class.
     */
    private static OpenStreetMapUtils instance = null;

    /**
     * It's called to get an instance
     * of OpenStreetMapUtils
     *
     * @return An instance of the class
     */
    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    /**
     *  It's called to get the right
     *  string from web.
     *
     * @param url the url to try to
     *            connect to
     *
     * @return The answer to the connection
     * test
     */
    private String getRequest(String url){
        StringBuilder response = new StringBuilder();
        int respond;
        String className = OpenStreetMapUtils.class.getName();
        String result;
        try {
            final URL obj = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            respond = con.getResponseCode();
            if (respond != 200) {
                return null;
            }
            InputStream i = con.getInputStream();
            InputStreamReader iSt = new InputStreamReader(i);
            BufferedReader in = new BufferedReader(iSt);
            String inputLine;
            response = new StringBuilder();
            inputLine = in.readLine();
            while (inputLine != null) {
                response.append(inputLine);
                inputLine = in.readLine();
            }
            in.close();
        }
        catch(IOException e){
            Logger logger = Logger.getLogger(className);
            logger.log(Level.SEVERE, e.getMessage());
        }
        result = response.toString();
        return result;
    }

    /**
     * It's called to get coordinate from given address
     *
     * @param address the address of which to retrieve
     *                the coordinates
     *
     * @return returns a Map of two values which are
     * longitude and latitude of the requested address
     */
    public Map<String, Double> getCoordinates(String address) {
        Map<String, Double> res;
        StringBuilder query;
        String[] split = address.split(" ");

        query = new StringBuilder();
        res = new HashMap<>();
        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            res = Collections.emptyMap();
            return res;
        }
        String queryS;
        String queryResult;

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");
        queryS = query.toString();
        queryResult = getRequest(queryS);

        if (queryResult == null) {
            res = Collections.emptyMap();
            return res;
        }
        double lonD;
        double latD;
        String lon;
        String lat;
        JSONObject jsonObject;
        Object obj = JSONValue.parse(queryResult);

        if (obj instanceof JSONArray array &&
                !array.isEmpty()) {
            jsonObject = (JSONObject) array.get(0);
            lon = (String) jsonObject.get("lon");
            lonD = Double.parseDouble(lon);
            lat = (String) jsonObject.get("lat");
            latD = Double.parseDouble(lat);
            res.put("lon", lonD);
            res.put("lat", latD);
        }
        return res;
    }

    /**
     * It's called to get distance from given two coordinates
     *
     * @param startPoint The coordinate from which to start
     *                   measuring the distance
     * @param endpoint The coordinate to arrive at to calculate
     *                how far it is from the first
     *
     * @return The distance between the two coordinates
     */
    public Double getDistance (Coordinate startPoint, Coordinate endpoint){
        double d2r = Math.PI / 180;
        double dLong1 = endpoint.getLongitude();
        double dLong2 = startPoint.getLongitude();
        double dLong3 = dLong1 - dLong2;
        double dLong = dLong3 * d2r;
        double dLat1 = endpoint.getLatitude();
        double dLat2 = startPoint.getLatitude();
        double dLat3 = dLat1 - dLat2;
        double dLat = dLat3 * d2r;
        double a11 = Math.sin(dLat / 2.0);
        double a1 = Math.pow(a11, 2);
        double a21 = startPoint.getLatitude();
        double a2 = Math.cos(a21 * d2r);
        double a31 = endpoint.getLatitude();
        double a3 = Math.cos(a31 * d2r);
        double a41 = Math.sin(dLong / 2.0);
        double a4 = Math.pow(a41, 2);
        double a5 = a2 * a3 * a4;
        double a = a1 + a5;
        double c1 = Math.sqrt(a);
        double c2 = Math.sqrt(1 - a);
        double c3 = Math.atan2(c1, c2);
        double c = 2 * c3;
        return 6367 * c;
    }
}

