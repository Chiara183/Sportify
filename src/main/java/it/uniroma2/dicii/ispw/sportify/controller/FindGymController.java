package it.uniroma2.dicii.ispw.sportify.controller;

import it.uniroma2.dicii.ispw.sportify.MainApp;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.phone.FindGymPhoneGraphicController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.GraphicController;
import it.uniroma2.dicii.ispw.sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends the {@link Controller}
 * class and implements its own methods to handle
 * the finding of gyms.
 *
 * @see Controller
 */
public class FindGymController extends Controller{

    /**
     * Reference to the {@link FindGymPhoneGraphicController}
     * object used to manage the graphical representation of the data.
     */
    private FindGymPhoneGraphicController findGymGraphicController;

    /**
     * Constructor for the {@link FindGymController} class.
     * This sets the type of the controller to
     * {@link ControllerType#FIND_GYM}.
     */
    public FindGymController() {
        this.type = ControllerType.FIND_GYM;
    }

    /**
     * Method used to set the {@link FindGymPhoneGraphicController} object for the class.
     *
     * @param graphicController The {@link FindGymPhoneGraphicController}
     *                          object to be used by the class.
     */
    public void setGraphicController(FindGymPhoneGraphicController graphicController) {
        this.findGymGraphicController = graphicController;
    }

    /**
     * Method used to set the map for the class.
     * It loads the map overview, sets it in the
     * top of the root layout, and initializes the
     * map and its controls.
     *
     * @param projection The {@link Projection}
     *                   object to be used by the map.
     */
    public void setProjection(Projection projection){
        URL url;
        try {
            // Load find map overview.
            FXMLLoader loaderGym = new FXMLLoader();
            if(MainApp.isNotMobile()) {
                url = MainApp.class.getResource("DesktopView/Map.fxml");
                loaderGym.setLocation(Objects.requireNonNull(url));
            }
            else {
                url = MainApp.class.getResource("SmartphoneView/MapPhone.fxml");
                loaderGym.setLocation(url);
            }
            AnchorPane paneMap = loaderGym.load();

            // Set menu overview into the top of root layout.
            this.findGymGraphicController.getMap().getChildren().add(paneMap);

            // Give the gymEditController access to the main app.
            MapGraphicController graphicController = loaderGym.getController();
            MapController controller = new MapController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setUser(this.user);
            controller.setMenu(this.menu);
            controller.initMapAndControls(projection);
            controller.setSearchCache(this.searchCache);
            if(searchCache !=null){
                graphicController.searchAction();
            }
        }
        catch (IOException e) {
            Logger logger = Logger.getLogger(FindGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Sets the graphic controller for this class
     *
     * @param graphicController The graphic controller
     *                          to set
     */
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.findGymGraphicController = (FindGymPhoneGraphicController) graphicController;
    }
}

