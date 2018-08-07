package de.qaware.smartlabtrigger.service.business;

import de.qaware.smartlabapi.service.connector.job.IJobManagementService;
import de.qaware.smartlabassistance.assistance.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.context.IAssistanceContext;
import de.qaware.smartlabcore.data.context.IAssistanceContextFactory;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.location.ILocation;
import de.qaware.smartlabcore.exception.InsufficientContextException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static de.qaware.smartlabcore.miscellaneous.Constants.VOID;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

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
            Set<CompletableFuture<Void>> assistanceTasks = new HashSet<>();
            for(IAssistanceConfiguration config : configs) {
                /*
                 * TODO: Rather than triggering the assistances here asynchronously it would be a better and cleaner way when the whole assistance service worked asynchronously (just like the trigger service).
                 * The challenge here is that the trigger service must not mark his own async job as finished until all
                 * assistances that he triggers (and that are also processed asynchronously by the assistance service)
                 * have been processed themselves. So there must be some kind of waiting mechanism.
                 * The current workaround is:
                 *  - Execute the triggers asynchronously
                 *  - Create CompletableFuture objects from the async tasks
                 *  - Wait for all CompletableFuture objects to finish and finally mark the trigger service job as finished
                 */
                assistanceTasks.add(supplyAsync(() -> {
                    try {
                        triggerAssistance(config, meeting, triggerReaction, jobId);
                    }
                    catch(Exception e) {
                        log.error("Could not process assistance with ID \"{}\"", config.getAssistanceId(), e);
                    }
                    return VOID;
                }));
            }
            allOf(assistanceTasks.stream().toArray(CompletableFuture[]::new));
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
        IAssistanceTriggerable assistance = this.assistanceTriggerableResolver.resolve(assistanceId);
        IAssistanceContext context = this.contextFactory.of(config, meeting);
        log.info("Calling assistance service for the trigger reaction of assistance \"{}\" at location with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new));
        triggerReaction.accept(context, assistance);
        log.info("Called assistance service for the trigger reaction of assistance \"{}\" at location with ID \"{}\"",
                assistance.getAssistanceId(),
                context.getLocation().map(ILocation::getId).orElseThrow(InsufficientContextException::new));
    }
}
