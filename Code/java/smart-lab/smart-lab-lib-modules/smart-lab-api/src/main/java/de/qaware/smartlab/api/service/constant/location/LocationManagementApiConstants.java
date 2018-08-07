package de.qaware.smartlab.api.service.constant.location;

public abstract class LocationManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-location-management-microservice";

    public static final String PARAMETER_NAME_LOCATION_ID = "locationId";
    public static final String PARAMETER_NAME_LOCATION_IDS = "locationIds";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";

    private static final String PATH_FRAGMENT_LOCATION = "/location";
    private static final String PATH_FRAGMENT_LOCATIONS = "/locations";
    private static final String PATH_FRAGMENT_EXTEND_CURRENT_EVENT = "/extend-current-event";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_LOCATIONS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_LOCATIONS + "";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_LOCATION + "";
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_LOCATIONS + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}";
    public static final String MAPPING_GET_EVENTS_AT_LOCATION = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}/events";
    public static final String MAPPING_GET_CURRENT_EVENT = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}/current-event";
    public static final String MAPPING_EXTEND_CURRENT_EVENT = PATH_FRAGMENT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}" + PATH_FRAGMENT_EXTEND_CURRENT_EVENT;
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_LOCATION + "/base-url";

    public static final String URL_TEMPLATE_EXTEND_CURRENT_EVENT = MAPPING_BASE + PATH_FRAGMENT_LOCATION + "/%s" + PATH_FRAGMENT_EXTEND_CURRENT_EVENT + "?" + PARAMETER_NAME_EXTENSION_IN_MINUTES + "=%d";
}
