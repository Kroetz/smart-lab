package de.qaware.smartlabcommons.api;

public class MeetingManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "meeting-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";
    public static final String PARAMETER_NAME_MEETING_ID = "meetingId";
    public static final String PARAMETER_NAME_SHORTENING_IN_MINUTES = "shortening-in-minutes";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String PARAMETER_NAME_SHIFT_IN_MINUTES = "shift-in-minutes";
    public static final String MAPPING_BASE = "/smart-lab/api/meeting";
    public static final String MAPPING_FIND_ALL = "";
    public static final String MAPPING_FIND_ONE = "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_CREATE = "";
    public static final String MAPPING_DELETE = "/{" + PARAMETER_NAME_MEETING_ID + "}";
    public static final String MAPPING_SHORTEN_MEETING = "/{" + PARAMETER_NAME_MEETING_ID + "}/shorten";
    public static final String MAPPING_EXTEND_MEETING = "/{" + PARAMETER_NAME_MEETING_ID + "}/extend";
    public static final String MAPPING_SHIFT_MEETING = "/{" + PARAMETER_NAME_MEETING_ID + "}/shift";
}
