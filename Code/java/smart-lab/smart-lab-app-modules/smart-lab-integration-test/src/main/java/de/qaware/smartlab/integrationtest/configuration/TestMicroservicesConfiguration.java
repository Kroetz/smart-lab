package de.qaware.smartlab.integrationtest.configuration;

import de.qaware.smartlab.api.annotation.EnableSmartLabApi;
import de.qaware.smartlab.core.annotation.EnableSmartLabCore;
import de.qaware.smartlab.core.constant.Property;
import de.qaware.smartlab.data.set.annotation.EnableSmartLabInitialData;
import de.qaware.smartlab.microservice.annotation.EnableSmartLabMicroservice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MICROSERVICE)
@EnableSmartLabCore
@EnableSmartLabApi
@EnableSmartLabInitialData
@EnableSmartLabMicroservice
public class TestMicroservicesConfiguration { }
