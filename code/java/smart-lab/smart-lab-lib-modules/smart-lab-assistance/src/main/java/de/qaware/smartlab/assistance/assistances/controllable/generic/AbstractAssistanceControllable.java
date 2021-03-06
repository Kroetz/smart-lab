package de.qaware.smartlab.assistance.assistances.controllable.generic;

import de.qaware.smartlab.api.service.connector.action.IActionService;
import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.exception.assistance.AssistanceException;
import de.qaware.smartlab.core.exception.context.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class AbstractAssistanceControllable implements IAssistanceControllable {

    protected final IAssistanceInfo assistanceInfo;

    protected AbstractAssistanceControllable(IAssistanceInfo assistanceInfo) {
        this.assistanceInfo = assistanceInfo;
    }

    @Override
    public String getAssistanceId() {
        return this.assistanceInfo.getAssistanceId();
    }

    @Override
    public Set<String> getAssistanceIdAliases() {
        return this.assistanceInfo.getAssistanceIdAliases();
    }

    @Override
    public String getAssistanceCommand() {
        return this.assistanceInfo.getAssistanceCommand();
    }

    @Override
    public Set<String> getAssistanceCommandAliases() {
        return this.assistanceInfo.getAssistanceCommandAliases();
    }

    protected <ConfigT extends IAssistanceConfiguration> ConfigT toSpecificConfigType(
            Class<ConfigT> targetConfigClass,
            IAssistanceConfiguration genericConfig) throws IllegalStateException {
        try {
            return targetConfigClass.cast(genericConfig);
        }
        catch(ClassCastException e) {
            String errorMessage = String.format(
                    "The assistance configuration %s must be of the type %s",
                    genericConfig.toString(),
                    targetConfigClass.getName());
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
    }

    @Override
    public void begin(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        log.info("Ignoring stage \"begin\" of assistance \"{}\" for event \"{}\" because it has no functionality",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public void end(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        log.info("Ignoring stage \"end\" of assistance \"{}\" for event \"{}\" because it has no functionality",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public void during(IActionService actionService, IAssistanceContext context) throws AssistanceException, InsufficientContextException {
        log.info("Ignoring stage \"during\" of assistance \"{}\" for event \"{}\" because it has no functionality",
                this.assistanceInfo.getAssistanceId(),
                context.getEvent().getId());
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return this.assistanceInfo.createConfiguration(configProperties);
    }
}
