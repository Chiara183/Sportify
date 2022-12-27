package sportify;

import com.sothawo.mapjfx.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenStreetMapUtils {

    /** The instance of the class.*/
    private static OpenStreetMapUtils instance = null;

    /** It's called to get an instance of OpenStreetMapUtils*/
    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    /** It's called to get the right string from web*/
    private String getRequest(String url){
        StringBuilder response = new StringBuilder();
        try {
            final URL obj = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            if (con.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch(IOException e){
            Logger logger = Logger.getLogger(OpenStreetMapUtils.class.getName());
            logger.log(Level.SEVERE, e.getMessage());        }

        return response.toString();
    }

    /** It's called to get coordinate from given address*/
    public Map<String, Double> getCoordinates(String address) {
        Map<String, Double> res;
        StringBuilder query;
        String[] split = address.split(" ");

        query = new StringBuilder();
        res = new HashMap<>();
        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return Collections.emptyMap();
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        String queryResult = getRequest(query.toString());

        if (queryResult == null) {
            return Collections.emptyMap();
        }

        Object obj = JSONValue.parse(queryResult);

        if (obj instanceof JSONArray array && array.size() > 0) {

                JSONObject jsonObject = (JSONObject) array.get(0);

                String lon = (String) jsonObject.get("lon");
                String lat = (String) jsonObject.get("lat");
                res.put("lon", Double.parseDouble(lon));
                res.put("lat", Double.parseDouble(lat));

        }
        return res;
    }

    /** It's called to get distance from given two coordinates*/
    public Double getDistance (Coordinate startPoint, Coordinate endpoint){
        double d2r = Math.PI / 180;
        double dLong = (endpoint.getLongitude() - startPoint.getLongitude()) * d2r;
        double dLat = (endpoint.getLatitude() - startPoint.getLatitude()) * d2r;
        double a =
                Math.pow(Math.sin(dLat / 2.0), 2)
                        + Math.cos(startPoint.getLatitude() * d2r)
                        * Math.cos(endpoint.getLatitude() * d2r)
                        * Math.pow(Math.sin(dLong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6367 * c;
    }
}

