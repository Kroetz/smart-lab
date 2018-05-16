package de.qaware.smartlabcommons.api;

public class DelegateApiConstants {

    public static final String PARAMETER_NAME_ACTION_ID = "actionId";

    public static final String MAPPING_BASE = "/smart-lab/api/delegate";
    public static final String MAPPING_EXECUTE = "/action/{" + PARAMETER_NAME_ACTION_ID + "}" + "/execute";
}
