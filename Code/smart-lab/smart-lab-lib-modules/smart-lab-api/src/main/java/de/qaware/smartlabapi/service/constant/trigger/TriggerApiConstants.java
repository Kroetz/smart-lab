package de.qaware.smartlabapi.service.constant.trigger;

public class TriggerApiConstants {

    public static final String FEIGN_CLIENT_NAME = "trigger";

    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_WORKGROUP_ID = "workgroupId";
    public static final String PARAMETER_NAME_CALLBACK_URL = "callback-url";

    private static final String PATH_FRAGMENT_START_CURRENT_MEETING_BY_ROOM_ID = "/start-current-meeting/room/";
    private static final String PATH_FRAGMENT_STOP_CURRENT_MEETING_BY_ROOM_ID = "/stop-current-meeting/room/";

    public static final String MAPPING_BASE = "/smart-lab/api/trigger";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_ROOM_ID = "/set-up-current-meeting/room/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_SET_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/set-up-current-meeting/workgroup/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_ROOM_ID = "/clean-up-current-meeting/room/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_CLEAN_UP_CURRENT_MEETING_BY_WORKGROUP_ID = "/clean-up-current-meeting/workgroup/{" + PARAMETER_NAME_WORKGROUP_ID + "}/";
    public static final String MAPPING_START_CURRENT_MEETING_BY_ROOM_ID = PATH_FRAGMENT_START_CURRENT_MEETING_BY_ROOM_ID + "{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_START_CURRENT_MEETING_BY_WORKGROUP_ID = "/start-current-meeting/workgroup/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_ROOM_ID = PATH_FRAGMENT_STOP_CURRENT_MEETING_BY_ROOM_ID + "{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_STOP_CURRENT_MEETING_BY_WORKGROUP_ID = "/stop-current-meeting/workgroup/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_GET_BASE_URL = "/base-url";

    public static final String URL_TEMPLATE_START_CURRENT_MEETING_BY_ROOM_ID = MAPPING_BASE + PATH_FRAGMENT_START_CURRENT_MEETING_BY_ROOM_ID + "%s";
    public static final String URL_TEMPLATE_STOP_CURRENT_MEETING_BY_ROOM_ID = MAPPING_BASE + PATH_FRAGMENT_STOP_CURRENT_MEETING_BY_ROOM_ID + "%s";
}
