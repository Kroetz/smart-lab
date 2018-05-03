package de.qaware.smartlabcommons.api;

public class AssistanceApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "assistance";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";

    public static final String PARAMETER_NAME_ASSISTANCE_ID = "assistanceId";

    public static final String MAPPING_BASE = "/smart-lab/api/assistance";
    public static final String MAPPING_BEGIN = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/begin";
    public static final String MAPPING_END = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/end";
    public static final String MAPPING_UPDATE = "/{" + PARAMETER_NAME_ASSISTANCE_ID + "}" + "/update";
}
