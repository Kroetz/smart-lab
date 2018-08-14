package de.qaware.smartlab.core.service.url;

import java.net.URL;

public interface IServiceBaseUrlGetter {

    String QUALIFIER_JOB_MANAGEMENT_SERVICE_BASE_URL_GETTER = "jobManagementServiceBaseUrlGetter";
    String QUALIFIER_EVENT_MANAGEMENT_SERVICE_BASE_URL_GETTER = "eventManagementServiceBaseUrlGetter";
    String QUALIFIER_LOCATION_MANAGEMENT_SERVICE_BASE_URL_GETTER = "locationManagementServiceBaseUrlGetter";
    String QUALIFIER_ACTUATOR_MANAGEMENT_SERVICE_BASE_URL_GETTER = "actuatorManagementServiceBaseUrlGetter";
    String QUALIFIER_WORKGROUP_MANAGEMENT_SERVICE_BASE_URL_GETTER = "workgroupManagementServiceBaseUrlGetter";
    String QUALIFIER_PERSON_MANAGEMENT_SERVICE_BASE_URL_GETTER = "personManagementServiceBaseUrlGetter";
    String QUALIFIER_TRIGGER_SERVICE_BASE_URL_GETTER = "triggerServiceBaseUrlGetter";
    String QUALIFIER_ASSISTANCE_SERVICE_BASE_URL_GETTER = "assistanceServiceBaseUrlGetter";
    String QUALIFIER_ACTION_SERVICE_BASE_URL_GETTER = "actionServiceBaseUrlGetter";
    String QUALIFIER_GUI_SERVICE_BASE_URL_GETTER = "guiServiceBaseUrlGetter";

    URL getBaseUrl();
}
