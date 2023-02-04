package sportify.controller;

import sportify.DAO;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.GymInfoGraphicController;
import sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.scene.Cursor;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapController extends Controller{

    private static final String SELECTALL = "SELECT * " + "FROM gym ";

    /** Reference to graphicController*/
    private MapGraphicController graphicController;

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 11;

    /* All the HashMap of the map*/
    private final HashMap<Coordinate, String> allGym = new HashMap<>();
    private final HashMap<String, Marker> mark = new HashMap<>();

    /* params for the WMS server. */
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

    /** Is called to set graphicController*/
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

        // set allGym list
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
                        String s = "Marker number: " + id;
                        Logger logger = Logger.getLogger(MapController.class.getName());
                        logger.log(Level.INFO, s);
                        if (allGym.get(gym.getPosition()) != null) {
                            String name = allGym.get(gym.getPosition());
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
        DAO objDAO = mainApp.getDAO();
        List<String> list = objDAO.checkData(SELECTALL, "latitude");
        List<String> list1 = objDAO.checkData(SELECTALL, "longitude");
        List<String> list2 = objDAO.checkData(SELECTALL, "name");

        int i = 0;
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

    /** load a gym info overview*/
    private void loadGymInfo(MapLabelEvent event){
        event.consume();
        if(Objects.equals(event.getMapLabel().getText(), "You are Here!")) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Marker is not a gym.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
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
            } else {
                this.menu.setGymInfo(event.getMapLabel().getText());
            }
            gym.loadingGymName(event.getMapLabel().getText());
            gym.menu.setView(ControllerType.GYM_INFO);
        }
    }

    /** Is called to get mark hashmap*/
    public HashMap<String, Marker> getMark(){
        return this.mark;
    }

    /** Is called to get gym hashmap*/
    public HashMap<Coordinate, String> getAllGym(){
        return this.allGym;
    }
}