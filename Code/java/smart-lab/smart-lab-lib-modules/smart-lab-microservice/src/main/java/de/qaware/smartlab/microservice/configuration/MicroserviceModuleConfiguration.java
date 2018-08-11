package de.qaware.smartlab.microservice.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MICROSERVICE)
@ComponentScan(basePackageClasses = de.qaware.smartlab.microservice.ComponentScanMarker.class)
@EnableSmartLabApi
@EnableSmartLabDtoConverters
public class MicroserviceModuleConfiguration { }
