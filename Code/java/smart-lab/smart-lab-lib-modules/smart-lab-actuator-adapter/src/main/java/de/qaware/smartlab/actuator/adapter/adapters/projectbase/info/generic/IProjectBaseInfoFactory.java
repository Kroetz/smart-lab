package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic;

import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;

import java.util.Map;

public interface IProjectBaseInfoFactory {

    String getDeviceType();
    IProjectBaseInfo newInstance(Map<String, String> projectBaseProperties);
}
