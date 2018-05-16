package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.data.context.IContext;

public interface IAssistanceBusinessLogic {

    void beginAssistance(String assistanceId, IContext context);
    void endAssistance(String assistanceId, IContext context);
    void updateAssistance(String assistanceId, IContext context);
}
