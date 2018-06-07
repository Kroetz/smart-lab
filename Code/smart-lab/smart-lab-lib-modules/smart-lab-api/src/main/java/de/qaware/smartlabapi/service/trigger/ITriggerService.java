package de.qaware.smartlabapi.service.trigger;

import de.qaware.smartlabcore.data.job.IJobInfo;

import java.net.URL;

public interface ITriggerService {

    IJobInfo setUpCurrentMeetingByRoomId(String roomId);
    IJobInfo setUpCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId);
    IJobInfo setUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    IJobInfo cleanUpCurrentMeetingByRoomId(String roomId);
    IJobInfo cleanUpCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    IJobInfo startCurrentMeetingByRoomId(String roomId);
    IJobInfo startCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId);
    IJobInfo startCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);

    IJobInfo stopCurrentMeetingByRoomId(String roomId);
    IJobInfo stopCurrentMeetingByRoomId(String roomId, URL callbackUrl);
    IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId);
    IJobInfo stopCurrentMeetingByWorkgroupId(String workgroupId, URL callbackUrl);
}
