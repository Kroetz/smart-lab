package de.qaware.smartlabtrigger.service.business;

import de.qaware.smartlabapi.service.connector.job.IJobManagementService;
import de.qaware.smartlabassistance.assistance.triggerable.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.context.IAssistanceContextFactory;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.room.IRoom;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import de.qaware.smartlabcore.exception.UnknownAssistanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class SyncTriggerHandler implements ITriggerHandler {

    private final IAssistanceContextFactory contextFactory;
    private final IResolver<String, IAssistanceTriggerable> assistanceTriggerableResolver;
    private final IJobManagementService jobManagementService;

    public SyncTriggerHandler(
            IAssistanceContextFactory contextFactory,
            IResolver<String, IAssistanceTriggerable> assistanceTriggerableResolver,
            IJobManagementService jobManagementService) {
        this.contextFactory = contextFactory;
        this.assistanceTriggerableResolver = assistanceTriggerableResolver;
        this.jobManagementService = jobManagementService;
    }

    @Override
    public void triggerAssistances(
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.jobManagementService.markJobAsProcessing(jobId);
        try {
            Set<IAssistanceConfiguration> configs = meeting.getAssistanceConfigurations();
            for(IAssistanceConfiguration config : configs) {
                triggerAssistance(config, meeting, triggerReaction, jobId);
            }
            this.jobManagementService.markJobAsFinished(jobId);
        }
        catch(Exception e) {
            this.jobManagementService.markJobAsFailed(jobId, e.getMessage());
            log.error("Failed to execute effect of trigger", e);
        }
    }

    @Override
    public void triggerAssistance(
            IAssistanceConfiguration config,
            IMeeting meeting,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        if(!meeting.getAssistanceConfigurations().contains(config)) {
            throw new IllegalStateException("The specified assistance configuration must be part of the specified meeting");
        }
        String assistanceId = config.getAssistanceId();
        log.info("Processing assistance with ID \"{}\"", assistanceId);
        IAssistanceTriggerable assistance = this.assistanceTriggerableResolver.resolve(assistanceId).orElseThrow(UnknownAssistanceException::new);
        IAssistanceContext context = this.contextFactory.of(config, meeting);
        log.info("Calling assistance service for the trigger reaction of assistance \"{}\" in room with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
        triggerReaction.accept(context, assistance);
        log.info("Called assistance service for the trigger reaction of assistance \"{}\" in room with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
    }
}
