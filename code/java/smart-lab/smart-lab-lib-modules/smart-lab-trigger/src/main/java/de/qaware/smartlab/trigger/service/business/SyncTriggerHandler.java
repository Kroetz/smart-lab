package de.qaware.smartlab.trigger.service.business;

import de.qaware.smartlab.api.service.connector.job.IJobManagementService;
import de.qaware.smartlab.assistance.assistances.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlab.core.assistance.IAssistanceConfiguration;
import de.qaware.smartlab.core.data.context.IAssistanceContext;
import de.qaware.smartlab.core.data.context.IAssistanceContextFactory;
import de.qaware.smartlab.core.resolver.IResolver;
import de.qaware.smartlab.core.data.event.IEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static de.qaware.smartlab.core.constant.Miscellaneous.VOID;
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
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        this.jobManagementService.markJobAsProcessing(jobId);
        try {
            Set<IAssistanceConfiguration> configs = event.getAssistanceConfigurations();
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
                        triggerAssistance(config, event, triggerReaction, jobId);
                    }
                    catch(Exception e) {
                        log.error("Could not process assistance \"{}\"", config.getAssistanceId(), e);
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
            IEvent event,
            BiConsumer<IAssistanceContext, IAssistanceTriggerable> triggerReaction,
            Long jobId) {
        if(!event.getAssistanceConfigurations().contains(config)) {
            throw new IllegalStateException("The specified assistance configuration must be part of the specified event");
        }
        String assistanceId = config.getAssistanceId();
        log.info("Processing assistance \"{}\"", assistanceId);
        IAssistanceTriggerable assistance = this.assistanceTriggerableResolver.resolve(assistanceId);
        IAssistanceContext context = this.contextFactory.of(config, event);
        log.info("Calling assistance service for the trigger reaction of assistance \"{}\" at location \"{}\"",
                assistance.getAssistanceId(),
                context.getLocation().getId());
        triggerReaction.accept(context, assistance);
        log.info("Called assistance service for the trigger reaction of assistance \"{}\" at location \"{}\"",
                assistance.getAssistanceId(),
                context.getLocation().getId());
    }
}
