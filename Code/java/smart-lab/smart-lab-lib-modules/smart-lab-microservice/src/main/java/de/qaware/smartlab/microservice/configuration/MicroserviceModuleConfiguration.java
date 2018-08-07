package de.qaware.smartlab.microservice.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlab.data.conversion.annotation.EnableSmartLabDtoConverters;
import de.qaware.smartlab.microservice.ComponentScanMarker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
@ComponentScan(basePackageClasses = {ComponentScanMarker.class})
@EnableSmartLabApi
@EnableSmartLabDtoConverters
public class MicroserviceModuleConfiguration { }
