package de.qaware.smartlabcore.filesystem;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.time.Duration;

import static java.nio.file.Paths.get;

@Configuration
@EnableConfigurationProperties(TempFileManagerConfiguration.TempFileProperties.class)
public class TempFileManagerConfiguration {

    private TempFileProperties tempFileProperties;

    public TempFileManagerConfiguration(TempFileProperties tempFileProperties) {
        this.tempFileProperties = tempFileProperties;
    }

    @Bean
    public Duration obsoleteFileCleaningInterval() {
        return this.tempFileProperties.getObsoleteFileCleaningIntervalInSeconds();
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

    @Bean
    public Path recordedAudioTempFileSubDir() {
        return this.tempFileProperties.getAudioSubDir();
    }

    @Bean
    public Path downloadsTempFileSubDir() {
        return this.tempFileProperties.getDownloadSubDir();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "temp")
    public static class TempFileProperties {

        private static final int DEFAULT_OBSOLETE_FILE_CLEANING_INTERVAL_IN_SECONDS = 60;
        private static final Path DEFAULT_BASE_DIR = get(System.getProperty("java.io.tmpdir"), "smart-lab");
        private static final String DEFAULT_FILE_NAME_PREFIX = "file";
        private static final String DEFAULT_FILE_NAME_SUFFIX = ".tmp";
        private static final Path DEFAULT_AUDIO_SUB_DIR = get("audio");
        private static final Path DEFAULT_DOWNLOAD_SUB_DIR = get("download");

        private int obsoleteFileCleaningIntervalInSeconds;
        private Path baseDir;
        private String fileNamePrefix;
        private String fileNameSuffix;
        private Path audioSubDir;
        private Path downloadSubDir;

        public TempFileProperties() {
            this.obsoleteFileCleaningIntervalInSeconds = DEFAULT_OBSOLETE_FILE_CLEANING_INTERVAL_IN_SECONDS;
            this.baseDir = DEFAULT_BASE_DIR;
            this.fileNamePrefix = DEFAULT_FILE_NAME_PREFIX;
            this.fileNameSuffix = DEFAULT_FILE_NAME_SUFFIX;
            this.audioSubDir = DEFAULT_AUDIO_SUB_DIR;
            this.downloadSubDir = DEFAULT_DOWNLOAD_SUB_DIR;
        }

        public Duration getObsoleteFileCleaningIntervalInSeconds() {
            return Duration.ofSeconds(this.obsoleteFileCleaningIntervalInSeconds);
        }

        public void setObsoleteFileCleaningIntervalInSeconds(int obsoleteFileCleaningIntervalInSeconds) {
            this.obsoleteFileCleaningIntervalInSeconds = obsoleteFileCleaningIntervalInSeconds;
        }

        public Path getBaseDir() {
            return this.baseDir;
        }

        public void setBaseDir(String baseDir) {
            this.baseDir = get(baseDir);
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

        public Path getAudioSubDir() {
            return this.audioSubDir;
        }

        public void setAudioSubDir(String audioSubDir) {
            this.audioSubDir = get(audioSubDir);
        }

        public Path getDownloadSubDir() {
            return this.downloadSubDir;
        }

        public void setDownloadSubDir(String downloadSubDir) {
            this.downloadSubDir = get(downloadSubDir);
        }
    }
}
