package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.OpenStreetMapUtils;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class MapController extends Controller{

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    // ObservableList
    private final ObservableList<String> radius = FXCollections.observableArrayList("1", "5", "10", "20", "50");

    // HashMap
    private final HashMap<Coordinate, String> all_gym = new HashMap<>();
    private final HashMap<String, Marker> mark = new HashMap<>();

    // MapCircle
    MapCircle circle;

    // ComboBox
    @FXML
    private ComboBox<String> km;

    // MapView
    @FXML
    private MapView mapView;

    // Accordion
    @FXML
    private Accordion leftControls;

    // TextField
    @FXML
    private TextField search;

    // RadioButton
    @FXML
    private RadioButton radioMsOSM;
    @FXML
    private RadioButton radioMsWMS;

    // ToggleGroup
    @FXML
    private ToggleGroup mapTypeGroup;

    /** params for the WMS server. */
    private final WMSParam wmsParam = new WMSParam()
            .setUrl("https://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    private final XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    public MapController() {
        this.type = ControllerType.MAP;
    }

    @Override
    public void setSearchCache(String[] search) {
        if(search!=null) {
            this.search.setText(search[0]);
            this.km.setValue(search[1]);
        }
    }

    @FXML
    public void searchAction() {
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(search.getText());
        if (coords.get("lat") != null && coords.get("lon") != null){
            if(this.circle!=null) {
                mapView.removeMapCircle(this.circle);
                mark.forEach((id, marker) -> mapView.removeMarker(marker));
                mark.clear();
            }
            Coordinate latLong = new Coordinate(coords.get("lat"), coords.get("lon"));

            if (isNumeric(km.getValue())){
                double distance = Double.parseDouble(km.getValue());
                this.circle = new MapCircle(latLong, (distance+2)*1000.0).setVisible(true);
                mapView.addMapCircle(this.circle);
                mapView.setZoom(MapView.MAX_ZOOM - distance/5.0 - 14);
                all_gym.forEach((key, value) -> {
                    if(OpenStreetMapUtils.getInstance().getDistance(key,latLong)<=distance){
                        Marker myMarker = Marker.createProvided(Marker.Provided.GREEN).setPosition(key).setVisible(true);
                        mark.put(myMarker.getId(), myMarker);
                        mapView.addMarker(myMarker);
                    }
                });
            }

            Marker myMarker = Marker.createProvided(Marker.Provided.RED).setPosition(latLong).setVisible(true);
            mark.put(myMarker.getId(), myMarker);
            mapView.setCenter(latLong);
            mapView.addMarker(myMarker);
        }else{
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Wrong address");
            alert.setHeaderText("Sorry, we can't find your address");
            alert.setContentText("Please enter valid address");
            alert.showAndWait();
        }
    }


    /**
     * called after the fxml is loaded and all objects are created. This is not called initialize anymore,
     * because we need to pass in the projection before initializing.
     *
     * @param projection to use in the map.
     */
    public void initMapAndControls(Projection projection) {
        // set ComboBox
        km.setValue("Km");
        km.setItems(radius);

        // set all_gym list
        new Thread(this::loadCoordinate).start();

        // set the controls to disabled, this will be changed when the MapView is initialized
        setControlsDisable(true);

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        // observe the map type radiobutton
        mapTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            MapType mapType;
            if (newValue == radioMsOSM) {
                mapType = MapType.OSM;
            } else if (newValue == radioMsWMS) {
                mapView.setWMSParam(wmsParam);
                mapType = MapType.WMS;
            } else {
                mapView.setXYZParam(xyzParams);
                mapType = MapType.XYZ;
            }
            mapView.setMapType(mapType);
        });
        mapTypeGroup.selectToggle(radioMsOSM);

        setupEventHandlers();

        // finally, initialize the map view
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
    }

    /**
     * initializes the event handlers.
     */
    private void setupEventHandlers() {
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            event.consume();
            Coordinate coords = event.getMarker().getPosition();
            if(event.getMarker().getMapLabel().isEmpty()) {
                mark.forEach((id, gym) -> {
                    if (coords == gym.getPosition()) {
                        System.out.println("Marker number: " + id);
                        if (all_gym.get(gym.getPosition()) != null) {
                            String name = all_gym.get(gym.getPosition());
                            MapLabel labelGym = new MapLabel(name, 10, -10).setCssClass("label");
                            mapView.removeMarker(event.getMarker());
                            event.getMarker().attachLabel(labelGym);
                            mapView.addMarker(event.getMarker());
                        } else {
                            MapLabel labelGym = new MapLabel("You are Here!", 10, -10).setCssClass("label");
                            mapView.removeMarker(event.getMarker());
                            event.getMarker().attachLabel(labelGym);
                            mapView.addMarker(event.getMarker());
                        }
                    } else {
                        gym.detachLabel();
                    }
                });
            } else {
                mapView.removeMarker(event.getMarker());
                event.getMarker().detachLabel();
                mapView.addMarker(event.getMarker());
            }
        });
        mapView.addEventHandler(MarkerEvent.MARKER_RIGHTCLICKED, event -> {
            //TODO
        });
        mapView.addEventHandler(MarkerEvent.MARKER_ENTERED, event -> {
            mapView.setCursor(Cursor.HAND);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
        });
        mapView.addEventHandler(MarkerEvent.MARKER_EXITED, event -> {
            mapView.setCursor(Cursor.DEFAULT);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
        });

        mapView.addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, this::loadGymInfo);
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, this::loadGymInfo);
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_ENTERED, event -> {
            mapView.setCursor(Cursor.HAND);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
        });
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_EXITED, event -> {
            mapView.setCursor(Cursor.DEFAULT);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * enables / disables the different controls
     *
     * @param flag if true the controls are disabled
     */
    private void setControlsDisable(boolean flag) {
        leftControls.setDisable(flag);
    }

    /**
     * finishes setup after the mpa is initialized
     */
    private void afterMapIsInitialized() {
        // start at the harbour with default zoom
        mapView.setZoom(ZOOM_DEFAULT);
        mapView.setCenter(new Coordinate(41.9109, 12.4818));

        // now enable the controls
        setControlsDisable(false);
    }

    /**
     * load a coordinate of the gym
     */
    private void loadCoordinate() {
        try {
            DAO obj_DAO = mainApp.getDAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM user " +
                            "LEFT JOIN gym ON gym.owner = user.username " +
                            "WHERE user.ruolo = \"gym\"");
            while (rs.next()) {
                Coordinate gym = new Coordinate(
                        Double.parseDouble(rs.getString("latitude")),
                        Double.parseDouble(rs.getString("longitude")));
                this.all_gym.put(gym, rs.getString("name"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * load a gym info overview
     */
    private void loadGymInfo(MapLabelEvent event){
        event.consume();
        if(Objects.equals(event.getMapLabel().getText(), "You are Here!")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Marker is not a gym.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            GymInfoController gym = new GymInfoController();
            gym.setMainApp(this.mainApp);
            gym.setUser(this.user);
            gym.setMenu(this.menu);
            String[] search_cache = new String[2];
            search_cache[0] = this.search.getText();
            search_cache[1] = this.km.getValue();
            gym.setSearchCache(search_cache);
            this.menu.setGym(event.getMapLabel().getText());
            gym.loadingGymName(event.getMapLabel().getText());
        }
    }
}