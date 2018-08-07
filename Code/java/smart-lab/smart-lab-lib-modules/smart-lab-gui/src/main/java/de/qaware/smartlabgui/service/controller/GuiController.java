package de.qaware.smartlabgui.service.controller;

import de.qaware.smartlab.api.service.constant.gui.GuiApiConstants;
import de.qaware.smartlabcore.data.location.LocationId;
import de.qaware.smartlabcore.service.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.service.controller.url.AbstractBaseUrlController;
import de.qaware.smartlabcore.service.url.IBaseUrlDetector;
import de.qaware.smartlabgui.service.business.IGuiBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@Controller
@RequestMapping(GuiApiConstants.MAPPING_BASE)
@Slf4j
public class GuiController extends AbstractSmartLabController {

    private final IGuiBusinessLogic guiBusinessLogic;

    public GuiController(IGuiBusinessLogic guiBusinessLogic) {
        this.guiBusinessLogic = guiBusinessLogic;
    }

    @GetMapping(GuiApiConstants.MAPPING_GET_CURRENT_MEETING_STATUS_PAGE)
    public String getCurrentMeetingStatusPage(@PathVariable(GuiApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId, Model model) {
        return this.guiBusinessLogic.getCurrentMeetingStatusPage(LocationId.of(locationId), model);
    }

    @GetMapping(GuiApiConstants.MAPPING_GET_CURRENT_MEETING_AGENDA_PAGE)
    public String getCurrentMeetingAgendaPage(@PathVariable(GuiApiConstants.PARAMETER_NAME_LOCATION_ID) String locationId, Model model) {
        return this.guiBusinessLogic.getCurrentMeetingAgendaPage(LocationId.of(locationId), model);
    }

    @RestController
    @RequestMapping(GuiApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlController extends AbstractBaseUrlController {

        public BaseUrlController(IBaseUrlDetector baseUrlDetector) {
            super(baseUrlDetector);
        }

        @Override
        @GetMapping(GuiApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            return super.getBaseUrl();
        }
    }
}
