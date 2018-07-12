package de.qaware.smartlabapi.service.connector.trigger;

import de.qaware.smartlabcore.data.job.IJobInfo;
import de.qaware.smartlabcore.data.room.RoomId;
import de.qaware.smartlabcore.data.workgroup.WorkgroupId;

import java.net.URL;

public interface ITriggerService {

    IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId);
    IJobInfo setUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl);
    IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo setUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId);
    IJobInfo cleanUpCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo cleanUpCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo startCurrentMeetingByRoomId(RoomId roomId);
    IJobInfo startCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl);
    IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo startCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);

    IJobInfo stopCurrentMeetingByRoomId(RoomId roomId);
    IJobInfo stopCurrentMeetingByRoomId(RoomId roomId, URL callbackUrl);
    IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId);
    IJobInfo stopCurrentMeetingByWorkgroupId(WorkgroupId workgroupId, URL callbackUrl);
}
