package de.qaware.smartlabcommons.api;

public class RoomManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "room-management";

    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_ROOM_IDS = "roomIds";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";

    public static final String PATH_FRAGMENT_ROOM = "/room";
    public static final String PATH_FRAGMENT_ROOMS = "/rooms";
    public static final String PATH_FRAGMENT_EXTEND_CURRENT_MEETING = "/extend-current-meeting";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_ROOMS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_ROOMS + "";
    public static final String MAPPING_CREATE = PATH_FRAGMENT_ROOM + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_GET_MEETINGS_IN_ROOM = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING;
    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting-status-page";

    public static final String URL_TEMPLATE_EXTEND_CURRENT_MEETING = MAPPING_BASE + "/%s" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING + "?" + PARAMETER_NAME_EXTENSION_IN_MINUTES + "=%d";
}
