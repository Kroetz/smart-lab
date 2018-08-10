package de.qaware.smartlab.actuator.adapter.windowhandling.configuration;

import de.qaware.smartlab.actuator.adapter.windowhandling.condition.ConditionalOnLinuxAndWindowHandlingEnabled;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlab.actuator.adapter.windowhandling.windowhandler.LinuxWindowHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Configuration
@Conditional(ConditionalOnLinuxAndWindowHandlingEnabled.class)
@Slf4j
public class LinuxWindowHandlingConfiguration {

    private final Map<String, String> localDisplaysBySmartLabDisplayId;
    private final Duration findWindowTimeout;

    public LinuxWindowHandlingConfiguration(
            @Qualifier(CommonWindowHandlingConfiguration.QUALIFIER_LOCAL_DISPLAYS_BY_SMART_LAB_DISPLAY_IDS) Map<String, String> localDisplaysBySmartLabDisplayId,
            @Qualifier(CommonWindowHandlingConfiguration.QUALIFIER_FIND_WINDOW_TIMEOUT) Duration findWindowTimeout) {
        this.localDisplaysBySmartLabDisplayId = localDisplaysBySmartLabDisplayId;
        this.findWindowTimeout = findWindowTimeout;
    }

    @Bean
    public IWindowHandler windowHandler() {
        return new LinuxWindowHandler(
                this.localDisplaysBySmartLabDisplayId,
                this.findWindowTimeout);
    }
}
