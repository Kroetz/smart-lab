package de.qaware.smartlabactuatoradapter.actuator.windowhandling.condition;

import de.qaware.smartlabcore.condition.WindowsEnvironmentCondition;
import de.qaware.smartlabcore.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;

public class ConditionalOnWindowsAndWindowHandlingEnabled extends AllNestedConditions {

    ConditionalOnWindowsAndWindowHandlingEnabled() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @Conditional(WindowsEnvironmentCondition.class)
    static class OnWindows { }

    @ConditionalOnProperty(
            prefix = Property.Prefix.WINDOW_HANDLING,
            name = Property.Name.WINDOW_HANDLING,
            havingValue = Property.Value.TRUE)
    static class OnProperty { }
}
