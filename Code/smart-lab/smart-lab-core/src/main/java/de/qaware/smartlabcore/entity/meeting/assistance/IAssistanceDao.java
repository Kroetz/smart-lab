package de.qaware.smartlabcore.entity.meeting.assistance;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AssistanceDao.class)
})
public interface IAssistanceDao {

    String getAssistance();
    Optional<IAssistance> toAssistance();
}
