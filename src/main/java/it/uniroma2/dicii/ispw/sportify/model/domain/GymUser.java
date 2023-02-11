package it.uniroma2.dicii.ispw.sportify.model.domain;

import it.uniroma2.dicii.ispw.sportify.model.dao.UserDAO;

import java.util.Objects;

/**
 * The class that represents
 * the gym user
 *
 * @see User
 */
public class GymUser extends User {

    /**
     * Constructs a new GymUser instance
     * with the given username and password.
     *
     * @param userName the username of the user
     * @param password the password of the user
     */
    public GymUser(String userName, String password) {
        super(userName, password);
        role = "gym";
    }

    /**
     * Update user in the DB.
     */
    @Override
    protected void update(){
        super.update();
        UserDAO.updateGymUser(this);
    }

    /**
     * The method that set up gym name
     *
     * @param name the gym name to set
     */
    public void setGymName(String name) {
        this.gymName = name;
        if (!Objects.equals(account.get("gymName"), name)) {
            this.update();
            account.put("gymName", name);
        }
    }

    /**
     * The method that set up gym address
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
        if (!Objects.equals(account.get("address"), address)) {
            this.update();
            account.put("address", address);
        }
    }

    /**
     * The method that set up gym latitude
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
        if (!Objects.equals(account.get("latitude"), latitude)) {
            this.update();
            account.put("latitude", latitude);
        }
    }

    /**
     * The method that set up gym longitude
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
        if (!Objects.equals(account.get("longitude"), longitude)) {
            this.update();
            account.put("longitude", longitude);
        }
    }

    /**
     * The method that set up gym phone
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
        if (!Objects.equals(account.get("phone"), phone)) {
            this.update();
            account.put("phone", phone);
        }
    }
}
