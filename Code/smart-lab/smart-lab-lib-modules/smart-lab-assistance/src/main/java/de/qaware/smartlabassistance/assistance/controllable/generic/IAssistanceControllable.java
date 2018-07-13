package de.qaware.smartlabassistance.assistance.controllable.generic;

import de.qaware.smartlabapi.service.connector.action.IActionService;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceControllable extends IAssistanceInfo {

    void begin(IActionService actionService, IAssistanceContext context);
    void end(IActionService actionService, IAssistanceContext context);
    void update(IActionService actionService, IAssistanceContext context);
}
