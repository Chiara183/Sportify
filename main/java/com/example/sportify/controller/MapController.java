package com.example.sportify.controller;


import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.*;
import com.dlsc.gmapsfx.service.directions.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;

    protected StringProperty from = new SimpleStringProperty();
    protected StringProperty to = new SimpleStringProperty();
    protected DirectionsRenderer directionsRenderer = null;

    // Button
    @FXML
    protected Button clearButton;
    @FXML
    protected Button searchButton;

    // MenuButton
    @FXML
    private MenuButton km;

    // GoogleMapView
    @FXML
    protected GoogleMapView mapView;

    // TextField
    @FXML
    protected TextField fromTextField;
    @FXML
    protected TextField toTextField;
    @FXML
    protected TextField search;

    @FXML
    private void toTextFieldAction(ActionEvent event) {
        DirectionsRequest request = new DirectionsRequest(search.getText(), search.getText(), TravelModes.DRIVING, true);
        directionsRenderer = new DirectionsRenderer(true, mapView.getMap(), directionsPane);
        directionsService.getRoute(request, this, directionsRenderer);
    }

    @FXML
    private void clearDirections(ActionEvent event) {
        directionsRenderer.clearDirections();
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInitializedListener(this);
        to.bindBidirectional(search.textProperty());
        from.bindBidirectional(search.textProperty());
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(41.902782, 12.496366))
                .zoomControl(true)
                .zoom(11)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
    }

}