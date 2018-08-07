package de.qaware.smartlab.api.service.constant.event;

public abstract class EventManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-event-management-microservice";

    public static final String PARAMETER_NAME_EVENT_ID = "eventId";
    public static final String PARAMETER_NAME_EVENT_IDS = "eventIds";
    public static final String PARAMETER_NAME_LOCATION_ID = "locationId";
    public static final String PARAMETER_NAME_WORKGROUP_ID = "workgroupId";
    public static final String PARAMETER_NAME_SHORTENING_IN_MINUTES = "shortening-in-minutes";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String PARAMETER_NAME_SHIFT_IN_MINUTES = "shift-in-minutes";

    private static final String PATH_FRAGMENT_EVENT = "/event";
    private static final String PATH_FRAGMENT_EVENTS = "/events";
    private static final String PATH_FRAGMENT_AT_LOCATION = "/at/location";
    private static final String PATH_FRAGMENT_OF_WORKGROUP = "/of/workgroup";
    private static final String PATH_FRAGMENT_SHORTEN = "/shorten";
    private static final String PATH_FRAGMENT_EXTEND = "/extend";
    private static final String PATH_FRAGMENT_SHIFT = "/shift";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_EVENTS + "/all";
    public static final String MAPPING_FIND_ALL_BY_LOCATION_ID = PATH_FRAGMENT_EVENTS + "/all" + PATH_FRAGMENT_AT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}";
    public static final String MAPPING_FIND_ALL_BY_WORKGROUP_ID = PATH_FRAGMENT_EVENTS + "/all" + PATH_FRAGMENT_OF_WORKGROUP  + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_FIND_ALL_CURRENT = PATH_FRAGMENT_EVENTS + "/all/current";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_EVENT + "/{" + PARAMETER_NAME_EVENT_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_EVENTS;
    public static final String MAPPING_FIND_CURRENT_BY_LOCATION_ID = PATH_FRAGMENT_EVENT + "/current" + PATH_FRAGMENT_AT_LOCATION + "/{" + PARAMETER_NAME_LOCATION_ID + "}";
    public static final String MAPPING_FIND_CURRENT_BY_WORKGROUP_ID = PATH_FRAGMENT_EVENT + "/current" + PATH_FRAGMENT_OF_WORKGROUP  + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_EVENT;
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_EVENTS;
    public static final String MAPPING_DELETE = PATH_FRAGMENT_EVENT + "/{" + PARAMETER_NAME_EVENT_ID + "}";
    public static final String MAPPING_SHORTEN_EVENT = PATH_FRAGMENT_EVENT + "/{" + PARAMETER_NAME_EVENT_ID + "}" + PATH_FRAGMENT_SHORTEN;
    public static final String MAPPING_EXTEND_EVENT = PATH_FRAGMENT_EVENT + "/{" + PARAMETER_NAME_EVENT_ID + "}" + PATH_FRAGMENT_EXTEND;
    public static final String MAPPING_SHIFT_EVENT = PATH_FRAGMENT_EVENT + "/{" + PARAMETER_NAME_EVENT_ID + "}" + PATH_FRAGMENT_SHIFT;
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_EVENT + "/base-url";
}
