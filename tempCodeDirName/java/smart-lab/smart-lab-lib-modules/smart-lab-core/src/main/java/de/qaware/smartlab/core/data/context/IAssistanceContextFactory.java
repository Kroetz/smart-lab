package de.qaware.smartlab.core.data.context;

import de.qaware.smartlab.core.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.event.IEvent;
import de.qaware.smartlab.core.data.person.IPerson;
import de.qaware.smartlab.core.data.location.ILocation;
import de.qaware.smartlab.core.data.workgroup.IWorkgroup;

import java.util.Set;

public interface IAssistanceContextFactory {

    IAssistanceContext of(IAssistanceConfiguration config, IEvent event);
    IAssistanceContext of(IAssistanceConfiguration config, IWorkgroup workgroup);
    IAssistanceContext of(IAssistanceConfiguration config, Set<IPerson> persons);
    IAssistanceContext of(IAssistanceConfiguration config, ILocation location);
}
