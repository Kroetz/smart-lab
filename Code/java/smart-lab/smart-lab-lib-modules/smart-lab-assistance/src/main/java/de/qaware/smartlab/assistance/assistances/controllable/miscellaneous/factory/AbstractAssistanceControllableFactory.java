package de.qaware.smartlab.assistance.assistances.controllable.miscellaneous.factory;

import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllableFactory implements IAssistanceControllableFactory {

    protected final IAssistanceInfo assistanceInfo;

    protected AbstractAssistanceControllableFactory(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    @Override
    public String getAssociatedAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    @Override
    public Set<String> getAssociatedAssistanceIdAliases() {
        return this.assistanceInfo.getAssistanceIdAliases();
    }
}
