package de.qaware.smartlabapi;

public class GuiApiConstants {

    public static final String MAPPING_BASE = "/smart-lab/gui";

    public static final String PARAMETER_NAME_ROOM_ID = "roomId";

    private static final String PATH_FRAGMENT_ROOM = "/room";

    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting-status-page";
}
