package de.qaware.smartlabcommons.api.service.trigger;

public interface ITriggerService {

    void setUpCurrentMeetingByRoomId(String roomId);
    void setUpCurrentMeetingByWorkgroupId(String workgroupId);

    void cleanUpCurrentMeetingByRoomId(String roomId);
    void cleanUpCurrentMeetingByWorkgroupId(String workgroupId);

    void startCurrentMeetingByRoomId(String roomId);
    void startCurrentMeetingByWorkgroupId(String workgroupId);

    void stopCurrentMeetingByRoomId(String roomId);
    void stopCurrentMeetingByWorkgroupId(String workgroupId);
}
