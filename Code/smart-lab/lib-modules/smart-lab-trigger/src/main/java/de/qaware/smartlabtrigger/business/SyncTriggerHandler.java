package de.qaware.smartlabtrigger.business;

import de.qaware.smartlabapi.service.job.IJobManagementService;
import de.qaware.smartlabassistance.assistance.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.context.IContext;
import de.qaware.smartlabcore.data.context.IContextFactory;
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

    private final IContextFactory contextFactory;
    private final IResolver<String, IAssistanceTriggerable> assistanceTriggerableResolver;
    private final IJobManagementService jobManagementService;

    public SyncTriggerHandler(
            IContextFactory contextFactory,
            IResolver<String, IAssistanceTriggerable> assistanceTriggerableResolver,
            IJobManagementService jobManagementService) {
        this.contextFactory = contextFactory;
        this.assistanceTriggerableResolver = assistanceTriggerableResolver;
        this.jobManagementService = jobManagementService;
    }

    @Override
    public void triggerAssistances(
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            Long jobId) {
        this.jobManagementService.markJobAsProcessing(jobId);
        try {
            Set<String> assistanceIds = meeting.getAssistanceIds();
            for(String assistanceId : assistanceIds) {
                triggerAssistance(assistanceId, meeting, triggerReactionGetter, jobId);
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
            String assistanceId,
            IMeeting meeting,
            BiConsumer<IContext, IAssistanceTriggerable> triggerReactionGetter,
            Long jobId) {
        log.info("Processing assistance with ID \"{}\"", assistanceId);
        IAssistanceTriggerable assistance = this.assistanceTriggerableResolver.resolve(assistanceId).orElseThrow(UnknownAssistanceException::new);
        IContext context = this.contextFactory.ofMeetingAssistance(meeting, assistance);
        log.info("Calling assistance service for the trigger reaction of assistance \"{}\" in room with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
        triggerReactionGetter.accept(context, assistance);
        log.info("Called assistance service for the trigger reaction of assistance \"{}\" in room with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getRoom().map(IRoom::getId).orElseThrow(InsufficientContextException::new));
    }
}
