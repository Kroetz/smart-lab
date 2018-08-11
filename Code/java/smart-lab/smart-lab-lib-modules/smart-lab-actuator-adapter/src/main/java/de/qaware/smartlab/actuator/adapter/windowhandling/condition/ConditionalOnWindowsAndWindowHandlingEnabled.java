package de.qaware.smartlab.actuator.adapter.windowhandling.condition;

import de.qaware.smartlab.actuator.adapter.windowhandling.configuration.CommonWindowHandlingConfiguration;
import de.qaware.smartlab.core.condition.ConditionalOnWindows;
import de.qaware.smartlab.core.constant.Miscellaneous;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

public class ConditionalOnWindowsAndWindowHandlingEnabled extends AllNestedConditions {

    ConditionalOnWindowsAndWindowHandlingEnabled() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @Conditional(ConditionalOnWindows.class)
    static class OnWindows { }

    @ConditionalOnProperty(
            prefix = CommonWindowHandlingConfiguration.Properties.PREFIX,
            name = CommonWindowHandlingConfiguration.Properties.ENABLED,
            havingValue = Miscellaneous.TRUE)
    static class OnProperty { }
}
