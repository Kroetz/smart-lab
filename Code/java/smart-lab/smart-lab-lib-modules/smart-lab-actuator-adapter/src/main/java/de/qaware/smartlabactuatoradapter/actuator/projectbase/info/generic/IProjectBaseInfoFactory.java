package de.qaware.smartlabactuatoradapter.actuator.projectbase.info.generic;

import de.qaware.smartlabcore.data.workgroup.IProjectBaseInfo;

import java.util.Map;

public interface IProjectBaseInfoFactory {

    String getServiceId();
    IProjectBaseInfo newInstance(Map<String, String> projectBaseProperties);
}
