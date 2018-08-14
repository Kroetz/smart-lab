package de.qaware.smartlab.core.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ModularityConfiguration {

    public static class Properties {

        public static final String PREFIX = "smart-lab.app.";
        public static final String MODULARITY = "modularity";
        public static final String MICROSERVICE = "microservice";
        public static final String MONOLITH = "monolith";
    }
}
