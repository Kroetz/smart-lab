package de.qaware.smartlabapi.service.connector.assistance;

import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceService {

    void beginAssistance(String assistanceId, IAssistanceContext context);
    void endAssistance(String assistanceId, IAssistanceContext context);
    void updateAssistance(String assistanceId, IAssistanceContext context);
}
