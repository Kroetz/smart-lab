package de.qaware.smartlabgui.service.business;

import de.qaware.smartlab.core.data.location.LocationId;
import org.springframework.ui.Model;

public interface IGuiBusinessLogic {

    String getCurrentMeetingStatusPage(LocationId locationId, Model model);
    String getCurrentMeetingAgendaPage(LocationId locationId, Model model);
}
