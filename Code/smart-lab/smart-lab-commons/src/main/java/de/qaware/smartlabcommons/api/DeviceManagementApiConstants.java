package de.qaware.smartlabcommons.api;

public class DeviceManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "device-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";
    public static final String PARAMETER_NAME_DEVICE_ID = "deviceId";
    public static final String MAPPING_BASE = "/smart-lab/api/device";
    public static final String MAPPING_GET_DEVICES = "";
    public static final String MAPPING_GET_DEVICE = "/{" + PARAMETER_NAME_DEVICE_ID + "}";
    public static final String MAPPING_CREATE_DEVICE = "";
    public static final String MAPPING_DELETE_DEVICE = "/{" + PARAMETER_NAME_DEVICE_ID + "}";
}
