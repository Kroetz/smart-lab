package de.qaware.smartlab.actuator.adapter.adapters.miscellaneous.remotecontrol;

import de.qaware.smartlab.core.exception.configuration.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

@Configuration
@Slf4j
public class InfraredRemoteControlConfiguration {

    @Bean
    public IInfraredRemoteControl infraredRemoteControl() {
        if(IS_OS_WINDOWS) return new WindowsCompatibleInfraredRemoteControl();
        if(IS_OS_LINUX) return LircInfraredRemoteControl.newInstance();
        String errorMessage = "The operating system is not supported for infrared remote controls";
        log.error(errorMessage);
        throw new ConfigurationException(errorMessage);
    }
}
