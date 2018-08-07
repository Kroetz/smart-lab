package de.qaware.smartlab.trigger.annotation;

import de.qaware.smartlab.trigger.configuration.CleanUpMeetingTriggerProviderConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(CleanUpMeetingTriggerProviderConfiguration.class)
public @interface EnableSmartLabCleanUpMeetingTriggerProvider { }
