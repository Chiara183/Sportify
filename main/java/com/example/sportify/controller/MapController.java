package com.example.sportify.controller;

import com.example.sportify.MainApp;
import com.example.sportify.OpenStreetMapUtils;
import com.sothawo.mapjfx.*;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapController {

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    // Button
    @FXML
    private Button searchButton;

    // MenuButton
    @FXML
    private MenuButton km;

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

    //  CoordinateLine
    /** the first CoordinateLine */
    private CoordinateLine trackMagenta;
    /** the second CoordinateLine */
    private CoordinateLine trackCyan;

    /** params for the WMS server. */
    private final WMSParam wmsParam = new WMSParam()
            .setUrl("https://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    private final XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions(
                    "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    // Reference to the main application.
    private MainApp mainApp = new MainApp();

    public MapController() {
    }

    @FXML
    public void searchAction() {
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(search.getText());
        System.out.println("latitude :" + coords.get("lat"));
        System.out.println("longitude:" + coords.get("lon"));
        if (coords.get("lat") != null && coords.get("lon") != null){
            Coordinate latLong = new Coordinate(coords.get("lat"), coords.get("lon"));

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
     * @param projection
     *     the projection to use in the map.
     */
    public void initMapAndControls(Projection projection) {

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

        // load two coordinate lines
        trackMagenta = loadCoordinateLine(this.mainApp.getClass().getResource("Address/M1.csv")).orElse(new CoordinateLine
                ()).setColor(Color.MAGENTA);
        trackCyan = loadCoordinateLine(this.mainApp.getClass().getResource("Address/M2.csv")).orElse(new CoordinateLine
                ()).setColor(Color.CYAN).setWidth(7);
        // get the extent of both tracks
        Extent tracksExtent = Extent.forCoordinates(
                Stream.concat(trackMagenta.getCoordinateStream(), trackCyan.getCoordinateStream())
                        .collect(Collectors.toList()));
        ChangeListener<Boolean> trackVisibleListener =
                (observable, oldValue, newValue) -> mapView.setExtent(tracksExtent);
        trackMagenta.visibleProperty().addListener(trackVisibleListener);
        trackCyan.visibleProperty().addListener(trackVisibleListener);

        // finally, initialize the map view
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
    }

    /**
     * enables / disables the different controls
     *
     * @param flag
     *     if true the controls are disabled
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

        // add the tracks
        mapView.addCoordinateLine(trackMagenta);
        mapView.addCoordinateLine(trackCyan);

        // now enable the controls
        setControlsDisable(false);
    }

    /**
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @param url
     *     url where to load from
     * @return optional CoordinateLine object
     * @throws java.lang.NullPointerException
     *     if uri is null
     */
    private Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (
                Stream<String> lines = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines()
        ) {
            return Optional.of(new CoordinateLine(
                    lines.map(line -> line.split(";")).filter(array -> array.length == 2)
                            .map(values -> new Coordinate(Double.valueOf(values[0]), Double.valueOf(values[1])))
                            .collect(Collectors.toList())));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}