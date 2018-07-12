package de.qaware.smartlabapi.service.constant.gui;

public abstract class GuiApiConstants {

    public static final String FEIGN_CLIENT_NAME = "gui";

    public static final String MAPPING_BASE = "/smart-lab/gui";

    public static final String PARAMETER_NAME_ROOM_ID = "roomId";

    private static final String PATH_FRAGMENT_ROOM = "/room";

    public static final String MAPPING_GET_CURRENT_MEETING_STATUS_PAGE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting-status-page";
    public static final String MAPPING_GET_CURRENT_MEETING_AGENDA_PAGE = PATH_FRAGMENT_ROOM + "/{" + PARAMETER_NAME_ROOM_ID + "}/current-meeting-agenda-page";
    public static final String MAPPING_GET_BASE_URL = "/base-url";

    public static final String URL_TEMPLATE_GET_CURRENT_MEETING_AGENDA_PAGE = MAPPING_BASE + PATH_FRAGMENT_ROOM + "/%s/current-meeting-agenda-page";
}
