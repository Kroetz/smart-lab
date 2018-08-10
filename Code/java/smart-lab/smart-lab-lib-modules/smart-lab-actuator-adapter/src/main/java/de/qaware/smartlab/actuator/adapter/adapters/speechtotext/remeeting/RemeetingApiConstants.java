package de.qaware.smartlab.actuator.adapter.adapters.speechtotext.remeeting;

public class RemeetingApiConstants {

    public static final String FEIGN_CLIENT_NAME = "remeeting";
    public static final String FEIGN_CLIENT_URL = "https://api.remeeting.com/asr/v1/";

    public static final String PARAMETER_NAME_JOB_ID = "jobId";

    public static final String MAPPING_SUBMIT_JOB = "/recognitions";
    public static final String MAPPING_QUERY_RESULT = "/recognitions/{" + PARAMETER_NAME_JOB_ID + "}";

    public static final String CONSUMES_TYPE_SUBMIT_JOB = "audio/wav";
}
