package de.qaware.smartlabapi;

public class DelegateApiConstants {

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";
    public static final String PARAMETER_NAME_DEVICE_TYPE = "deviceType";

    public static final String MAPPING_BASE = "/smart-lab/api/delegate";
    public static final String MAPPING_EXECUTE = "/execute/action/{" + PARAMETER_NAME_ACTION_ID + "}/with/device/{" + PARAMETER_NAME_DEVICE_TYPE+ "}";
}
