package de.qaware.smartlabcommons.api;

public class PersonManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "person-management";
    public static final String FEIGN_CLIENT_URL = "http://localhost:8080";
    public static final String PARAMETER_NAME_PERSON_ID = "personId";
    public static final String MAPPING_BASE = "/smart-lab/api/person";
    public static final String MAPPING_FIND_ALL = "";
    public static final String MAPPING_FIND_ONE = "/{" + PARAMETER_NAME_PERSON_ID + "}";
    public static final String MAPPING_CREATE = "";
    public static final String MAPPING_DELETE = "/{" + PARAMETER_NAME_PERSON_ID + "}";
}
