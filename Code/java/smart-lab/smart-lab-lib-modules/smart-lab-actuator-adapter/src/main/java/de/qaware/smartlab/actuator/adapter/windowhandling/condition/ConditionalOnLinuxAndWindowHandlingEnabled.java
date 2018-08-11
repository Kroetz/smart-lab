package de.qaware.smartlab.actuator.adapter.windowhandling.condition;

import de.qaware.smartlab.actuator.adapter.windowhandling.configuration.CommonWindowHandlingConfiguration;
import de.qaware.smartlab.core.condition.ConditionalOnLinux;
import de.qaware.smartlab.core.constant.Miscellaneous;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

public class ConditionalOnLinuxAndWindowHandlingEnabled extends AllNestedConditions {

    ConditionalOnLinuxAndWindowHandlingEnabled() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @Conditional(ConditionalOnLinux.class)
    static class OnLinux { }

    @ConditionalOnProperty(
            prefix = CommonWindowHandlingConfiguration.Properties.PREFIX,
            name = CommonWindowHandlingConfiguration.Properties.ENABLED,
            havingValue = Miscellaneous.TRUE)
    static class OnProperty { }
}