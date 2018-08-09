package de.qaware.smartlab.assistance.service.business;

import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceBusinessLogic {

    void beginAssistance(IAssistanceContext context);
    void endAssistance(IAssistanceContext context);
    void duringAssistance(IAssistanceContext context);
}
