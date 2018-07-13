package de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory;

import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllableFactory implements IAssistanceControllableFactory {

    protected final IAssistanceInfo assistanceInfo;

    public AbstractAssistanceControllableFactory(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    @Override
    public String getAssociatedAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    @Override
    public Set<String> getAssociatedAssistanceAliases() {
        return this.assistanceInfo.getAssistanceAliases();
    }
}
