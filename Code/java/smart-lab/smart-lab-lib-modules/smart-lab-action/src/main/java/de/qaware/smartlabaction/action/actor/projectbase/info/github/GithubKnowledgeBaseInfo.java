package de.qaware.smartlabaction.action.actor.projectbase.info.github;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlabaction.action.actor.projectbase.info.generic.AbstractKnowledgeBaseInfoFactory;
import de.qaware.smartlabaction.action.actor.projectbase.service.github.GithubServiceConnector;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Slf4j
public class GithubKnowledgeBaseInfo implements IKnowledgeBaseInfo {

    public static final String SERVICE_ID = GithubServiceConnector.SERVICE_ID;
    public static final String KNOWLEDGE_BASE_PROPERTY_KEY_USER = "user";
    public static final String KNOWLEDGE_BASE_PROPERTY_KEY_REPOSITORY = "repository";

    private String user;
    private String repository;

    private GithubKnowledgeBaseInfo(Map<String, String> knowledgeBaseProperties) {
        for(String key : knowledgeBaseProperties.keySet()) {
            switch (key) {
                case KNOWLEDGE_BASE_PROPERTY_KEY_USER:
                    this.user = knowledgeBaseProperties.get(key);
                    break;
                case KNOWLEDGE_BASE_PROPERTY_KEY_REPOSITORY:
                    this.repository = knowledgeBaseProperties.get(key);
                    break;
                default:
                    log.warn("Ignoring property \"{}\" since it is not relevant for the knowledge base service \"{}\"", key, getServiceId());
                    break;
            }
        }
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }

    @Override
    public Map<String, String> getKnowledgeBaseProperties() {
        return ImmutableMap.<String, String>builder()
                .put(KNOWLEDGE_BASE_PROPERTY_KEY_USER, this.user)
                .put(KNOWLEDGE_BASE_PROPERTY_KEY_REPOSITORY, this.repository)
                .build();
    }

    @Component
    @Slf4j
    public static class Factory extends AbstractKnowledgeBaseInfoFactory {

        public Factory() {
            super(GithubKnowledgeBaseInfo.SERVICE_ID);
        }

        @Override
        public IKnowledgeBaseInfo newInstance(Map<String, String> knowledgeBaseProperties) {
            return new GithubKnowledgeBaseInfo(knowledgeBaseProperties);
        }
    }
}
