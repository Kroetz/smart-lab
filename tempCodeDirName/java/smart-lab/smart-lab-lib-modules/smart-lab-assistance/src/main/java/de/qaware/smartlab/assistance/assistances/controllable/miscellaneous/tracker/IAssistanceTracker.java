package de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.tracker;

import de.qaware.smartlab.assistance.assistances.controllable.generic.IAssistanceControllable;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.assistance.AssistanceTrackingException;

public interface IAssistanceTracker {

    IAssistanceControllable track(IAssistanceContext context) throws AssistanceTrackingException;
    void stopTracking(IAssistanceContext context) throws AssistanceTrackingException;
    IAssistanceControllable getTracked(IAssistanceContext context) throws AssistanceTrackingException;
    IAssistanceControllable updateTracked(IAssistanceContext context, IAssistanceControllable assistance) throws AssistanceTrackingException;
}
