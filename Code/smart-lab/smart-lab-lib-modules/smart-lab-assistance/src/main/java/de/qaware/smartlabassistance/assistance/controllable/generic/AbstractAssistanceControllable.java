package de.qaware.smartlabassistance.assistance.controllable.generic;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllable implements IAssistanceControllable {

    protected final IAssistanceInfo assistanceInfo;

    public AbstractAssistanceControllable(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    @Override
    public Set<String> getAssistanceAliases() {
        return this.assistanceInfo.getAssistanceAliases();
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return this.assistanceInfo.createConfiguration(configProperties);
    }
}
