package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.OpenStreetMapUtils;
import com.example.sportify.readWriteFile;
import com.sothawo.mapjfx.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class MapController {

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    // ObservableList
    ObservableList<String> radius = FXCollections.observableArrayList("1", "5", "10", "20", "50");
    ObservableList<Coordinate> all_gym = FXCollections.observableArrayList();

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
            }
            Coordinate latLong = new Coordinate(coords.get("lat"), coords.get("lon"));

            if (isNumeric(km.getValue())){
                int distance = Integer.parseInt(km.getValue());
                this.circle = new MapCircle(latLong, distance*1000).setVisible(true);
                mapView.addMapCircle(this.circle);
                mapView.setZoom(MapView.MAX_ZOOM - distance/5.0 - 14);
                System.out.println(all_gym);
                all_gym.forEach((gym) -> {
                    if(OpenStreetMapUtils.getInstance().getDistance(gym,latLong)<=distance){
                        Marker myMarker = Marker.createProvided(Marker.Provided.GREEN).setPosition(gym).setVisible(true);
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
        //loadCoordinate(System.getProperty("user.dir") + "\\trunk\\SystemFile\\" + "Gym.csv");
        loadCoordinate("gym.dat");

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

        // finally, initialize the map view
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
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
     * @param name of the file
     * @throws java.lang.NullPointerException if path is null
     */
    private void loadCoordinate(String name) {
        /*try {
            BufferedReader lines = new BufferedReader(new FileReader(path));
            String line = lines.readLine();
            while (line!=null){
                String[] coord = line.split(";");
                Coordinate gym = new Coordinate(Double.valueOf(coord[0]), Double.valueOf(coord[1]));
                this.all_gym.add(gym);
                line = lines.readLine();
            }
            lines.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }*/
        readWriteFile file = new readWriteFile();
        HashMap<String, HashMap<String, String>> account = file.readFile(name);
        for (String key : account.keySet()) {
            Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(account.get(key).get("gymAddress"));
            Coordinate gym = new Coordinate(coords.get("lat"), coords.get("lon"));
            this.all_gym.add(gym);
        }
    }
}