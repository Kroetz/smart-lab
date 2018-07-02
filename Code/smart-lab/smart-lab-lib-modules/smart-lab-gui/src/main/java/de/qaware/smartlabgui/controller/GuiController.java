package de.qaware.smartlabgui.controller;

import de.qaware.smartlabapi.GuiApiConstants;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabgui.business.IGuiBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(GuiApiConstants.MAPPING_BASE)
@Slf4j
public class GuiController extends AbstractSmartLabController {

    private final IGuiBusinessLogic guiBusinessLogic;

    public GuiController(IGuiBusinessLogic guiBusinessLogic) {
        this.guiBusinessLogic = guiBusinessLogic;
    }
}
