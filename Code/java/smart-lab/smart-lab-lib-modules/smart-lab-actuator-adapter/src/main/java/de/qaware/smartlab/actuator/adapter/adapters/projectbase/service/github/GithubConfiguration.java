package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github;

import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic.IProjectBaseAdapter;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.filesystem.TempFileManagerConfiguration;
import de.qaware.smartlab.core.miscellaneous.Property;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.GITHUB,
        name = Property.Name.GITHUB,
        havingValue = Property.Value.TRUE)
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
                this.tempFileManager,
                this.downloadsTempFileSubDir);
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.actuator.github")
    public static class Properties {

        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
