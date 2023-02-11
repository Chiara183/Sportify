package it.uniroma2.dicii.ispw.sportify.controller.graphic;

import it.uniroma2.dicii.ispw.sportify.controller.Controller;
import it.uniroma2.dicii.ispw.sportify.controller.ControllerType;

public interface GraphicController {

    /** Is called to set controller*/
    void setController(Controller controller);

    /** Is called to get type*/
    ControllerType getGraphicType();
}
