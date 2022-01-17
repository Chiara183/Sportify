package com.example.sportify.controller;

import com.example.sportify.DAO;
import com.example.sportify.controller.graphic.GraphicController;
import com.example.sportify.controller.graphic.GymInfoGraphicController;
import com.example.sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.scene.Cursor;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class MapController extends Controller{

    /** Reference to graphic controller*/
    private MapGraphicController graphicController;

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    /** All the HashMap of the map*/
    private final HashMap<Coordinate, String> all_gym = new HashMap<>();
    private final HashMap<String, Marker> mark = new HashMap<>();

    /** params for the WMS server. */
    private final WMSParam wmsParam = new WMSParam().setUrl("https://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");
    private final XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    /** The constructor.*/
    public MapController() {
        this.type = ControllerType.MAP;
    }

    /** It's called to set search cache*/
    @Override
    public void setSearchCache(String[] search) {
        graphicController.setSearchCache(search);
    }

    /** Is called to set graphic controller*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (MapGraphicController) graphicController;
    }

    /**
     * called after the fxml is loaded and all objects are created. This is not called initialize anymore,
     * because we need to pass in the projection before initializing.
     *
     * @param projection to use in the map.
     */
    public void initMapAndControls(Projection projection) {
        // set ComboBox
        graphicController.setKmCombo();

        // set all_gym list
        new Thread(this::loadCoordinate).start();

        // set the controls to disabled, this will be changed when the MapView is initialized
        graphicController.setControlsDisable(true);

        // watch the MapView's initialized property to finish initialization
        graphicController.getMapView().initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        // observe the map type radiobutton
        graphicController.setMapTypeGroup(wmsParam,xyzParams);

        setupEventHandlers();

        // finally, initialize the map view
        graphicController.getMapView().initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
    }

    /** initializes the event handlers.*/
    private void setupEventHandlers() {
        graphicController.getMapView().addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            event.consume();
            Coordinate coords = event.getMarker().getPosition();
            if(event.getMarker().getMapLabel().isEmpty()) {
                mark.forEach((id, gym) -> {
                    if (coords == gym.getPosition()) {
                        System.out.println("Marker number: " + id);
                        if (all_gym.get(gym.getPosition()) != null) {
                            String name = all_gym.get(gym.getPosition());
                            MapLabel labelGym = new MapLabel(name, 10, -10).setCssClass("label");
                            graphicController.getMapView().removeMarker(event.getMarker());
                            event.getMarker().attachLabel(labelGym);
                            graphicController.getMapView().addMarker(event.getMarker());
                        } else {
                            MapLabel labelGym = new MapLabel("You are Here!", 10, -10).setCssClass("label");
                            graphicController.getMapView().removeMarker(event.getMarker());
                            event.getMarker().attachLabel(labelGym);
                            graphicController.getMapView().addMarker(event.getMarker());
                        }
                    } else {
                        gym.detachLabel();
                    }
                });
            } else {
                graphicController.getMapView().removeMarker(event.getMarker());
                event.getMarker().detachLabel();
                graphicController.getMapView().addMarker(event.getMarker());
            }
        });
        graphicController.getMapView().addEventHandler(MarkerEvent.MARKER_RIGHTCLICKED, event -> {
            //TODO
        });
        graphicController.getMapView().addEventHandler(MarkerEvent.MARKER_ENTERED, event -> {
            graphicController.getMapView().setCursor(Cursor.HAND);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
        });
        graphicController.getMapView().addEventHandler(MarkerEvent.MARKER_EXITED, event -> {
            graphicController.getMapView().setCursor(Cursor.DEFAULT);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
        });

        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, this::loadGymInfo);
        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, this::loadGymInfo);
        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_ENTERED, event -> {
            graphicController.getMapView().setCursor(Cursor.HAND);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
        });
        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_EXITED, event -> {
            graphicController.getMapView().setCursor(Cursor.DEFAULT);
            mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
        });
    }

    /** finishes setup after the mpa is initialized*/
    private void afterMapIsInitialized() {
        // start at the harbour with default zoom
        graphicController.getMapView().setZoom(ZOOM_DEFAULT);
        graphicController.getMapView().setCenter(new Coordinate(41.9109, 12.4818));

        // now enable the controls
        graphicController.setControlsDisable(false);
    }

    /** load the coordinate of all gym*/
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

    /** load a gym info overview*/
    private void loadGymInfo(MapLabelEvent event){
        event.consume();
        if(Objects.equals(event.getMapLabel().getText(), "You are Here!")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Marker is not a gym.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            GymInfoGraphicController graphicController = new GymInfoGraphicController();
            GymInfoController gym = new GymInfoController();
            gym.setGraphicController(graphicController);
            graphicController.setController(gym);
            gym.setMainApp(this.mainApp);
            gym.setUser(this.user);
            gym.setMenu(this.menu);
            String[] search_cache = new String[2];
            search_cache[0] = this.graphicController.getSearch();
            search_cache[1] = this.graphicController.getKm();
            gym.setSearchCache(search_cache);
            this.menu.setGym(event.getMapLabel().getText());
            this.menu.setFindGym();
            gym.loadingGymName(event.getMapLabel().getText());
        }
    }

    /** Is called to get mark hashmap*/
    public HashMap<String, Marker> getMark(){
        return this.mark;
    }

    /** Is called to get gym hashmap*/
    public HashMap<Coordinate, String> getAllGym(){
        return this.all_gym;
    }
}