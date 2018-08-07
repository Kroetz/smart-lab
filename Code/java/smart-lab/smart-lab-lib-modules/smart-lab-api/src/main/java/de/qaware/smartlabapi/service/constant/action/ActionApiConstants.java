package de.qaware.smartlabapi.service.constant.action;

public abstract class ActionApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-action-microservice";

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";

    public static final String MAPPING_BASE = "/smart-lab/api/action";
    public static final String MAPPING_EXECUTE = "/{" + PARAMETER_NAME_ACTION_ID + "}" + "/execute";
    public static final String MAPPING_GET_BASE_URL = "/base-url";
}
