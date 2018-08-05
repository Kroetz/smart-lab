package de.qaware.smartlabactuatoradapter.actuator.projectbase.info.generic;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class ProjectBaseInfoFactoryResolver extends AbstractResolver<String, IProjectBaseInfoFactory> {

    public ProjectBaseInfoFactoryResolver(List<IProjectBaseInfoFactory> factories) {
        super(getFactoriesByProjectBaseId(factories));
    }

    private static Set<Map.Entry<String, IProjectBaseInfoFactory>> getFactoriesByProjectBaseId(List<IProjectBaseInfoFactory> factories) {
        return factories.stream()
                .collect(toMap(IProjectBaseInfoFactory::getServiceId, identity()))
                .entrySet();
    }
}
