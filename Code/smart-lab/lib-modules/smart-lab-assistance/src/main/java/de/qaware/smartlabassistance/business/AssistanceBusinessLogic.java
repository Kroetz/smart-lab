package de.qaware.smartlabassistance.business;

import de.qaware.smartlabcommons.api.internal.service.action.IActionService;
import de.qaware.smartlabcommons.data.assistance.IAssistanceExecutable;
import de.qaware.smartlabcommons.data.context.IContext;
import de.qaware.smartlabcommons.data.generic.IResolver;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcommons.exception.InsufficientContextException;
import de.qaware.smartlabcommons.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class AssistanceBusinessLogic implements IAssistanceBusinessLogic {

    private final IActionService actionService;
    private final IResolver<String, IAssistanceExecutable> assistanceExecutableResolver;

    public AssistanceBusinessLogic(
            IActionService actionService,
            IResolver<String, IAssistanceExecutable> assistanceExecutableResolver) {
        this.actionService = actionService;
        this.assistanceExecutableResolver = assistanceExecutableResolver;
    }

    private void executeAssistanceStage(String assistanceId, Consumer<IAssistanceExecutable> assistanceStageExecution) {
        IAssistanceExecutable assistance = this.assistanceExecutableResolver
                .resolve(assistanceId)
                .orElseThrow(UnknownAssistanceException::new);
        assistanceStageExecution.accept(assistance);
    }

    public void beginAssistance(String assistanceId, final IContext context) {
        log.info("Executing begin stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        executeAssistanceStage(assistanceId, assistance -> assistance.begin(this.actionService, context));
        log.info("Executed begin stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void endAssistance(String assistanceId, IContext context) {
        log.info("Executing end stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
        executeAssistanceStage(assistanceId, assistance -> assistance.end(this.actionService, context));
        log.info("Executed end stage of assistance (ID: \"{}\") in room with ID \"{}\"",
                assistanceId,
                context.getRoom().map(IRoom::getName).orElseThrow(InsufficientContextException::new));
    }

    public void updateAssistance(String assistanceId, IContext context) {

        // TODO: Implementation
    }
}
