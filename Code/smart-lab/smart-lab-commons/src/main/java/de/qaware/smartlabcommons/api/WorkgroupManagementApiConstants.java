package de.qaware.smartlabcommons.api;

public class WorkgroupManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "workgroup-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";
    public static final String PARAMETER_NAME_WORKGROUP_ID = "workgroupId";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";
    public static final String MAPPING_BASE = "/smart-lab/api/workgroup";
    public static final String MAPPING_GET_WORKGROUPS = "";
    public static final String MAPPING_GET_WORKGROUP = "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_CREATE_WORKGROUP = "";
    public static final String MAPPING_DELETE_WORKGROUP = "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_GET_MEETINGS_OF_WORKGROUP = "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/meetings";
    public static final String MAPPING_GET_CURRENT_MEETING = "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/current-meeting";
    public static final String MAPPING_EXTEND_CURRENT_MEETING = "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/" + PARAMETER_NAME_EXTENSION_IN_MINUTES;
}
