package de.qaware.smartlab.api.service.constant.actuator;

public abstract class ActuatorManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-actuator-management-microservice";

    public static final String PARAMETER_NAME_ACTUATOR_ID = "actuatorId";
    public static final String PARAMETER_NAME_ACTUATOR_IDS = "actuatorIds";

    private static final String PATH_FRAGMENT_ACTUATOR = "/actuator";
    private static final String PATH_FRAGMENT_ACTUATORS = "/actuators";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_ACTUATORS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_ACTUATOR + "/{" + PARAMETER_NAME_ACTUATOR_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_ACTUATORS + "";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_ACTUATOR + "";
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_ACTUATORS + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_ACTUATOR + "/{" + PARAMETER_NAME_ACTUATOR_ID + "}";
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_ACTUATOR + "/base-url";
}
