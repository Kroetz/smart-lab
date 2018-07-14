package de.qaware.smartlabapi.service.constant.meeting;

public abstract class MeetingManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "meeting-management";

    public static final String PARAMETER_NAME_MEETING_ID = "meetingId";
    public static final String PARAMETER_NAME_MEETING_IDS = "meetingIds";
    public static final String PARAMETER_NAME_ROOM_ID = "roomId";
    public static final String PARAMETER_NAME_WORKGROUP_ID = "workgroupId";
    public static final String PARAMETER_NAME_SHORTENING_IN_MINUTES = "shortening-in-minutes";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String PARAMETER_NAME_SHIFT_IN_MINUTES = "shift-in-minutes";

    private static final String PATH_FRAGMENT_MEETING = "/meeting";
    private static final String PATH_FRAGMENT_MEETINGS = "/meetings";
    private static final String PATH_FRAGMENT_IN_ROOM = "/in/room";
    private static final String PATH_FRAGMENT_OF_WORKGROUP = "/of/workgroup";
    private static final String PATH_FRAGMENT_SHORTEN = "/shorten";
    private static final String PATH_FRAGMENT_EXTEND = "/extend";
    private static final String PATH_FRAGMENT_SHIFT = "/shift";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_MEETINGS + "/all";
    public static final String MAPPING_FIND_ALL_BY_ROOM_ID = PATH_FRAGMENT_MEETINGS + "/all" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_ALL_BY_WORKGROUP_ID = PATH_FRAGMENT_MEETINGS + "/all" + PATH_FRAGMENT_OF_WORKGROUP  + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_FIND_ALL_CURRENT = PATH_FRAGMENT_MEETINGS + "/all/current";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_MEETINGS;
    public static final String MAPPING_FIND_CURRENT_BY_ROOM_ID = PATH_FRAGMENT_MEETING + "/current" + PATH_FRAGMENT_IN_ROOM  + "/{" + PARAMETER_NAME_ROOM_ID + "}";
    public static final String MAPPING_FIND_CURRENT_BY_WORKGROUP_ID = PATH_FRAGMENT_MEETING + "/current" + PATH_FRAGMENT_OF_WORKGROUP  + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_MEETING;
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_MEETINGS;
    public static final String MAPPING_DELETE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_SHORTEN_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_SHORTEN;
    public static final String MAPPING_EXTEND_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_EXTEND;
    public static final String MAPPING_SHIFT_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}" + PATH_FRAGMENT_SHIFT;
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_MEETING + "/base-url";
}
