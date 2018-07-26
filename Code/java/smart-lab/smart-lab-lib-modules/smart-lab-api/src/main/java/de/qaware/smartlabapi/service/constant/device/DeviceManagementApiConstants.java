package de.qaware.smartlabapi.service.constant.device;

public abstract class DeviceManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "device-management";

    public static final String PARAMETER_NAME_DEVICE_ID = "deviceId";
    public static final String PARAMETER_NAME_DEVICE_IDS = "deviceIds";

    private static final String PATH_FRAGMENT_DEVICE = "/device";
    private static final String PATH_FRAGMENT_DEVICES = "/devices";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_DEVICES + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_DEVICE + "/{" + PARAMETER_NAME_DEVICE_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_DEVICES + "";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_DEVICE + "";
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_DEVICES + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_DEVICE + "/{" + PARAMETER_NAME_DEVICE_ID + "}";
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_DEVICE + "/base-url";
}
