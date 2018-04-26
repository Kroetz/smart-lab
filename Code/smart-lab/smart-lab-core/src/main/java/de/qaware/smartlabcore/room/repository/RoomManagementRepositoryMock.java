package de.qaware.smartlabcore.room.repository;

import de.qaware.smartlabcommons.api.client.IMeetingManagementApiClient;
import de.qaware.smartlabcommons.data.meeting.IMeeting;
import de.qaware.smartlabcommons.data.room.IRoom;
import de.qaware.smartlabcore.data.sample.provider.ISampleDataProvider;
import de.qaware.smartlabcore.generic.repository.AbstractEntityManagementRepositoryMock;
import de.qaware.smartlabcore.generic.result.ExtensionResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class RoomManagementRepositoryMock extends AbstractEntityManagementRepositoryMock<IRoom> implements IRoomManagementRepository {

    private final IMeetingManagementApiClient meetingManagementApiClient;

    public RoomManagementRepositoryMock(
            IMeetingManagementApiClient meetingManagementApiClient,
            ISampleDataProvider sampleDataProvider) {
        this.meetingManagementApiClient = meetingManagementApiClient;
        this.entities = new HashSet<>(sampleDataProvider.getRooms());
    }

    @Override
    public Optional<Set<IMeeting>> getMeetingsInRoom(String roomId) {
        if(exists(roomId)) {
            return Optional.of(meetingManagementApiClient.findAll().stream()
                    .filter(meeting -> meeting.getRoomId().equals(roomId))
                    .collect(Collectors.toSet()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<IMeeting> getCurrentMeeting(String roomId) {
        return getMeetingsInRoom(roomId)
                .map(meetings -> meetings.stream()
                    .filter(meeting -> meeting.getStart().isBefore(Instant.now()) && meeting.getEnd().isAfter(Instant.now()))
                    .findFirst())
                .orElse(Optional.empty());
    }

    @Override
    public ExtensionResult extendCurrentMeeting(String roomId, Duration extension) {
        try {
            return getCurrentMeeting(roomId)
                    .map(meeting -> ExtensionResult.fromHttpStatus(meetingManagementApiClient.extendMeeting(meeting.getId(), extension.toMinutes()).getStatusCode()))
                    .orElse(ExtensionResult.NOT_FOUND);
        }
        catch(FeignException e) {
            return ExtensionResult.fromHttpStatus(HttpStatus.valueOf(e.status()));
        }
    }
}
