package de.qaware.smartlabcommons.configuration.action;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(DeactivateMicrophoneConfiguration.TempFileProperties.class)
public class DeactivateMicrophoneConfiguration {

    private TempFileProperties tempFileProperties;

    public DeactivateMicrophoneConfiguration(TempFileProperties tempFileProperties) {
        this.tempFileProperties = tempFileProperties;
    }

    @Bean
    public Path recordedAudioTempFileSubDir() {
        return this.tempFileProperties.getSubDir();
    }

    // TODO: String literal
    @ConfigurationProperties(prefix = "temp.audio")
    public static class TempFileProperties {

        private Path subDir;

        public Path getSubDir() {
            return this.subDir;
        }

        public void setSubDir(String subDir) {
            this.subDir = Paths.get(subDir);
        }
    }
}
