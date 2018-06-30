package de.qaware.smartlabmeetingcleanuptriggermock;

import de.qaware.smartlabapi.service.meeting.IMeetingManagementService;
import de.qaware.smartlabapi.service.trigger.ITriggerService;
import de.qaware.smartlabcore.data.meeting.IMeeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeetingCleanUpTrigger implements CommandLineRunner {

    private final IMeetingManagementService meetingManagementService;
    private final ITriggerService triggerService;
    private final RestTemplate restTemplate;
    private Instant currentCheck = Instant.now();
    private Instant lastCheck = Instant.now().minus(Duration.ofDays(1));
    private boolean active = true;
    private long checkInterval = 5000L;

    public MeetingCleanUpTrigger(
            IMeetingManagementService meetingManagementService,
            ITriggerService triggerService,
            RestTemplateBuilder restTemplateBuilder) {
        this.meetingManagementService = meetingManagementService;
        this.triggerService = triggerService;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(active) {
                triggerMeetingCleanUps();
                try {
                    Thread.sleep(checkInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() {
        this.active = false;
    }

    public synchronized void triggerMeetingCleanUps() {
        Instant currentCheckBackup = currentCheck;
        currentCheck = Instant.now();
        try {
            Set<IMeeting> meetingsAboutToEnd = meetingManagementService.findAll().stream()
                    .filter(this::isAboutToEnd)
                    .collect(Collectors.toSet());
            for(IMeeting meetingAboutToEnd : meetingsAboutToEnd) {
                try {
                    log.info("Triggering clean up of meeting: " + meetingAboutToEnd.getId());
                    triggerMeetingCleanUp(meetingAboutToEnd);
                    log.info("Successfully triggered clean up of meeting: " + meetingAboutToEnd.getId());
                }
                catch(Exception e) {
                    log.error("Triggering clean up of meeting {} failed", meetingAboutToEnd.getId(), e);
                    throw e;
                }
            }
            lastCheck = currentCheck;
            log.info("Triggered possible meeting clean ups");
        }
        catch(Exception e) {
            currentCheck = currentCheckBackup;
            log.error("Triggering of possible meeting clean ups failed");
        }
    }

    private void triggerMeetingCleanUp(IMeeting endedMeeting) {
        triggerService.cleanUpCurrentMeetingByRoomId(endedMeeting.getRoomId());
    }

    private boolean isAboutToEnd(IMeeting meeting) {
        return meeting.getEnd().minusSeconds(5).isBefore(currentCheck) && meeting.getEnd().minusSeconds(5).isAfter(lastCheck);
    }
}
