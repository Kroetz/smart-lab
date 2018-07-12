package de.qaware.smartlabapi;

public class ActionApiConstants {

    public static final String FEIGN_CLIENT_NAME = "action";

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";

    public static final String MAPPING_BASE = "/smart-lab/api/action";
    public static final String MAPPING_EXECUTE = "/{" + PARAMETER_NAME_ACTION_ID + "}" + "/execute";
    public static final String MAPPING_GET_BASE_URL = "/url";
}
