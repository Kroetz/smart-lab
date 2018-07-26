package de.qaware.smartlabaction.action.actor.beamer;

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
public class BeamerAdapterResolver extends AbstractResolver<String, IBeamerAdapter> {

    public BeamerAdapterResolver(List<IBeamerAdapter> beamerAdapters) {
        super(getBeamerAdaptersByType(beamerAdapters));
    }

    private static Set<Map.Entry<String, IBeamerAdapter>> getBeamerAdaptersByType(List<IBeamerAdapter> beamerAdapters) {
        return beamerAdapters.stream()
                .collect(toMap(IBeamerAdapter::getDeviceType, identity()))
                .entrySet();
    }
}
