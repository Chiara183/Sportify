package sportify.controller;

import sportify.model.dao.DAO;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymInfoGraphicController;
import sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.scene.Cursor;
import sportify.errorlogic.DAOException;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The MapController class extends the Controller
 * class and provides functionality for managing
 * a map display and its associated controls.
 *
 * @see Controller
 */
public class MapController extends Controller{

    /**
     * A constant string representing the
     * SQL query for selecting all gym data.
     */
    private static final String SELECTALL = "SELECT * " + "FROM gym ";

    /**
     * An instance of the MapGraphicController
     * class that manages the graphical display of the map.
     */
    private MapGraphicController graphicController;

    /**
     * A constant integer representing
     * the default zoom level for the map.
     */
    private static final int ZOOM_DEFAULT = 11;

    /* All the HashMap of the map*/
    /**
     * A HashMap that stores all the gym data, where the
     * key is the coordinate and the value is the gym name.
     */
    private final HashMap<Coordinate, String> allGym = new HashMap<>();

    /**
     * A HashMap that stores markers for the gyms, where the
     * key is the gym name and the value is the marker.
     */
    private final HashMap<String, Marker> mark = new HashMap<>();

    /* params for the WMS server. */
    /**
     * An instance of the WMSParam class that
     * contains parameters for connecting to a WMS server.
     */
    private final WMSParam wmsParam = new WMSParam().setUrl("https://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    /**
     * An instance of the XYZParam class that contains
     * parameters for connecting to an XYZ tile server.
     */
    private final XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions("'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    /**
     * Constructs a MapController object.
     */
    public MapController() {
        this.type = ControllerType.MAP;
    }

    /**
     * Implements the abstract setSearchCache
     * method from the Controller class.
     *
     * @param search an array of strings representing the search cache
     */
    @Override
    public void setSearchCache(String[] search) {
        graphicController.setSearchCache(search);
    }

    /**
     * Implements the abstract setGraphicController
     * method from the Controller class.
     *
     * @param graphicController an instance of the GraphicController class
     *                         that manages the graphical display of the map
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = (MapGraphicController) graphicController;
    }

    /**
     * called after the fxml is loaded and all objects are created.
     * This is not called initialize anymore,
     * because we need to pass in the projection before initializing.
     *
     * @param projection the projection for the map.
     */
    public void initMapAndControls(Projection projection) {
        // set ComboBox
        graphicController.setKmCombo();

        // set allGym list
        new Thread(this::loadCoordinate).start();

        // set the controls to disabled, this will be changed when the MapView is initialized
        graphicController.setControlsDisable(true);

        // watch the MapView's initialized property to finish initialization
        MapView map = graphicController.getMapView();
        map.initializedProperty().addListener(
                (observable, oldValue, newValue) ->
                {
            if (newValue) {
                afterMapIsInitialized();
            }
        }
        );

        // observe the map type radiobutton
        graphicController.setMapTypeGroup(wmsParam,xyzParams);

        setupEventHandlers();

        // finally, initialize the map view
        map.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build()
        );
    }

    /**
     * Sets up event handlers for the map and its controls.
     */
    private void setupEventHandlers() {
        graphicController.getMapView().addEventHandler(
                MarkerEvent.MARKER_CLICKED, event ->
                {
                    event.consume();
                    Coordinate coords = event.getMarker().getPosition();
                    if(event.getMarker().getMapLabel().isEmpty()) {
                        mark.forEach(
                                (id, gym) ->
                                {
                                    if (coords == gym.getPosition()) {
                                        String s = "Marker number: " + id;
                                        Logger logger = Logger.getLogger(MapController.class.getName());
                                        logger.log(Level.INFO, s);
                                        if (allGym.get(gym.getPosition()) != null) {
                                            String name = allGym.get(gym.getPosition());
                                            MapLabel labelGym = new MapLabel(name, 10, -10).setCssClass("label");
                                            graphicController.getMapView().removeMarker(event.getMarker());
                                            event.getMarker().attachLabel(labelGym);
                                            graphicController.getMapView().addMarker(event.getMarker());
                                        }
                                        else {
                                            MapLabel labelGym = new MapLabel("You are Here!", 10, -10).setCssClass("label");
                                            graphicController.getMapView().removeMarker(event.getMarker());
                                            event.getMarker().attachLabel(labelGym);
                                            graphicController.getMapView().addMarker(event.getMarker());
                                        }
                                    }
                                    else {
                                        gym.detachLabel();
                                    }
                                }
                                );
                    }
                    else {
                        graphicController.getMapView().removeMarker(event.getMarker());
                        event.getMarker().detachLabel();
                        graphicController.getMapView().addMarker(event.getMarker());
                    }
                }
                );
        graphicController.getMapView().addEventHandler(
                MarkerEvent.MARKER_ENTERED, event ->
                {
                    graphicController.getMapView().setCursor(Cursor.HAND);
                    mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
                }
                );
        graphicController.getMapView().addEventHandler(
                MarkerEvent.MARKER_EXITED, event ->
                {
                    graphicController.getMapView().setCursor(Cursor.DEFAULT);
                    mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
                }
                );

        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, this::loadGymInfo);
        graphicController.getMapView().addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, this::loadGymInfo);
        graphicController.getMapView().addEventHandler(
                MapLabelEvent.MAPLABEL_ENTERED, event ->
                {
                    graphicController.getMapView().setCursor(Cursor.HAND);
                    mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.HAND);
                }
                );
        graphicController.getMapView().addEventHandler(
                MapLabelEvent.MAPLABEL_EXITED, event ->
                {
                    graphicController.getMapView().setCursor(Cursor.DEFAULT);
                    mainApp.getPrimaryStage().getScene().getRoot().setCursor(Cursor.DEFAULT);
                }
                );
    }

    /**
     * Method to be called after the map is initialized.
     */
    private void afterMapIsInitialized() {
        // start at the harbour with default zoom
        graphicController.getMapView().setZoom(ZOOM_DEFAULT);
        graphicController.getMapView().setCenter(new Coordinate(41.9109, 12.4818));

        // now enable the controls
        graphicController.setControlsDisable(false);
    }

    /**
     * loadCoordinate is a private method that
     * is responsible for loading map coordinates.
     */
    private void loadCoordinate() {
        DAO objDAO = mainApp.getDAO();
        List<String> list = null;
        List<String> list1 = null;
        List<String> list2 = null;
        try {
            list = objDAO.checkDataColumn(SELECTALL, "latitude");
            list1 = objDAO.checkDataColumn(SELECTALL, "longitude");
            list2 = objDAO.checkDataColumn(SELECTALL, "name");
        }
        catch (DAOException e){
            Logger logger = Logger.getLogger(MapController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }

        int i = 0;
        assert list2 != null;
        for (String rs2 : list2) {
            String rs = list.get(i);
            String rs1 = list1.get(i);
            Coordinate gym = new Coordinate(
                    Double.parseDouble(rs),
                    Double.parseDouble(rs1));
            this.allGym.put(gym, rs2);
            i++;
        }

    }

    /**
     * loadGymInfo is a private method that takes in a
     * MapLabelEvent as an argument and is responsible
     * for loading gym information.
     *
     * @param event MapLabelEvent to be processed
     */
    private void loadGymInfo(MapLabelEvent event){
        event.consume();
        if(Objects.equals(event.getMapLabel().getText(), "You are Here!")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Marker is not a gym.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else {
            GymInfoGraphicController gymInfoGraphicController = new GymInfoGraphicController();
            GymInfoController gym = new GymInfoController();
            gym.setGraphicController(gymInfoGraphicController);
            gymInfoGraphicController.setController(gym);
            gym.setMainApp(this.mainApp);
            gym.setUser(this.user);
            gym.setMenu(this.menu);
            String[] searchCache = new String[2];
            searchCache[0] = this.graphicController.getSearch();
            searchCache[1] = this.graphicController.getKm();
            gym.setSearchCache(searchCache);
            this.menu.setGym(event.getMapLabel().getText());
            if(mainApp.isNotMobile()) {
                this.menu.setFindGym();
            }
            else {
                this.menu.setGymInfo(event.getMapLabel().getText());
            }
            gym.loadingGymName(event.getMapLabel().getText());
            gym.menu.setView(ControllerType.GYM_INFO);
        }
    }

    /**
     * getMark is a public method that returns a HashMap of markers.
     * The key is a string representation of the marker,
     * and the value is a Marker object.
     *
     * @return a HashMap of markers.
     */
    public HashMap<String, Marker> getMark(){
        return this.mark;
    }

    /**
     * getAllGym is a public method that returns a HashMap of all gym
     * information. The key is a Coordinate object representing the gym location,
     * and the value is a string representation of the gym information.
     *
     * @return a HashMap of all gym information.
     */
    public HashMap<Coordinate, String> getAllGym(){
        return this.allGym;
    }
}