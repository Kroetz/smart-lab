package de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.tracker;

import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.core.data.context.IAssistanceContext;

public interface IAssistanceTracker {

    IAssistanceControllable track(IAssistanceContext context);
    void stopTracking(IAssistanceContext context);
    IAssistanceControllable getTracked(IAssistanceContext context);
    IAssistanceControllable updateTracked(IAssistanceContext context, IAssistanceControllable assistance);
}
