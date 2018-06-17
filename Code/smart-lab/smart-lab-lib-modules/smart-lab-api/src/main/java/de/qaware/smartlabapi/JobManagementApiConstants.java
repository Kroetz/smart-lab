package de.qaware.smartlabapi;

public class JobManagementApiConstants {

    public static final String FEIGN_CLIENT_NAME = "job-management";

    public static final String PARAMETER_NAME_JOB_ID = "jobId";
    public static final String PARAMETER_NAME_CALLBACK_URL = "callback-url";

    public static final String PATH_FRAGMENT_JOB = "/job";
    public static final String PATH_FRAGMENT_JOBS = "/jobs";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_JOBS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_JOB + "/{" + PARAMETER_NAME_JOB_ID + "}";
    public static final String MAPPING_RECORD_NEW_JOB = PATH_FRAGMENT_JOB + "";
    public static final String MAPPING_MARK_JOB_AS_PROCESSING = PATH_FRAGMENT_JOB + "/{" + PARAMETER_NAME_JOB_ID + "}/processing";
    public static final String MAPPING_MARK_JOB_AS_FINISHED = PATH_FRAGMENT_JOB + "/{" + PARAMETER_NAME_JOB_ID + "}/finished";
    public static final String MAPPING_MARK_JOB_AS_FAILED = PATH_FRAGMENT_JOB + "/{" + PARAMETER_NAME_JOB_ID + "}/failed";
}
