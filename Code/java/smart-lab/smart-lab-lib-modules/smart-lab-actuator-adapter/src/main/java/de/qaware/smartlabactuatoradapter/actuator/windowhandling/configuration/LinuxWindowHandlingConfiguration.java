package de.qaware.smartlabactuatoradapter.actuator.windowhandling.configuration;

import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.IWindowHandler;
import de.qaware.smartlabactuatoradapter.actuator.windowhandling.windowhandler.LinuxWindowHandler;
import de.qaware.smartlabcore.condition.LinuxEnvironmentCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Configuration
@Conditional(LinuxEnvironmentCondition.class)
@Slf4j
public class LinuxWindowHandlingConfiguration {

    private final Map<String, String> localDisplaysBySmartLabDisplayId;
    private final Duration findWindowTimeout;

    public LinuxWindowHandlingConfiguration(
            // TODO: String literal
            @Qualifier("localDisplaysBySmartLabDisplayIds") Map<String, String> localDisplaysBySmartLabDisplayId,
            Duration findWindowTimeout) {
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
