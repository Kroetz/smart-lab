package de.qaware.smartlabmeetingcleanuptriggermock;

import de.qaware.smartlabcommons.api.service.meeting.IMeetingManagementService;
import de.qaware.smartlabcommons.api.service.trigger.ITriggerService;
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

    public void triggerMeetingCleanUps() {

        log.info("Triggering possible meeting clean ups");

        currentCheck = Instant.now();

        /*// TODO: Alle meetings durchgehen und schauen, ob eines, das zum Zeitpunkt des letzten checks nockh lief, jetzt vorbei ist
        String getMeetingsUrl = MeetingController.URL_TEMPLATE_GET_MEETINGS;
        List<> results = restTemplate.getForObject(url, User.class);*/

        List<IMeeting> meetingsAboutToEnd = meetingManagementService.findAll().stream()
                .filter(this::isAboutToEnd)
                .collect(Collectors.toList());

        for(IMeeting meetingAboutToEnd : meetingsAboutToEnd) {
            triggerMeetingCleanUp(meetingAboutToEnd);
        }

        // User results = restTemplate.getForObject(url, User.class);
        // return CompletableFuture.completedFuture(results);

        lastCheck = currentCheck;
    }

    private void triggerMeetingCleanUp(IMeeting endedMeeting) {
        // String url = TriggerController.MAPPING_BASE + String.format(TriggerController.URL_TEMPLATE_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID, endedMeeting.getRoomId());
        triggerService.cleanUpCurrentMeetingByRoomId(endedMeeting.getRoomId());
    }

    private boolean isAboutToEnd(IMeeting meeting) {
        return meeting.getEnd().minusSeconds(5).isBefore(currentCheck) && meeting.getEnd().minusSeconds(5).isAfter(lastCheck);
    }
}
