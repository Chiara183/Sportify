package sportify.controller;

import sportify.MainApp;
import sportify.controller.graphic.phone.FindGymPhoneGraphicController;
import sportify.controller.graphic.GraphicController;
import sportify.controller.graphic.MapGraphicController;
import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FindGymController extends Controller{

    /** Reference to graphic gymEditController*/
    private FindGymPhoneGraphicController findGymGraphicController;

    /** The constructor.*/
    public FindGymController() {
        this.type = ControllerType.FIND_GYM;
    }

    /** Is called to set graphic gymEditController*/
    public void setGraphicController(FindGymPhoneGraphicController graphicController) {
        this.findGymGraphicController = graphicController;
    }

    /** Set the map*/
    public void setProjection(Projection projection){
        try {
            // Load find map overview.
            FXMLLoader loaderGym = new FXMLLoader();
            if(mainApp.isNotMobile()) {
                loaderGym.setLocation(Objects.requireNonNull(mainApp.getClass().getResource("DesktopView/Map.fxml")));
            } else {
                loaderGym.setLocation(MainApp.class.getResource("SmartphoneView/MapPhone.fxml"));
            }
            AnchorPane paneMap = loaderGym.load();

            // Set menu overview into the top of root layout.
            this.findGymGraphicController.getMap().getChildren().add(paneMap);

            // Give the gymEditController access to the main app.
            MapGraphicController graphicController = loaderGym.getController();
            MapController controller = new MapController();
            controller.setGraphicController(graphicController);
            graphicController.setController(controller);
            controller.setMainApp(this.mainApp);
            controller.setUser(this.user);
            controller.setMenu(this.menu);
            controller.initMapAndControls(projection);
            controller.setSearchCache(this.searchCache);
            if(searchCache !=null){
                graphicController.searchAction();
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(FindGymController.class.getName());
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /** Is called to set graphic gymEditController*/
    @Override
    public void setGraphicController(GraphicController graphicController) {
        this.findGymGraphicController = (FindGymPhoneGraphicController) graphicController;
    }
}

