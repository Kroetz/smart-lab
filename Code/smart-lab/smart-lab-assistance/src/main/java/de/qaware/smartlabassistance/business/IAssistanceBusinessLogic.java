package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.result.BeginAssistanceResult;
import de.qaware.smartlabcommons.result.EndAssistanceResult;
import de.qaware.smartlabcommons.result.UpdateAssistanceResult;

public interface IAssistanceBusinessLogic {

    BeginAssistanceResult beginAssistance(String assistanceId, IContext context);
    EndAssistanceResult endAssistance(String assistanceId, IContext context);
    UpdateAssistanceResult updateAssistance(String assistanceId, IContext context);
}
