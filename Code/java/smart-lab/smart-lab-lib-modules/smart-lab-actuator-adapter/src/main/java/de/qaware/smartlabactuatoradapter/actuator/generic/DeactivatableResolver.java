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
public class DeactivatableResolver extends AbstractResolver<String, IDeactivatable> {

    public DeactivatableResolver(List<IDeactivatable> deactivatables) {
        super(getDeactivatablesByType(deactivatables));
    }

    private static Set<Map.Entry<String, IDeactivatable>> getDeactivatablesByType(List<IDeactivatable> deactivatables) {
        return deactivatables.stream()
                .collect(toMap(IDeactivatable::getDeviceType, identity()))
                .entrySet();
    }
}
