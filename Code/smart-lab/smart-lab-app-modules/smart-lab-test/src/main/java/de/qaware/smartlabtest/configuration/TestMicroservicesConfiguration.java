package de.qaware.smartlabtest.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabsampledata.annotation.EnableSmartLabSampleData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabSampleData
public class TestMicroservicesConfiguration { }
