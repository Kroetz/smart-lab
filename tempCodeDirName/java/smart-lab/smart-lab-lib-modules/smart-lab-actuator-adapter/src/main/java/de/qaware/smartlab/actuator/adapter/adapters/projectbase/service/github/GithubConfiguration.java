package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github;

import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic.IProjectBaseAdapter;
import de.qaware.smartlab.core.constant.Miscellaneous;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.filesystem.TempFileManagerConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConditionalOnProperty(
        prefix = GithubConfiguration.Properties.PREFIX,
        name = GithubConfiguration.Properties.ENABLED,
        havingValue = Miscellaneous.TRUE)
@EnableConfigurationProperties(GithubConfiguration.Properties.class)
public class GithubConfiguration {

    private final Properties properties;
    private final ITempFileManager tempFileManager;
    private final Path downloadsTempFileSubDir;

    public GithubConfiguration(
            Properties properties,
            ITempFileManager tempFileManager,
            @Qualifier(TempFileManagerConfiguration.QUALIFIER_DOWNLOADS_TEMP_FILE_SUB_DIR) Path downloadsTempFileSubDir) {
        this.properties = properties;
        this.tempFileManager = tempFileManager;
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
    }

    @Bean
    public IProjectBaseAdapter githubAdapter() {
        return new GithubAdapter(
                properties.getApiKey(),
                properties.getCommitterName(),
                properties.getCommitterEmail(),
                this.tempFileManager,
                this.downloadsTempFileSubDir);
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        public static final String PREFIX = "smart-lab.actuator.github";
        public static final String ENABLED = "enabled";

        private String apiKey;
        private String committerName;
        private String committerEmail;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getCommitterName() {
            return committerName;
        }

        public void setCommitterName(String committerName) {
            this.committerName = committerName;
        }

        public String getCommitterEmail() {
            return committerEmail;
        }

        public void setCommitterEmail(String committerEmail) {
            this.committerEmail = committerEmail;
        }
    }
}
