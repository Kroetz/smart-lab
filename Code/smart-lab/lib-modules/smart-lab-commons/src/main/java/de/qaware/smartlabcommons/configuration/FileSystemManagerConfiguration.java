package de.qaware.smartlabcommons.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(FileSystemManagerConfiguration.TempFileProperties.class)
public class FileSystemManagerConfiguration {

    private TempFileProperties tempFileProperties;

    public FileSystemManagerConfiguration(TempFileProperties tempFileProperties) {
        this.tempFileProperties = tempFileProperties;
    }

    @Bean
    public Path tempFileBaseDir() {
        return this.tempFileProperties.getBaseDir();
    }

    @Bean
    public String tempFileNamePrefix() {
        return this.tempFileProperties.getFileNamePrefix();
    }

    @Bean
    public String tempFileNameSuffix() {
        return this.tempFileProperties.getFileNameSuffix();
    }

    @ConfigurationProperties(prefix = "temp")
    public static class TempFileProperties {

        private Path baseDir;
        private String fileNamePrefix;
        private String fileNameSuffix;

        public Path getBaseDir() {
            return this.baseDir;
        }

        public void setBaseDir(String baseDir) {
            this.baseDir = Paths.get(baseDir);
        }

        public String getFileNamePrefix() {
            return fileNamePrefix;
        }

        public void setFileNamePrefix(String fileNamePrefix) {
            this.fileNamePrefix = fileNamePrefix;
        }

        public String getFileNameSuffix() {
            return fileNameSuffix;
        }

        public void setFileNameSuffix(String fileNameSuffix) {
            this.fileNameSuffix = fileNameSuffix;
        }
    }
}
