package de.qaware.smartlabcore.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

import static java.nio.file.Paths.get;

@Configuration
@EnableConfigurationProperties(ResourcesConfiguration.TempFileProperties.class)
public class ResourcesConfiguration {

    private final TempFileProperties tempFileProperties;

    public ResourcesConfiguration(TempFileProperties tempFileProperties) {
        this.tempFileProperties = tempFileProperties;
    }

    @Bean
    // TODO: String literals
    @Qualifier("resourcesTempFileSubDir")
    public Path resourcesTempFileSubDir() {
        return this.tempFileProperties.getSubDir();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "smart-lab.temp.resources")
    public static class TempFileProperties {

        private static final Path DEFAULT_SUB_DIR = get("resources");
        private Path subDir;

        public TempFileProperties() {
            this.subDir = DEFAULT_SUB_DIR;
        }

        public Path getSubDir() {
            return this.subDir;
        }

        public void setSubDir(String subDir) {
            this.subDir = get(subDir);
        }
    }
}
