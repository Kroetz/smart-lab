package de.qaware.smartlabassistance.assistance.controllable;

import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllable implements IAssistanceControllable {

    protected final IAssistanceInfo assistanceInfo;

    public AbstractAssistanceControllable(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    public Set<String> getAssistanceAliases() {
        return this.assistanceInfo.getAssistanceAliases();
    }
}
