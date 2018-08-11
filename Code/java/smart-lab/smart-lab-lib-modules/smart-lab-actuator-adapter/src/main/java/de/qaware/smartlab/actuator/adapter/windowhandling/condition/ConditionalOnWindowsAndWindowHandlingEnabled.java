package de.qaware.smartlab.actuator.adapter.windowhandling.condition;

import de.qaware.smartlab.core.condition.ConditionalOnWindows;
import de.qaware.smartlab.core.constant.Constants;
import de.qaware.smartlab.core.constant.Property;
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
            prefix = Property.Prefix.WINDOW_HANDLING,
            name = Property.Name.WINDOW_HANDLING,
            havingValue = Constants.TRUE)
    static class OnProperty { }
}
