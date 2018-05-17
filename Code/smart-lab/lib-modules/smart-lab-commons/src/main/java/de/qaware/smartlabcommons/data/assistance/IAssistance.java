package de.qaware.smartlabcommons.data.assistance;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.action.IAssistanceStage;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.miscellaneous.Constants;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IAssistance {

    String getAssistanceId();
    Set<String> getAssistanceAliases();

    ITriggerEffect effectOfTriggerSetUpMeeting(final IContext context);
    ITriggerEffect effectOfTriggerCleanUpMeeting(final IContext context);
    ITriggerEffect effectOfTriggerStartMeeting(final IContext context);
    ITriggerEffect effectOfTriggerStopMeeting(final IContext context);

    IAssistanceStage actionsOfBeginStage(IContext context);
    IAssistanceStage actionsOfEndStage(IContext context);
    IAssistanceStage actionsOfUpdateStage(IContext context);
}
