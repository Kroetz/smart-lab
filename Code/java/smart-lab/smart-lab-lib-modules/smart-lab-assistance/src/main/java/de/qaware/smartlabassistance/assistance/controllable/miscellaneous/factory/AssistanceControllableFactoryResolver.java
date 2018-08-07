package de.qaware.smartlabassistance.assistance.controllable.miscellaneous.factory;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;

@Component
@Slf4j
public class AssistanceControllableFactoryResolver extends AbstractResolver<String, IAssistanceControllableFactory> {

    public AssistanceControllableFactoryResolver(List<IAssistanceControllableFactory> factories) {
        super(getFactoriesByAssistanceId(factories));
    }

    private static Set<Map.Entry<String, IAssistanceControllableFactory>> getFactoriesByAssistanceId(List<IAssistanceControllableFactory> factories) {
        Set<Map.Entry<String, IAssistanceControllableFactory>> factoriesByAssistanceId = new HashSet<>();
        for(IAssistanceControllableFactory factory : factories) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(factory.getAssociatedAssistanceId());
            identifiers.addAll(factory.getAssociatedAssistanceIdAliases());
            for(String identifier : identifiers) {
                factoriesByAssistanceId.add(new AbstractMap.SimpleImmutableEntry<>(identifier, factory));
            }
        }
        return factoriesByAssistanceId;
    }

    @Override
    protected String getErrorMessage(String assistanceId) {
        return format("The assistance \"%s\" is unknown", assistanceId);
    }
}
