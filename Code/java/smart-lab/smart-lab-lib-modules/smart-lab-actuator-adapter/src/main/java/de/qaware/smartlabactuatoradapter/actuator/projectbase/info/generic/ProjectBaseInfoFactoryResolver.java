package de.qaware.smartlabactuatoradapter.actuator.projectbase.info.generic;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class ProjectBaseInfoFactoryResolver extends AbstractResolver<String, IProjectBaseInfoFactory> {

    public ProjectBaseInfoFactoryResolver(Optional<List<IProjectBaseInfoFactory>> factories) {
        super(getFactoriesByProjectBaseId(factories));
    }

    private static Set<Map.Entry<String, IProjectBaseInfoFactory>> getFactoriesByProjectBaseId(Optional<List<IProjectBaseInfoFactory>> projectBaseInfoFactories) {
        return projectBaseInfoFactories
                .map(factories -> factories
                            .stream()
                            .collect(toMap(IProjectBaseInfoFactory::getServiceId, identity()))
                            .entrySet())
                .orElse(emptySet());
    }
}
