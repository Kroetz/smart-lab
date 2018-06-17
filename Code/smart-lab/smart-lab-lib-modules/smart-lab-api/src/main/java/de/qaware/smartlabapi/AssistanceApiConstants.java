package de.qaware.smartlabapi;

public class AssistanceApiConstants {

    public static final String FEIGN_CLIENT_NAME = "assistance";

    public static final String PARAMETER_NAME_ASSISTANCE_ID = "assistanceId";

    public static final String MAPPING_BASE = "/smart-lab/api/assistance";
    public static final String MAPPING_BEGIN = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/begin";
    public static final String MAPPING_END = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/end";
    public static final String MAPPING_UPDATE = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/update";
}
