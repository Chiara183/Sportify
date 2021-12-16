package com.example.sportify.controller;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.object.*;
import com.example.sportify.MainApp;
import com.example.sportify.OpenStreetMapUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MapController implements Initializable{

    // Button
    @FXML
    private Button searchButton;

    // MenuButton
    @FXML
    private MenuButton km;

    // GoogleMapView
    @FXML
    private GoogleMapView mapView;

    // TextField
    @FXML
    private TextField search;

    // WebView
    @FXML
    private WebView webMap;

    private GoogleMap map;

    // Reference to the main application.
    private final MainApp mainApp = new MainApp();

    public MapController() {
    }

    @FXML
    public void searchAction() {
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(search.getText());
        System.out.println("latitude :" + coords.get("lat"));
        System.out.println("longitude:" + coords.get("lon"));
        if (coords.get("lat") != null && coords.get("lon") != null){
            LatLong latLong = new LatLong(coords.get("lat"), coords.get("lon"));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLong)
                    .title("My new Marker")
                    .animation(Animation.DROP)
                    .visible(true);
            //.icon("my_marker.png")

            Marker myMarker = new Marker(markerOptions);
            map.setCenter(latLong);
            map.addMarker(myMarker);
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInitializedListener(this::configureMap);
        //webMap.getEngine().load("https://www.opencyclemap.org/");
    }

    protected void configureMap() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(41.9109, 12.4818))
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(11)
                .keyboardShortcuts(false)
                .clickableIcons(false);
        map = mapView.createMap(mapOptions, false);

        //map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {});

    }
}