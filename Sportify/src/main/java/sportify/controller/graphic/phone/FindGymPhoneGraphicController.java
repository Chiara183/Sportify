package sportify.controller.graphic.phone;

import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.FindGymController;
import sportify.controller.graphic.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class FindGymPhoneGraphicController implements GraphicController {

    /** Reference to controller*/
    protected FindGymController controller;

    /** Reference to the map pane*/
    @FXML
    private Pane map;

    /** Is called to get map pane*/
    public Pane getMap() {
        return this.map;
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.controller = (FindGymController) controller;
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return controller.getType();
    }
}
