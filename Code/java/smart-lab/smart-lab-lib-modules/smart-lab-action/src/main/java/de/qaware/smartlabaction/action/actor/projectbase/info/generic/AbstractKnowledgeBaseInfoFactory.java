package de.qaware.smartlabaction.action.actor.projectbase.info.generic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractKnowledgeBaseInfoFactory implements IKnowledgeBaseInfoFactory {

    protected final String serviceId;

    public AbstractKnowledgeBaseInfoFactory(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return this.serviceId;
    }
}
