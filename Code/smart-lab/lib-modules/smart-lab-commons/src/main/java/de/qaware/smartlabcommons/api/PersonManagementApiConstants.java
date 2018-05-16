package de.qaware.smartlabcommons.api;

public class PersonManagementApiConstants {

    public static final String FEIGN_CLIENT_VALUE = "person-management";

    public static final String PARAMETER_NAME_PERSON_ID = "personId";
    public static final String PARAMETER_NAME_PERSON_IDS = "personIds";

    public static final String PATH_FRAGMENT_PERSON = "/person";
    public static final String PATH_FRAGMENT_PERSONS = "/persons";

    public static final String MAPPING_BASE = "/smart-lab/api";
    public static final String MAPPING_FIND_ALL = PATH_FRAGMENT_PERSONS + "/all";
    public static final String MAPPING_FIND_ONE = PATH_FRAGMENT_PERSON + "/{" + PARAMETER_NAME_PERSON_ID + "}";
    public static final String MAPPING_FIND_MULTIPLE = PATH_FRAGMENT_PERSONS + "";
    public static final String MAPPING_CREATE = PATH_FRAGMENT_PERSON + "";
    public static final String MAPPING_DELETE = PATH_FRAGMENT_PERSON + "/{" + PARAMETER_NAME_PERSON_ID + "}";
}
