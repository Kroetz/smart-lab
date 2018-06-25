package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceInfo implements IAssistanceInfo {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;

    public AbstractAssistanceInfo(
            String assistanceId,
            Set<String> assistanceAliases) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceId;
    }

    @Override
    public Set<String> getAssistanceAliases() {
        return this.assistanceAliases;
    }

    // TODO: Possible to force inner class for configuration?
}
