package com.example.sportify.controller.graphic;

import com.example.sportify.controller.Controller;
import com.example.sportify.controller.ControllerType;

public interface GraphicController {

    /** Is called to set controller*/
    void setController(Controller controller);

    /** Is called to get type*/
    ControllerType getGraphicType();
}
