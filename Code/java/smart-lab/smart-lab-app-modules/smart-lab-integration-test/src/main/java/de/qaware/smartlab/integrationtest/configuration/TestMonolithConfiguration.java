package de.qaware.smartlab.integrationtest.configuration;

import de.qaware.smartlab.core.miscellaneous.Property;
import de.qaware.smartlabmonolith.annotation.EnableSmartLabMonolith;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = Property.Prefix.MODULARITY,
        name = Property.Name.MODULARITY,
        havingValue = Property.Value.Modularity.MONOLITH)
@EnableSmartLabMonolith
public class TestMonolithConfiguration { }
