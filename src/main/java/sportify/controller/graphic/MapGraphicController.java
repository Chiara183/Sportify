package sportify.controller.graphic;

import sportify.OpenStreetMapUtils;
import sportify.bean.MapBean;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.MapController;
import com.sothawo.mapjfx.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class MapGraphicController implements GraphicController{

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

    /** All radio button of the interface*/
    @FXML
    private RadioButton radioMsOSM;
    @FXML
    private RadioButton radioMsWMS;

    // ToggleGroup
    @FXML
    private ToggleGroup mapTypeGroup;

    // MapCircle
    MapCircle circle;

    /** Reference to controller*/
    private MapController controller;

    /**Reference to bean*/
    private final MapBean bean = new MapBean();

    // ObservableList
    private final ObservableList<String> radius = FXCollections.observableArrayList("1", "5", "10", "20", "50");

    /** It's called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (MapController) controller;
    }

    /** It's called to set search cache*/
    public void setSearchCache(String[] search) {
        if(search!=null) {
            this.search.setText(search[0]);
            this.km.setValue(search[1]);
        }
    }

    /** It's called to set km comboBox*/
    public void setKmCombo(){
        // set ComboBox
        km.setValue("Km");
        km.setItems(radius);
    }

    /** It's called to set mapTypeGroup*/
    public void setMapTypeGroup(WMSParam wmsParam, XYZParam xyzParams){
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
    }

    /** enables / disables the different controls*/
    public void setControlsDisable(boolean flag) {
        leftControls.setDisable(flag);
    }

    /** It's called to get mapView*/
    public MapView getMapView(){
        return this.mapView;
    }

    /** It's called to get search string*/
    public String getSearch(){
        while(!bean.checkSearch(this.search)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("Field is empty");
            alert.setContentText("Please fill search field");
            alert.showAndWait();
        }
        return this.search.getText();
    }

    /** It's called to get km round value*/
    public String getKm(){
        while(!bean.checkKm(this.km)){
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("Field empty");
            alert.setHeaderText("km field is empty");
            alert.setContentText("Please select a km range");
            alert.showAndWait();
        }
        return this.km.getValue();
    }

    /** The action of the button*/
    @FXML
    public void searchAction() {
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(search.getText());
        if (coords.get("lat") != null && coords.get("lon") != null){
            if(this.circle!=null) {
                mapView.removeMapCircle(this.circle);
                controller.getMark().forEach((id, marker) -> mapView.removeMarker(marker));
                controller.getMark().clear();
            }
            Coordinate latLong = new Coordinate(coords.get("lat"), coords.get("lon"));

            if (isNumeric(km.getValue())){
                double distance = Double.parseDouble(km.getValue());
                this.circle = new MapCircle(latLong, (distance+2)*1000.0).setVisible(true);
                mapView.addMapCircle(this.circle);
                mapView.setZoom(MapView.MAX_ZOOM - distance/5.0 - 14);
                controller.getAllGym().forEach((key, value) -> {
                    if(OpenStreetMapUtils.getInstance().getDistance(key,latLong)<=distance){
                        Marker myMarker = Marker.createProvided(Marker.Provided.GREEN).setPosition(key).setVisible(true);
                        controller.getMark().put(myMarker.getId(), myMarker);
                        mapView.addMarker(myMarker);
                    }
                });
            }

            Marker myMarker = Marker.createProvided(Marker.Provided.RED).setPosition(latLong).setVisible(true);
            controller.getMark().put(myMarker.getId(), myMarker);
            mapView.setCenter(latLong);
            mapView.addMarker(myMarker);
        }else{
            //show error message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(controller.getMainApp().getPrimaryStage());
            alert.setTitle("Wrong address");
            alert.setHeaderText("Sorry, we can't find your address");
            alert.setContentText("Please enter valid address");
            alert.showAndWait();
        }
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
