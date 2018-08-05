package de.qaware.smartlabactuatoradapter.actuator.generic;

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
public class ActivatableResolver extends AbstractResolver<String, IActivatable> {

    public ActivatableResolver(List<IActivatable> activatables) {
        super(getActivatablesByType(activatables));
    }

    private static Set<Map.Entry<String, IActivatable>> getActivatablesByType(List<IActivatable> activatables) {
        return activatables.stream()
                .collect(toMap(IActivatable::getDeviceType, identity()))
                .entrySet();
    }
}