package de.qaware.smartlabtrigger.provider.generic;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import de.qaware.smartlab.core.data.generic.IEntity;
import de.qaware.smartlab.core.data.job.IJobInfo;
import de.qaware.smartlab.core.data.meeting.IMeeting;
import de.qaware.smartlab.core.data.meeting.MeetingId;
import de.qaware.smartlab.core.exception.TriggerProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Collections.emptySet;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.stream.Collectors.toSet;

@Slf4j
public abstract class AbstractTriggerProvider implements ITriggerProvider, CommandLineRunner {

    private final Duration checkInterval;
    private boolean providingTriggers;
    protected Instant currentCheck;
    /**
     * Contains all current trigger candidates and if they have already been triggered.
     */
    private final BiMap<MeetingId, IJobInfo> triggerJobInfoCache;
    private final Function<IMeeting, IJobInfo> triggerCall;
    private final String triggerName;

    public AbstractTriggerProvider(
            Duration checkInterval,
            Function<IMeeting, IJobInfo> triggerCall,
            String triggerName) {
        this.checkInterval = checkInterval;
        this.triggerCall = triggerCall;
        this.triggerJobInfoCache = Maps.synchronizedBiMap(HashBiMap.create());
        this.triggerName = triggerName;
    }

    @Override
    public void run(String... args) {
        start();
    }

    public void start() {
        if(this.providingTriggers) throw new IllegalStateException("Already providing triggers");
        this.providingTriggers = true;
        ExecutorService executor = newSingleThreadExecutor();
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

    @Override
    public void updateTriggerJob(IJobInfo newTriggerJobInfo) {
        BiMap<IJobInfo, MeetingId> inversedCache = this.triggerJobInfoCache.inverse();
        Set<IJobInfo> oldTriggerJobInfos = inversedCache.keySet();
        synchronized(this.triggerJobInfoCache) {  // Manual synchronization is necessary (See https://stackoverflow.com/questions/44050911/synchronization-for-inverse-view-of-synchronized-bimap)
            Set<IJobInfo> triggerJobInfosWithSameId = oldTriggerJobInfos.stream()
                    .filter(triggerJobInfo -> triggerJobInfo.getId().equals(newTriggerJobInfo.getId()))
                    .collect(toSet());
            if(triggerJobInfosWithSameId.size() > 1) throw new IllegalStateException("The IDs of all job info objects in the cache must be unique");
            Optional<IJobInfo> oldTriggerJobInfoOptional = triggerJobInfosWithSameId.stream().findFirst();
            // TODO: Simpler with "ifPresentOrElse" in Java 9 (See https://stackoverflow.com/questions/23773024/functional-style-of-java-8s-optional-ifpresent-and-if-not-present)
            if(oldTriggerJobInfoOptional.isPresent()) {
                IJobInfo oldTriggerJobInfo = oldTriggerJobInfoOptional.get();
                MeetingId meetingId = inversedCache.get(oldTriggerJobInfo);
                this.triggerJobInfoCache.put(meetingId, newTriggerJobInfo);
                log.info("Updated trigger job info {} to {}", oldTriggerJobInfo, newTriggerJobInfo);
            }
            else {
                log.warn("Ignoring update of trigger job info {} because it is not relevant anymore", newTriggerJobInfo);
            }
        }
    }

    private synchronized void provideTriggers() {
        log.info("Providing trigger \"{}\" for all suitable meetings", this.triggerName);
        this.currentCheck = Instant.now();
        Set<IMeeting> triggerCandidates = getTriggerCandidates();
        cleanTriggerJobInfoCache(triggerCandidates);
        triggerCandidates = removeAlreadyTriggered(triggerCandidates);
        log.info("{} meeting(s) suitable for trigger \"{}\"", triggerCandidates.size(), this.triggerName);
        for(IMeeting triggerCandidate : triggerCandidates) {
            provideTrigger(triggerCandidate);
        }
    }

    private Set<IMeeting> removeAlreadyTriggered(Set<IMeeting> triggerCandidates) {
        return triggerCandidates.stream()
                .filter(this::isNotYetTriggered)
                .collect(toSet());
    }

    private Set<IMeeting> getTriggerCandidates() {
        try {
            return findTriggerCandidates();
        }
        catch(Exception e) {
            log.error("Could not get trigger candidates", e);
            return emptySet();
        }
    }

    protected abstract Set<IMeeting> findTriggerCandidates();

    private void provideTrigger(IMeeting triggerCandidate) {
        if(!isAlreadyTriggered(triggerCandidate)) {
            try {
                log.info("Providing trigger \"{}\" for meeting {}", this.triggerName, triggerCandidate);
                IJobInfo triggerJobInfo = triggerCall.apply(triggerCandidate);
                this.triggerJobInfoCache.put(triggerCandidate.getId(), triggerJobInfo);
                log.info("Successfully provided trigger \"{}\" for meeting {}", this.triggerName, triggerCandidate);
            }
            catch(Exception e) {
                log.error("Providing trigger \"{}\" for meeting {} failed", this.triggerName, triggerCandidate, e);
            }
        }
    }

    private boolean isAlreadyTriggered(IMeeting triggerCandidate) {
        if(this.triggerJobInfoCache.containsKey(triggerCandidate.getId())) {
            IJobInfo triggerJobInfo = this.triggerJobInfoCache.get(triggerCandidate.getId());
            switch (triggerJobInfo.getStatus()) {
                case PENDING:
                case PROCESSING:
                case FINISHED:
                    return true;
                case FAILED:
                    return false;
                default:
                    throw new IllegalStateException("The trigger job status must be valid");
            }
        }
        return false;
    }

    private boolean isNotYetTriggered(IMeeting triggerCandidate) {
        return !isAlreadyTriggered(triggerCandidate);
    }

    private void cleanTriggerJobInfoCache(Set<IMeeting> newTriggerCandidates) {
        log.info("Cleaning up trigger job info cache");
        Set<MeetingId> newTriggerCandidateIds = newTriggerCandidates.stream()
                .map(IEntity::getId)
                .collect(toSet());
        synchronized(this.triggerJobInfoCache) {  // Manual synchronization is necessary (See https://stackoverflow.com/questions/44050911/synchronization-for-inverse-view-of-synchronized-bimap)
            Set<MeetingId> cacheItemsToRemove = new HashSet<>();
            for (MeetingId meetingId : this.triggerJobInfoCache.keySet()) {
                if (!newTriggerCandidateIds.contains(meetingId)) {
                    cacheItemsToRemove.add(meetingId);
                }
            }
            log.info("Found {} cache item(s) that can be cleared", cacheItemsToRemove.size());
            /*
             * Do not manipulate the map while iterating over its key set! Perform manipulations outside of the loop
             * or otherwise undefined behavior may occur.
             */
            for(MeetingId cacheItemToRemove : cacheItemsToRemove) {
                log.info("Clearing trigger job info of meeting {}", cacheItemToRemove);
                this.triggerJobInfoCache.remove(cacheItemToRemove);
            }
        }
    }
}
