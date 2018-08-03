package de.qaware.smartlabtest.configuration;

import de.qaware.smartlabapi.annotation.EnableSmartLabApi;
import de.qaware.smartlabcore.annotation.EnableSmartLabCore;
import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabdataset.annotation.EnableSmartLabInitialData;
import de.qaware.smartlabmicroservice.annotation.EnableSmartLabMicroservice;
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
