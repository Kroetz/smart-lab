package de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.generic;

import de.qaware.smartlab.core.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class ProjectBaseInfoFactoryResolver extends AbstractResolver<String, IProjectBaseInfoFactory> {

    public ProjectBaseInfoFactoryResolver(Optional<List<IProjectBaseInfoFactory>> factories) {
        super(getFactoriesByActuatorType(factories));
    }

    private static Set<Map.Entry<String, IProjectBaseInfoFactory>> getFactoriesByActuatorType(Optional<List<IProjectBaseInfoFactory>> projectBaseInfoFactories) {
        return projectBaseInfoFactories
                .map(factories -> factories
                            .stream()
                            .collect(toMap(IProjectBaseInfoFactory::getActuatorType, identity()))
                            .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String actuatorType) {
        return format("The project base type \"%s\" is unknown", actuatorType);
    }
}
