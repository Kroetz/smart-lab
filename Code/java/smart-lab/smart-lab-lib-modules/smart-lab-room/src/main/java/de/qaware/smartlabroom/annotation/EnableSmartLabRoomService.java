package de.qaware.smartlabroom.annotation;

import de.qaware.smartlabroom.configuration.RoomServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(RoomServiceConfiguration.class)
public @interface EnableSmartLabRoomService { }
