package de.qaware.smartlabcommons.api;

public class RoomManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "room-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";
    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String MAPPING_BASE = "/smart-lab/api/room";
    public static final String MAPPING_FIND_ALL = "";
    public static final String MAPPING_FIND_ONE = "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_CREATE = "";
    public static final String MAPPING_DELETE = "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_GET_MEETINGS_IN_ROOM = "/{" + PARAMETER_NAME_ROOM_ID + "}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting";
    public static final String PATH_FRAGMENT_EXTEND_CURRENT_MEETING = "/extend-current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING;
    public static final String URL_TEMPLATE_EXTEND_CURRENT_MEETING = MAPPING_BASE + "/%s" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING + "?" + PARAMETER_NAME_EXTENSION_IN_MINUTES + "=%d";
    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting-status-page";
}
