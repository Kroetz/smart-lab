package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IContext;

public interface IAssistanceControllable extends IAssistanceInfo {

    void begin(IActionService actionService, IContext context);
    void end(IActionService actionService, IContext context);
    void update(IActionService actionService, IContext context);
}
