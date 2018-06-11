package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.assistance.IAssistance;
import de.qaware.smartlabcore.data.context.IContext;

public interface IAssistanceControllable extends IAssistance {

    void begin(IActionService actionService, IContext context);
    void end(IActionService actionService, IContext context);
    void update(IActionService actionService, IContext context);
}
