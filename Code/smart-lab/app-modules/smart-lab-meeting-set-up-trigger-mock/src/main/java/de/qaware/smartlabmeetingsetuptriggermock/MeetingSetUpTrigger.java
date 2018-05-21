package de.qaware.smartlabmeetingsetuptriggermock;

import de.qaware.smartlabcommons.api.internal.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcommons.api.internal.service.trigger.ITriggerService;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MeetingSetUpTrigger implements CommandLineRunner {

    private final IMeetingManagementService meetingManagementService;
    private final ITriggerService triggerService;
    private final RestTemplate restTemplate;
    private Instant currentCheck = Instant.now();
    private Instant lastCheck = Instant.now().minus(Duration.ofDays(1));
    private boolean active = true;
    private long checkInterval = 5000L;

    public MeetingSetUpTrigger(
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
                triggerMeetingSetUps();
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

    public synchronized void triggerMeetingSetUps() {
        log.info("Triggering possible meeting set ups");
        Instant currentCheckBackup = currentCheck;
        currentCheck = Instant.now();
        try {
            List<IMeeting> justStartedMeetings = meetingManagementService.findAll().stream()
                    .filter(this::hasJustStarted)
                    .collect(Collectors.toList());
            for(IMeeting startedMeeting : justStartedMeetings) {
                try {
                    log.info("Triggering set up of meeting: " + startedMeeting.getId());
                    triggerMeetingSetUp(startedMeeting);
                    log.info("Successfully triggered set up of meeting: " + startedMeeting.getId());
                }
                catch(Exception e) {
                    log.error("Triggering set up of meeting {} failed", startedMeeting.getId(), e);
                    throw e;
                }
            }
            lastCheck = currentCheck;
            log.info("Triggered possible meeting set ups");
        }
        catch(Exception e) {
            currentCheck = currentCheckBackup;
            log.error("Triggering of possible meeting set ups failed");
        }
    }

    private void triggerMeetingSetUp(IMeeting startedMeeting) {
        triggerService.setUpCurrentMeetingByRoomId(startedMeeting.getRoomId());
    }

    private boolean hasJustStarted(IMeeting meeting) {
        return meeting.getStart().isBefore(currentCheck) && meeting.getStart().isAfter(lastCheck);
    }
}
