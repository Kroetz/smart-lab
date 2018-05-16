package de.qaware.smartlabcommons.api.service.assistance;

import de.qaware.smartlabcommons.data.context.IContext;

public interface IAssistanceService {

    void beginAssistance(String assistanceId, IContext context);
    void endAssistance(String assistanceId, IContext context);
    void updateAssistance(String assistanceId, IContext context);
}
