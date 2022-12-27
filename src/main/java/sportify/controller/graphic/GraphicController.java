package sportify.controller.graphic;

import sportify.controller.Controller;
import sportify.controller.ControllerType;

public interface GraphicController {

    /** Is called to set controller*/
    void setController(Controller controller);

    /** Is called to get type*/
    ControllerType getGraphicType();
}
