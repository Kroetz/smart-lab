package de.qaware.smartlab.core.data.context;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IAssistanceContext {

    IAssistanceConfiguration getAssistanceConfiguration();
    Optional<IEvent> getEvent();
    Optional<IWorkgroup> getWorkgroup();
    Optional<Set<IPerson>> getPersons();
    Optional<ILocation> getLocation();
}
