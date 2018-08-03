package de.qaware.smartlabaction.action.actor.projectbase.info.generic;

import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;

import java.util.Map;

public interface IKnowledgeBaseInfoFactory {

    String getServiceId();
    IKnowledgeBaseInfo newInstance(Map<String, String> knowledgeBaseProperties);
}
