package com.example.sportify.user;

import com.example.sportify.DAO;
import javafx.beans.property.SimpleStringProperty;

public class gymUser extends User {

    /**
     * Default constructor.
     */
    public gymUser() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     */
    public gymUser(String userName, String password) {
        super(userName, password);
        role = new SimpleStringProperty("gym");
        this.gymName = new SimpleStringProperty(null);
        this.address = new SimpleStringProperty(null);
        this.latitude = new SimpleStringProperty(null);
        this.longitude = new SimpleStringProperty(null);
        this.phone = new SimpleStringProperty(null);
    }

    @Override
    public void setGymName(String name) {
        this.gymName.set(name);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `name` = '"
                        + name + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    @Override
    public void setAddress(String address) {
        this.address.set(address);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `address` = '"
                        + address + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    @Override
    public void setLatitude(String latitude) {
        this.latitude.set(latitude);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `latitude` = '"
                        + latitude + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    @Override
    public void setLongitude(String longitude) {
        this.longitude.set(longitude);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `longitude` = '"
                        + longitude + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }

    @Override
    public void setPhone(String phone) {
        this.phone.set(phone);
        DAO obj_DAO = mainApp.getDAO();
        obj_DAO.updateDB(
                "UPDATE `gym` SET `phone` = '"
                        + phone + "' WHERE `gym`.`owner` = '"
                        + this.userName.getValue() +"'");
    }
}
