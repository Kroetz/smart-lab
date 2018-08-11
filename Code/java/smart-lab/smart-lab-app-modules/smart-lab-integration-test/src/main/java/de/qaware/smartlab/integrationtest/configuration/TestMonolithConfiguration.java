package de.qaware.smartlab.integrationtest.configuration;

import de.qaware.smartlab.core.configuration.ModularityConfiguration;
import de.qaware.smartlab.monolith.annotation.EnableSmartLabMonolith;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = ModularityConfiguration.Properties.PREFIX,
        name = ModularityConfiguration.Properties.MODULARITY,
        havingValue = ModularityConfiguration.Properties.MONOLITH)
@EnableSmartLabMonolith
public class TestMonolithConfiguration { }
