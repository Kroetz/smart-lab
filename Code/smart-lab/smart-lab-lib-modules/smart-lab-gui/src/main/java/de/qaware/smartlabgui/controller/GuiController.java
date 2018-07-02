package de.qaware.smartlabgui.controller;

import de.qaware.smartlabapi.GuiApiConstants;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.generic.controller.AbstractSmartLabController;
import de.qaware.smartlabgui.business.IGuiBusinessLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
