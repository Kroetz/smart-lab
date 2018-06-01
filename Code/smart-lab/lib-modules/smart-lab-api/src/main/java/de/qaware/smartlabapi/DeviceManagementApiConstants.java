package de.qaware.smartlabapi;

public class DeviceManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "device-management";

    public static final String PARAMETER_NAME_DEVICE_ID = "deviceId";
    public static final String PARAMETER_NAME_DEVICE_IDS = "deviceIds";

    public static final String PATH_FRAGMENT_DEVICE = "/device";
    public static final String PATH_FRAGMENT_DEVICES = "/devices";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_DEVICES + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_DEVICE + "/{" + PARAMETER_NAME_DEVICE_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_DEVICES + "";
    public static final String MAPPING_CREATE = PATH_FRAGMENT_DEVICE + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_DEVICE + "/{" + PARAMETER_NAME_DEVICE_ID + "}";
}
