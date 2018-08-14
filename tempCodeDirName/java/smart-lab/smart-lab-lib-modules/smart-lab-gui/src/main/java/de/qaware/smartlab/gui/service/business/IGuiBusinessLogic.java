package de.qaware.smartlab.gui.service.business;

import de.qaware.smartlab.core.data.location.LocationId;
import org.springframework.ui.Model;

public interface IGuiBusinessLogic {

    String getCurrentEventStatusPage(LocationId locationId, Model model);
    String getCurrentEventAgendaPage(LocationId locationId, Model model);
}
