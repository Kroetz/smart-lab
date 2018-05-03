package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.api.service.assistance.IAssistanceService;
import de.qaware.smartlabcommons.data.context.IContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
@Slf4j
public abstract class AbstractAssistance implements IAssistance {

    protected final String assistanceId;
    protected final Set<String> assistanceAliases;
    protected final IAssistanceService assistanceService;

    public AbstractAssistance(
            String assistanceId,
            Set<String> assistanceAliases,
            IAssistanceService assistanceService) {
        this.assistanceId = assistanceId;
        this.assistanceAliases = assistanceAliases;
        this.assistanceService = assistanceService;
    }

    @Override
    public void triggerSetUpMeeting(IContext context) {

    }

    @Override
    public void triggerCleanUpMeeting(IContext context) {

    }

    @Override
    public void triggerStartMeeting(IContext context) {

    }

    @Override
    public void triggerStopMeeting(IContext context) {

    }
}
