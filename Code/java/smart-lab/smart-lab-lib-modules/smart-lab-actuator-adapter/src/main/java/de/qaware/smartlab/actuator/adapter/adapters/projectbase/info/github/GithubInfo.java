package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github;

import com.google.common.collect.ImmutableMap;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic.AbstractProjectBaseInfoFactory;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github.GithubConfiguration;
import de.qaware.smartlab.core.constant.Constants;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
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
public class GithubInfo implements IProjectBaseInfo {

    public static final String ACTUATOR_TYPE = GithubAdapter.ACTUATOR_TYPE;
    public static final String PROJECT_BASE_PROPERTY_KEY_USER = "user";
    public static final String PROJECT_BASE_PROPERTY_KEY_REPOSITORY = "repository";

    private String user;
    private String repository;

    private GithubInfo(Map<String, String> projectBaseProperties) {
        for(String key : projectBaseProperties.keySet()) {
            switch (key) {
                case PROJECT_BASE_PROPERTY_KEY_USER:
                    this.user = projectBaseProperties.get(key);
                    break;
                case PROJECT_BASE_PROPERTY_KEY_REPOSITORY:
                    this.repository = projectBaseProperties.get(key);
                    break;
                default:
                    log.warn("Ignoring property \"{}\" since it is not relevant for the project base service \"{}\"", key, getActuatorType());
                    break;
            }
        }
    }

    @Override
    public String getActuatorType() {
        return ACTUATOR_TYPE;
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
            prefix = GithubConfiguration.Properties.PREFIX,
            name = GithubConfiguration.Properties.ENABLED,
            havingValue = Constants.TRUE)
    @Slf4j
    public static class Factory extends AbstractProjectBaseInfoFactory {

        public Factory() {
            super(GithubInfo.ACTUATOR_TYPE);
        }

        @Override
        public IProjectBaseInfo newInstance(Map<String, String> projectBaseProperties) {
            return new GithubInfo(projectBaseProperties);
        }
    }
}
