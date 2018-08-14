package de.qaware.smartlab.core.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

import static java.nio.file.Paths.get;

@Configuration
@EnableConfigurationProperties(ResourcesConfiguration.Properties.class)
public class ResourcesConfiguration {

    public static final String QUALIFIER_RESOURCES_TEMP_FILE_SUB_DIR = "resourcesTempFileSubDir";

    private final Properties properties;

    public ResourcesConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_RESOURCES_TEMP_FILE_SUB_DIR)
    public Path resourcesTempFileSubDir() {
        return this.properties.getSubDir();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        private static final String PREFIX = "smart-lab.temp.resources";
        private static final Path DEFAULT_SUB_DIR = get("resources");
        private Path subDir;

        public Properties() {
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
