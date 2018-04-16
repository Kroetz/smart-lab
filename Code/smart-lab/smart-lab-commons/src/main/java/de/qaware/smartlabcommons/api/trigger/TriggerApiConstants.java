package de.qaware.smartlabcommons.api.trigger;

public class TriggerApiConstants {

    public static final String MAPPING_BASE = "/smart-lab/api/trigger";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID = "/set-up-current-meeting/room/{roomId}";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/set-up-current-meeting/workgroup/{workgroupId}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID = "/clean-up-current-meeting/room/{roomId}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/clean-up-current-meeting/workgroup/{workgroupId}/";
    public static final String MAPPING_START_CURRENT_MEETING_BY_ROOM_ID = "/start-current-meeting/room/{roomId}";
    public static final String MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID = "/start-current-meeting/workgroup/{workgroupId}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID = "/stop-current-meeting/room/{roomId}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID = "/stop-current-meeting/workgroup/{workgroupId}";
}
