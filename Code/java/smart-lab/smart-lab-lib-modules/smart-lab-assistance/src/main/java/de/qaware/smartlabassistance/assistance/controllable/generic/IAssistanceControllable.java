package de.qaware.smartlabassistance.assistance.controllable.generic;

import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceCommandSupplier;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceConfigurationSupplier;
import de.qaware.smartlabassistance.assistance.generic.IAssistanceIdSupplier;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceControllable extends IAssistanceIdSupplier, IAssistanceCommandSupplier, IAssistanceConfigurationSupplier {

    void begin(IActionService actionService, IAssistanceContext context);
    void end(IActionService actionService, IAssistanceContext context);
    void update(IActionService actionService, IAssistanceContext context);
}
