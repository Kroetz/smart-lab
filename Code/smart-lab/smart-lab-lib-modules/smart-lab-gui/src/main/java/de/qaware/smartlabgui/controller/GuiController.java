package de.qaware.smartlabgui.controller;

import de.qaware.smartlabapi.GuiApiConstants;
import de.qaware.smartlabcore.concurrency.ThreadContext;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabcore.miscellaneous.StringUtils;
import de.qaware.smartlabgui.business.IGuiBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
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
    public String getCurrentMeetingStatusPage(@PathVariable(GuiApiConstants.PARAMETER_NAME_ROOM_ID) String roomId, Model model) {
        return this.guiBusinessLogic.getCurrentMeetingStatusPage(RoomId.of(roomId), model);
    }

    @GetMapping(GuiApiConstants.MAPPING_GET_CURRENT_MEETING_AGENDA_PAGE)
    public String getCurrentMeetingAgendaPage(@PathVariable(GuiApiConstants.PARAMETER_NAME_ROOM_ID) String roomId, Model model) {
        return this.guiBusinessLogic.getCurrentMeetingAgendaPage(RoomId.of(roomId), model);
    }

    @RestController
    @RequestMapping(GuiApiConstants.MAPPING_BASE)
    @Slf4j
    public static class BaseUrlGetter {

        private final URL fallbackBaseUrl;

        public BaseUrlGetter(
                // TODO: String literal
                @Qualifier("guiServiceFallbackBaseUrl") URL fallbackBaseUrl) {
            this.fallbackBaseUrl = fallbackBaseUrl;
        }

        @GetMapping(GuiApiConstants.MAPPING_GET_BASE_URL)
        public ResponseEntity<URL> getBaseUrl() {
            // TODO: Exception
            URL associatedRequestUrl = ThreadContext.get()
                    .getAssociatedRequestUrl()
                    .orElse(this.fallbackBaseUrl);
            URL baseUrl;
            try {
                baseUrl = new URL(
                        associatedRequestUrl.getProtocol(),
                        associatedRequestUrl.getHost(),
                        associatedRequestUrl.getPort(),
                        StringUtils.EMPTY);
            } catch (MalformedURLException e) {
                log.error("Could not build base URL of the request this thread is associated with", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok(baseUrl);
        }
    }
}
