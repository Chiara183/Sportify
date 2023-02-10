package sportify.model.domain;

import sportify.model.dao.UserDAO;

/**
 * The class that represents
 * the gym user
 *
 * @see User
 */
public class GymUser extends User {

    /**
     * Constructs a new GymUser instance.
     */
    public GymUser() {
        this(null, null);
    }

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
        update();
    }

    /**
     * The method that set up gym address
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
        update();
    }

    /**
     * The method that set up gym latitude
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
        update();
    }

    /**
     * The method that set up gym longitude
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
        update();
    }

    /**
     * The method that set up gym phone
     *
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
        update();
    }
}
