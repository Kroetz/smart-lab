package de.qaware.smartlabmicroservice.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabdata.annotation.EnableSmartLabDtoConverters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
@ComponentScan(basePackageClasses = {de.qaware.smartlabmicroservice.ComponentScanMarker.class})
@EnableSmartLabApi
@EnableSmartLabDtoConverters
public class MicroserviceModuleConfiguration { }
