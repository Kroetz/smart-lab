package de.qaware.smartlab.assistance.service.business;

import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceBusinessLogic {

    void beginAssistance(String assistanceId, IAssistanceContext context);
    void endAssistance(String assistanceId, IAssistanceContext context);
    void updateAssistance(String assistanceId, IAssistanceContext context);
}
