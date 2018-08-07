package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.AbstractProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubServiceConnector;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.miscellaneous.Property;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public class GithubProjectBaseInfo implements IProjectBaseInfo {

    public static final String SERVICE_ID = GithubServiceConnector.SERVICE_ID;
    public static final String PROJECT_BASE_PROPERTY_KEY_USER = "user";
    public static final String PROJECT_BASE_PROPERTY_KEY_REPOSITORY = "repository";

    private String user;
    private String repository;

    private GithubProjectBaseInfo(Map<String, String> projectBaseProperties) {
        for(String key : projectBaseProperties.keySet()) {
            switch (key) {
                case PROJECT_BASE_PROPERTY_KEY_USER:
                    this.user = projectBaseProperties.get(key);
                    break;
                case PROJECT_BASE_PROPERTY_KEY_REPOSITORY:
                    this.repository = projectBaseProperties.get(key);
                    break;
                default:
                    log.warn("Ignoring property \"{}\" since it is not relevant for the project base service \"{}\"", key, getServiceId());
                    break;
            }
        }
    }

    @Override
    public String getServiceId() {
        return SERVICE_ID;
    }

    @Override
    public Map<String, String> getProjectBaseProperties() {
        return ImmutableMap.<String, String>builder()
                .put(PROJECT_BASE_PROPERTY_KEY_USER, this.user)
                .put(PROJECT_BASE_PROPERTY_KEY_REPOSITORY, this.repository)
                .build();
    }

    @Component
    @ConditionalOnProperty(
            prefix = Property.Prefix.GITHUB,
            name = Property.Name.GITHUB,
            havingValue = Property.Value.TRUE)
    @Slf4j
    public static class Factory extends AbstractProjectBaseInfoFactory {

        public Factory() {
            super(GithubProjectBaseInfo.SERVICE_ID);
        }

        @Override
        public IProjectBaseInfo newInstance(Map<String, String> projectBaseProperties) {
            return new GithubProjectBaseInfo(projectBaseProperties);
        }
    }
}