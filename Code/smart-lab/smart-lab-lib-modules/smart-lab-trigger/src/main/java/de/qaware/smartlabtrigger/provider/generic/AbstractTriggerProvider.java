package de.qaware.smartlabtrigger.provider.generic;

import de.qaware.smartlabcore.data.generic.IEntity;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import de.qaware.smartlabcore.data.meeting.MeetingId;
import de.qaware.smartlabcore.exception.TriggerProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractTriggerProvider implements CommandLineRunner {

    private final Duration checkInterval;
    private boolean providingTriggers;
    protected Instant currentCheck;
    // Contains all current trigger candidates and if they have already been triggered
    protected final Map<MeetingId, Boolean> triggerCandidateCache;
    private final Consumer<IMeeting> triggerCall;
    private final String triggerName;

    public AbstractTriggerProvider(
            Duration checkInterval,
            Consumer<IMeeting> triggerCall,
            String triggerName) {
        this.checkInterval = checkInterval;
        this.triggerCall = triggerCall;
        this.triggerCandidateCache = new HashMap<>();
        this.triggerName = triggerName;
    }

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        if(this.providingTriggers) throw new IllegalStateException("Already providing triggers");
        this.providingTriggers = true;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(this.providingTriggers) {
                provideTriggers();
                try {
                    TimeUnit.SECONDS.sleep(this.checkInterval.getSeconds());
                } catch (InterruptedException e) {
                    String errorMessage = String.format(
                            "Could not wait specified polling interval of %d seconds",
                            this.checkInterval.getSeconds());
                    log.error(errorMessage, e);
                    throw new TriggerProviderException(errorMessage, e);
                }
            }
        });
    }

    public void stop() {
        this.providingTriggers = false;
    }

    private synchronized void provideTriggers() {
        log.info("Providing trigger \"{}\" for all suitable meetings", this.triggerName);
        this.currentCheck = Instant.now();
        Set<IMeeting> triggerCandidates = getTriggerCandidates();
        updateTriggerCandidateCache(triggerCandidates);
        log.info("{} meeting(s) suitable for trigger \"{}\"", triggerCandidates.size(), this.triggerName);
        for(IMeeting triggerCandidate : triggerCandidates) {
            provideTrigger(triggerCandidate);
        }
    }

    protected abstract Set<IMeeting> getTriggerCandidates();

    private void provideTrigger(IMeeting triggerCandidate) {
        if(!isAlreadyTriggered(triggerCandidate)) {
            try {
                log.info("Providing trigger \"{}\" for meeting {}", this.triggerName, triggerCandidate);
                triggerCall.accept(triggerCandidate);
                this.triggerCandidateCache.put(triggerCandidate.getId(), true);
                log.info("Successfully provided trigger \"{}\" for meeting {}", this.triggerName, triggerCandidate);
            }
            catch(Exception e) {
                this.triggerCandidateCache.put(triggerCandidate.getId(), false);
                log.error("Providing trigger \"{}\" for meeting {} failed", this.triggerName, triggerCandidate, e);
            }
        }
    }

    private boolean isAlreadyTriggered(IMeeting triggerCandidate) {
        if(this.triggerCandidateCache.containsKey(triggerCandidate.getId())) {
            return this.triggerCandidateCache.get(triggerCandidate.getId());
        }
        return false;
    }

    private void updateTriggerCandidateCache(Set<IMeeting> newTriggerCandidates) {
        Set<MeetingId> currentMeetingIds = newTriggerCandidates.stream()
                .map(IEntity::getId)
                .collect(Collectors.toSet());
        for(MeetingId meetingId : this.triggerCandidateCache.keySet()) {
            if(!currentMeetingIds.contains(meetingId)) {
                this.triggerCandidateCache.remove(meetingId);
            }
        }
    }
}
