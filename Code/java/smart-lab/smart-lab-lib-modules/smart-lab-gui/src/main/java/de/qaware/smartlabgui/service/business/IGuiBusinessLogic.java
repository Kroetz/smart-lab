package de.qaware.smartlabgui.service.business;

import de.qaware.smartlabcore.data.room.RoomId;
import org.springframework.ui.Model;

public interface IGuiBusinessLogic {

    String getCurrentMeetingStatusPage(RoomId roomId, Model model);
    String getCurrentMeetingAgendaPage(RoomId roomId, Model model);
}
