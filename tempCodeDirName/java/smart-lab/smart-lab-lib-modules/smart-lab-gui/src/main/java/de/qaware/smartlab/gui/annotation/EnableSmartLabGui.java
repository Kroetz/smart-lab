package de.qaware.smartlab.gui.annotation;

import de.qaware.smartlab.gui.configuration.GuiConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@Import(GuiConfiguration.class)
public @interface EnableSmartLabGui { }
