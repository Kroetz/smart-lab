package de.qaware.smartlabassistance.assistance.controllable.miscellaneous.tracker;

import de.qaware.smartlabassistance.assistance.controllable.generic.IAssistanceControllable;
import de.qaware.smartlabcore.data.context.IAssistanceContext;

public interface IAssistanceTracker {

    IAssistanceControllable track(IAssistanceContext context);
    void stopTracking(IAssistanceContext context);
    IAssistanceControllable getTracked(IAssistanceContext context);
    IAssistanceControllable updateTracked(IAssistanceContext context, IAssistanceControllable assistance);
}
