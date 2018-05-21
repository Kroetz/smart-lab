package de.qaware.smartlabcommons.api.internal;

public class ActionApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "action";

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";

    public static final String MAPPING_BASE = "/smart-lab/api/action";
    public static final String MAPPING_EXECUTE = "/{" + PARAMETER_NAME_ACTION_ID + "}" + "/execute";
}
