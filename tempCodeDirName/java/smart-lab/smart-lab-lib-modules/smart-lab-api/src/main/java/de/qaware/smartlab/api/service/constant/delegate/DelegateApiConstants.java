package de.qaware.smartlab.api.service.constant.delegate;

public abstract class DelegateApiConstants {

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";
    public static final String PARAMETER_NAME_ACTUATOR_TYPE = "actuatorType";

    public static final String MAPPING_BASE = "/smart-lab/api/delegate";
    public static final String MAPPING_EXECUTE = "/execute/action/{" + PARAMETER_NAME_ACTION_ID + "}/with/actuator/{" + PARAMETER_NAME_ACTUATOR_TYPE + "}";
}
