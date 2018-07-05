package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceControllable extends IAssistanceInfo {

    void begin(IActionService actionService, IAssistanceContext context);
    void end(IActionService actionService, IAssistanceContext context);
    void update(IActionService actionService, IAssistanceContext context);
}
