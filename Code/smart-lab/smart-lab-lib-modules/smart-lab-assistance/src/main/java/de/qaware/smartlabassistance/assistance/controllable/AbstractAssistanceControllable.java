package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabaction.action.executable.IActionExecutable;
import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.generic.IResolver;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllable implements IAssistanceControllable {

    protected final IAssistanceInfo assistanceInfo;
    protected final IResolver<String, IActionExecutable> actionResolver;

    public AbstractAssistanceControllable(
            IAssistanceInfo assistanceInfo,
            IResolver<String, IActionExecutable> actionResolver) {
        this.assistanceInfo = assistanceInfo;
        this.actionResolver = actionResolver;
    }

    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    public Set<String> getAssistanceAliases() {
        return this.assistanceInfo.getAssistanceAliases();
    }
}
