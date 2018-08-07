package de.qaware.smartlab.assistance.assistances.controllable.generic;

import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceCommandSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceConfigurationSupplier;
import de.qaware.smartlab.assistance.assistances.generic.IAssistanceIdSupplier;
import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceControllable extends IAssistanceIdSupplier, IAssistanceCommandSupplier, IAssistanceConfigurationSupplier {

    void begin(IActionService actionService, IAssistanceContext context);
    void end(IActionService actionService, IAssistanceContext context);
    void update(IActionService actionService, IAssistanceContext context);
}
