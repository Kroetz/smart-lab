package de.qaware.smartlabassistance.assistance.controllable.factory;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceControllableFactoryResolver extends AbstractResolver<String, IAssistanceControllableFactory> {

    public AssistanceControllableFactoryResolver(List<IAssistanceControllableFactory> factories) {
        super(AssistanceControllableFactoryResolver.getFactoriesByAssistanceId(factories));
    }

    private static Map<String, IAssistanceControllableFactory> getFactoriesByAssistanceId(List<IAssistanceControllableFactory> factories) {
        Map<String, IAssistanceControllableFactory> factoriesByAssistanceId = new HashMap<>();
        for(IAssistanceControllableFactory factory : factories) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(factory.getAssociatedAssistanceId());
            identifiers.addAll(factory.getAssociatedAssistanceAliases());
            for(String identifier : identifiers) {
                factoriesByAssistanceId.put(identifier, factory);
            }
        }
        return factoriesByAssistanceId;
    }
}
