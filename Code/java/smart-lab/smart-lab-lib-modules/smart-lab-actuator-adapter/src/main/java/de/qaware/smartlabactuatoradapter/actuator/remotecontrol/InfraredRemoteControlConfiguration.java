package de.qaware.smartlabactuatoradapter.actuator.remotecontrol;

import de.qaware.smartlabcore.exception.ConfigurationException;
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
        throw new ConfigurationException("The operating system is not supported");
    }
}
