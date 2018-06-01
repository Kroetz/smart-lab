package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabapi.service.action.IActionService;
import de.qaware.smartlabcommons.data.assistance.IAssistance;
import de.qaware.smartlabcommons.data.context.IContext;

public interface IAssistanceExecutable extends IAssistance {

    void begin(IActionService actionService, IContext context);
    void end(IActionService actionService, IContext context);
    void update(IActionService actionService, IContext context);
}
