package de.qaware.smartlabcommons.data.assistance;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcommons.data.context.IContext;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AgendaShowing.class),
        @JsonSubTypes.Type(value = MinuteTaking.class),
        @JsonSubTypes.Type(value = RoomUnlocking.class),
})
public interface IAssistance {

    String getAssistanceId();
    void triggerSetUpMeeting(IContext context);
    void triggerCleanUpMeeting(IContext context);
    void triggerStartMeeting(IContext context);
    void triggerStopMeeting(IContext context);
}
