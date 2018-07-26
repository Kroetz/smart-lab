package de.qaware.smartlabgui.annotation;

import de.qaware.smartlabgui.configuration.GuiConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(GuiConfiguration.class)
public @interface EnableSmartLabGui { }
