package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.workgroup.dto.ProjectBaseInfoDto;

import java.util.Map;

public interface IKnowledgeBaseInfo {

    String getServiceId();
    ProjectBaseInfoDto toDto();
    Map<String, String> getKnowledgeBaseProperties();
}
