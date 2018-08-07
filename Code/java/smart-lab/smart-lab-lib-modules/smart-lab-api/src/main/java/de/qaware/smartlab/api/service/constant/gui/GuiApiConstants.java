package de.qaware.smartlab.api.service.constant.gui;

public abstract class GuiApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-gui-microservice";

    public static final String MAPPING_BASE = "/smart-lab/gui";

    public static final String PARAMETER_NAME_LOCATION_ID = "locationId";

    private static final String PATH_FRAGMENT_LOCATION = "/location";

    public static final String MAPPING_GET_CURRENT_EVENT_STATUS_PAGE = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}/current-event-status-page";
    public static final String MAPPING_GET_CURRENT_EVENT_AGENDA_PAGE = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}/current-event-agenda-page";
    public static final String MAPPING_GET_BASE_URL = "/base-url";

    public static final String URL_TEMPLATE_GET_CURRENT_EVENT_AGENDA_PAGE = MAPPING_BASE + PATH_FRAGMENT_LOCATION + "/%s/current-event-agenda-page";
}
