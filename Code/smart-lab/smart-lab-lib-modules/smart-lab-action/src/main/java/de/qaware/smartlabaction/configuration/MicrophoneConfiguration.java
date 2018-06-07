package de.qaware.smartlabaction.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(MicrophoneConfiguration.TempFileProperties.class)
public class MicrophoneConfiguration {

    private TempFileProperties tempFileProperties;

    public MicrophoneConfiguration(TempFileProperties tempFileProperties) {
        this.tempFileProperties = tempFileProperties;
    }

    @Bean
    public Path recordedAudioTempFileSubDir() {
        return this.tempFileProperties.getSubDir();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "temp.audio")
    public static class TempFileProperties {

        private static final Path DEFAULT_SUB_DIR = Paths.get("audio");
        private Path subDir;

        public TempFileProperties() {
            this.subDir = DEFAULT_SUB_DIR;
        }

        public Path getSubDir() {
            return this.subDir;
        }

        public void setSubDir(String subDir) {
            this.subDir = Paths.get(subDir);
        }
    }
}
