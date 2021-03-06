package de.qaware.smartlab.api.service.constant.workgroup;

public abstract class WorkgroupManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "smart-lab-workgroup-management-microservice";

    public static final String PARAMETER_NAME_WORKGROUP_ID = "workgroupId";
    public static final String PARAMETER_NAME_WORKGROUP_IDS = "workgroupIds";
    public static final String PARAMETER_NAME_EXTENSION_IN_MINUTES = "extension-in-minutes";

    private static final String PATH_FRAGMENT_WORKGROUP = "/workgroup";
    private static final String PATH_FRAGMENT_WORKGROUPS = "/workgroups";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_WORKGROUPS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_WORKGROUP + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_WORKGROUPS + "";
    public static final String MAPPING_CREATE_SINGLE = PATH_FRAGMENT_WORKGROUP + "";
    public static final String MAPPING_CREATE_MULTIPLE = PATH_FRAGMENT_WORKGROUPS + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_WORKGROUP + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}";
    public static final String MAPPING_GET_EVENTS_OF_WORKGROUP = PATH_FRAGMENT_WORKGROUP + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/events";
    public static final String MAPPING_GET_CURRENT_EVENT = PATH_FRAGMENT_WORKGROUP + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/current-event";
    public static final String MAPPING_EXTEND_CURRENT_EVENT = PATH_FRAGMENT_WORKGROUP + "/{" + PARAMETER_NAME_WORKGROUP_ID + "}/extend-current-event";
    public static final String MAPPING_GET_BASE_URL = PATH_FRAGMENT_WORKGROUP + "/base-url";
}
