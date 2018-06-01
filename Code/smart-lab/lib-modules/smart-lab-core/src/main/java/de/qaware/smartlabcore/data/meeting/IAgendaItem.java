package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabcore.miscellaneous.Constants;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IAgendaItem {

    String getText();
}
