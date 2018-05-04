package de.qaware.smartlabcommons.data.assistance;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    void triggerSetUpMeeting(IContext context);
    void triggerCleanUpMeeting(IContext context);
    void triggerStartMeeting(IContext context);
    void triggerStopMeeting(IContext context);
}
