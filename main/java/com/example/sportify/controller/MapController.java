package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.MainApp;
import com.example.sportify.OpenStreetMapUtils;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class MapController {

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    // ObservableList
    ObservableList<String> radius = FXCollections.observableArrayList("1", "5", "10", "20", "50");
    HashMap<String, Coordinate> all_gym = new HashMap<>();
    ObservableList<Marker> mark = FXCollections.observableArrayList();

    // MapCircle
    MapCircle circle;

    // ComboBox
    @FXML
    private ComboBox<String> km;

    /** the MapView containing the map */
    // MapView
    @FXML
    private MapView mapView;

    /** Accordion for all the different options */
    @FXML
    private Accordion leftControls;

    // TextField
    @FXML
    private TextField search;

    // RadioButton
    @FXML
    private RadioButton radioMsOSM;
    @FXML
    private RadioButton radioMsBR;
    @FXML
    private RadioButton radioMsCd;
    @FXML
    private RadioButton radioMsCg;
    @FXML
    private RadioButton radioMsCl;
    @FXML
    private RadioButton radioMsBA;
    @FXML
    private RadioButton radioMsBAwL;
    @FXML
    private RadioButton radioMsWMS;
    @FXML
    private RadioButton radioMsXYZ;

    /** ToggleGroup for the MapStyle radios */
    @FXML
    private ToggleGroup mapTypeGroup;

    /** params for the WMS server. */
    private final WMSParam wmsParam = new WMSParam()
            .setUrl("https://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    private final XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    // Reference to the main application.
    private final MainApp mainApp = new MainApp();

    public MapController() {
    }

    @FXML
    public void searchAction() {
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(search.getText());
        if (coords.get("lat") != null && coords.get("lon") != null){
            if(this.circle!=null) {
                mapView.removeMapCircle(this.circle);
                mark.forEach(marker -> mapView.removeMarker(marker));
                mark.clear();
            }
            Coordinate latLong = new Coordinate(coords.get("lat"), coords.get("lon"));

            if (isNumeric(km.getValue())){
                double distance = Double.parseDouble(km.getValue());
                this.circle = new MapCircle(latLong, (distance+2)*1000.0).setVisible(true);
                mapView.addMapCircle(this.circle);
                mapView.setZoom(MapView.MAX_ZOOM - distance/5.0 - 14);
                all_gym.forEach((key, value) -> {
                    if(OpenStreetMapUtils.getInstance().getDistance(value,latLong)<=distance){
                        MapLabel labelGym = new MapLabel(key, 10, -10).setVisible(false).setCssClass("label");
                        Marker myMarker = Marker.createProvided(Marker.Provided.GREEN).setPosition(value).setVisible(true).attachLabel(labelGym);
                        mark.add(myMarker);
                        mapView.addMarker(myMarker);
                    }
                });
            }

            Marker myMarker = Marker.createProvided(Marker.Provided.RED).setPosition(latLong).setVisible(true);
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
            MapType mapType = MapType.OSM;
            if (newValue == radioMsOSM) {
                mapType = MapType.OSM;
            } else if (newValue == radioMsBR) {
                mapType = MapType.BINGMAPS_ROAD;
            } else if (newValue == radioMsCd) {
                mapType = MapType.BINGMAPS_CANVAS_DARK;
            } else if (newValue == radioMsCg) {
                mapType = MapType.BINGMAPS_CANVAS_GRAY;
            } else if (newValue == radioMsCl) {
                mapType = MapType.BINGMAPS_CANVAS_LIGHT;
            } else if (newValue == radioMsBA) {
                mapType = MapType.BINGMAPS_AERIAL;
            } else if (newValue == radioMsBAwL) {
                mapType = MapType.BINGMAPS_AERIAL_WITH_LABELS;
            } else if (newValue == radioMsWMS) {
                mapView.setWMSParam(wmsParam);
                mapType = MapType.WMS;
            } else if (newValue == radioMsXYZ) {
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
        mapView.addEventHandler(MapViewEvent.MAP_POINTER_MOVED, event -> {
            Coordinate coords = event.getCoordinate();
                    mark.forEach((gym) -> {
                        if(coords == gym.getPosition()){
                            if (gym.getMapLabel().isPresent()) {
                                MapLabel label = gym.getMapLabel().get();
                                label.setVisible(true);
                                gym.detachLabel();
                                gym.attachLabel(label);
                            }
                        }
                    });
        });

        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            //TODO
        });
        mapView.addEventHandler(MarkerEvent.MARKER_RIGHTCLICKED, event -> {
            //TODO
        });

        mapView.addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, event -> {
            //TODO
        });
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, event -> {
            //TODO
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
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @throws java.lang.NullPointerException if path is null
     */
    private void loadCoordinate() {
        try {
            DAO obj_DAO = new DAO();
            ResultSet rs = obj_DAO.Check_Data(
                    "SELECT * " +
                            "FROM user " +
                            "LEFT JOIN gym ON gym.owner = user.username " +
                            "WHERE user.ruolo = \"gym\"");
            while (rs.next()) {
                Coordinate gym = new Coordinate(
                        Double.parseDouble(rs.getString("latitude")),
                        Double.parseDouble(rs.getString("longitude")));
                this.all_gym.put(rs.getString("name"), gym);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}