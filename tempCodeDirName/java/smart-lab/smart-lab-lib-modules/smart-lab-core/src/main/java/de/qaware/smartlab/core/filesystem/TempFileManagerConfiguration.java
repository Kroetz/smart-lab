package de.qaware.smartlab.core.filesystem;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.time.Duration;

import static java.nio.file.Paths.get;
import static java.time.Duration.ofSeconds;

@Configuration
@EnableConfigurationProperties(TempFileManagerConfiguration.Properties.class)
public class TempFileManagerConfiguration {

    public static final String QUALIFIER_OBSOLETE_FILE_CLEANING_INTERVAL = "obsoleteFileCleaningInterval";
    public static final String QUALIFIER_TEMP_FILE_BASE_DIR = "tempFileBaseDir";
    public static final String QUALIFIER_TEMP_FILE_NAME_PREFIX = "tempFileNamePrefix";
    public static final String QUALIFIER_TEMP_FILE_NAME_SUFFIX = "tempFileNameSuffix";
    public static final String QUALIFIER_RECORDED_AUDIO_TEMP_FILE_SUB_DIR = "recordedAudioTempFileSubDir";
    public static final String QUALIFIER_DOWNLOADS_TEMP_FILE_SUB_DIR = "downloadsTempFileSubDir";

    private Properties properties;

    public TempFileManagerConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Bean
    @Qualifier(QUALIFIER_OBSOLETE_FILE_CLEANING_INTERVAL)
    public Duration obsoleteFileCleaningInterval() {
        return this.properties.getObsoleteFileCleaningIntervalInSeconds();
    }

    @Bean
    @Qualifier(QUALIFIER_TEMP_FILE_BASE_DIR)
    public Path tempFileBaseDir() {
        return this.properties.getBaseDir();
    }

    @Bean
    @Qualifier(QUALIFIER_TEMP_FILE_NAME_PREFIX)
    public String tempFileNamePrefix() {
        return this.properties.getFileNamePrefix();
    }

    @Bean
    @Qualifier(QUALIFIER_TEMP_FILE_NAME_SUFFIX)
    public String tempFileNameSuffix() {
        return this.properties.getFileNameSuffix();
    }

    @Bean
    @Qualifier(QUALIFIER_RECORDED_AUDIO_TEMP_FILE_SUB_DIR)
    public Path recordedAudioTempFileSubDir() {
        return this.properties.getAudioSubDir();
    }

    @Bean
    @Qualifier(QUALIFIER_DOWNLOADS_TEMP_FILE_SUB_DIR)
    public Path downloadsTempFileSubDir() {
        return this.properties.getDownloadSubDir();
    }

    @ConfigurationProperties(prefix = Properties.PREFIX)
    public static class Properties {

        private static final String PREFIX = "smart-lab.temp";
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

        public Properties() {
            this.obsoleteFileCleaningIntervalInSeconds = DEFAULT_OBSOLETE_FILE_CLEANING_INTERVAL_IN_SECONDS;
            this.baseDir = DEFAULT_BASE_DIR;
            this.fileNamePrefix = DEFAULT_FILE_NAME_PREFIX;
            this.fileNameSuffix = DEFAULT_FILE_NAME_SUFFIX;
            this.audioSubDir = DEFAULT_AUDIO_SUB_DIR;
            this.downloadSubDir = DEFAULT_DOWNLOAD_SUB_DIR;
        }

        public Duration getObsoleteFileCleaningIntervalInSeconds() {
            return ofSeconds(this.obsoleteFileCleaningIntervalInSeconds);
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
