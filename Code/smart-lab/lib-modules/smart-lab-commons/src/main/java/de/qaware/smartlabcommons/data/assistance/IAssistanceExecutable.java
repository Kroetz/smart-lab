package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;
import de.qaware.smartlabcommons.data.context.IContext;

public interface IAssistanceExecutable extends IAssistance {

    void begin(IActionService actionService, IContext context);
    void end(IActionService actionService, IContext context);
    void update(IActionService actionService, IContext context);
}
