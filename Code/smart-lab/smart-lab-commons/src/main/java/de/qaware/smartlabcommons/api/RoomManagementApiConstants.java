package de.qaware.smartlabcommons.api;

public class RoomManagementApiConstants {

    public static final String MAPPING_BASE = "/smart-lab/api/room";
    public static final String MAPPING_GET_ROOMS = "";
    public static final String MAPPING_GET_ROOM = "/{roomId}";
    public static final String MAPPING_CREATE_ROOM = "";
    public static final String MAPPING_DELETE_ROOM = "/{roomId}";
    public static final String MAPPING_GET_MEETINGS_IN_ROOM = "/{roomId}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = "/{roomId}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{roomId}/extend-current-meeting";
    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = "/{roomId}/current-meeting-status-page";
}
