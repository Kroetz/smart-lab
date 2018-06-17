package de.qaware.smartlabapi;

public class MeetingManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "meeting-management";

    public static final String PARAMETER_NAME_MEETING_ID = "meetingId";
    public static final String PARAMETER_NAME_MEETING_IDS = "meetingIds";
    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_SHORTENING_IN_MINUTES = "shortening-in-minutes";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String PARAMETER_NAME_SHIFT_IN_MINUTES = "shift-in-minutes";

    private static final String PATH_FRAGMENT_MEETING = "/meeting";
    private static final String PATH_FRAGMENT_MEETINGS = "/meetings";
    private static final String PATH_FRAGMENT_IN_ROOM = "/in/room";
    private static final String PATH_FRAGMENT_SHORTEN = "/shorten";
    private static final String PATH_FRAGMENT_EXTEND = "/extend";
    private static final String PATH_FRAGMENT_SHIFT = "/shift";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_MEETINGS + "/all";
    public static final String MAPPING_FIND_ALL_BY_ROOM_ID = PATH_FRAGMENT_MEETINGS + "/all" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_MEETINGS + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_CREATE = PATH_FRAGMENT_MEETING + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_SHORTEN_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_SHORTEN;
    public static final String MAPPING_EXTEND_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_EXTEND;
    public static final String MAPPING_SHIFT_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}" + PATH_FRAGMENT_SHIFT;
}
