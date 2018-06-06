package de.qaware.smartlabapi.service.trigger;

import java.net.URL;

public interface ITriggerService {

    void setUpCurrentMeetingByRoomId(String roomId);
    void setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    void setUpCurrentMeetingByWorkgroupId(String workgroupId);
    void setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    void cleanUpCurrentMeetingByRoomId(String roomId);
    void cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    void cleanUpCurrentMeetingByWorkgroupId(String workgroupId);
    void cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    void startCurrentMeetingByRoomId(String roomId);
    void startCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    void startCurrentMeetingByWorkgroupId(String workgroupId);
    void startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    void stopCurrentMeetingByRoomId(String roomId);
    void stopCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    void stopCurrentMeetingByWorkgroupId(String workgroupId);
    void stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);
}
