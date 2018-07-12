package de.qaware.smartlabapi.service.constant.room;

public class RoomManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "room-management";

    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_ROOM_IDS = "roomIds";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";

    private static final String PATH_FRAGMENT_ROOM = "/room";
    private static final String PATH_FRAGMENT_ROOMS = "/rooms";
    private static final String PATH_FRAGMENT_EXTEND_CURRENT_MEETING = "/extend-current-meeting";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_ROOMS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_ROOMS + "";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_ROOM + "";
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_ROOMS + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_GET_MEETINGS_IN_ROOM = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING;
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_ROOM + "/base-url";

    public static final String URL_TEMPLATE_EXTEND_CURRENT_MEETING = MAPPING_BASE + PATH_FRAGMENT_ROOM + "/%s" + PATH_FRAGMENT_EXTEND_CURRENT_MEETING + "?" + PARAMETER_NAME_EXTENSION_IN_MINUTES + "=%d";
}
