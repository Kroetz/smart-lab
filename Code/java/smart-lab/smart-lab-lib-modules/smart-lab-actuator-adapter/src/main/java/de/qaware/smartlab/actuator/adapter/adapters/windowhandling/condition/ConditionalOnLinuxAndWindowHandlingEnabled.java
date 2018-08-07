package de.qaware.smartlab.actuator.adapter.adapters.windowhandling.condition;

import de.qaware.smartlabcore.condition.ConditionalOnLinux;
import de.qaware.smartlabcore.miscellaneous.Property;
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
            prefix = Property.Prefix.WINDOW_HANDLING,
            name = Property.Name.WINDOW_HANDLING,
            havingValue = Property.Value.TRUE)
    static class OnProperty { }
}