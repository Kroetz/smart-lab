package de.qaware.smartlabcommons.api;

public class MeetingManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "meeting-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";

    public static final String PARAMETER_NAME_MEETING_ID = "meetingId";
    public static final String PARAMETER_NAME_MEETING_IDS = "meetingIds";
    public static final String PARAMETER_NAME_SHORTENING_IN_MINUTES = "shortening-in-minutes";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String PARAMETER_NAME_SHIFT_IN_MINUTES = "shift-in-minutes";

    public static final String PATH_FRAGMENT_MEETING = "/meeting";
    public static final String PATH_FRAGMENT_MEETINGS = "/meetings";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_MEETINGS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_MEETINGS + "";
    public static final String MAPPING_CREATE = PATH_FRAGMENT_MEETING + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_SHORTEN_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}/shorten";
    public static final String MAPPING_EXTEND_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}/extend";
    public static final String MAPPING_SHIFT_MEETING = PATH_FRAGMENT_MEETING + "/{" + PARAMETER_NAME_MEETING_ID + "}/shift";
}
