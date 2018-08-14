package de.qaware.smartlab.api.service.connector.assistance;

import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceService {

    void beginAssistance(IAssistanceContext context);
    void endAssistance(IAssistanceContext context);
    void duringAssistance(IAssistanceContext context);
}
