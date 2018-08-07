package de.qaware.smartlabeventmanagement.annotation;

import de.qaware.smartlabeventmanagement.configuration.MeetingServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(MeetingServiceConfiguration.class)
public @interface EnableSmartLabMeetingService { }
