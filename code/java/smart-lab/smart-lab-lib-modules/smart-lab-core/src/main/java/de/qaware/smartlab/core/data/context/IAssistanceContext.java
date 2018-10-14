package de.qaware.smartlab.core.data.context;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
import de.qaware.smartlab.core.constant.Miscellaneous;

import java.util.Set;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Miscellaneous.JSON_TYPE_PROPERTY_NAME)
public interface IAssistanceContext {

    IAssistanceConfiguration getAssistanceConfiguration();
    IEvent getEvent() throws InsufficientContextException;
    IWorkgroup getWorkgroup() throws InsufficientContextException;
    Set<IPerson> getPersons() throws InsufficientContextException;
    ILocation getLocation() throws InsufficientContextException;
}
