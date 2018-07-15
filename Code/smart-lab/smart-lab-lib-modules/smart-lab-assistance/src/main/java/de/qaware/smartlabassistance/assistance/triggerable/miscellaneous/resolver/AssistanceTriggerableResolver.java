package de.qaware.smartlabassistance.assistance.triggerable.miscellaneous.resolver;

import de.qaware.smartlabassistance.assistance.triggerable.generic.IAssistanceTriggerable;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceTriggerableResolver extends AbstractResolver<String, IAssistanceTriggerable> {

    public AssistanceTriggerableResolver(List<IAssistanceTriggerable> assistances) {
        super(AssistanceTriggerableResolver.getAssistancesById(assistances));
    }

    private static Set<Map.Entry<String, IAssistanceTriggerable>> getAssistancesById(List<IAssistanceTriggerable> assistances) {
        Set<Map.Entry<String, IAssistanceTriggerable>> assistancesById = new HashSet<>();
        for(IAssistanceTriggerable assistance : assistances) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(assistance.getAssistanceId());
            identifiers.addAll(assistance.getAssistanceIdAliases());
            for(String identifier : identifiers) {
                assistancesById.add(new AbstractMap.SimpleImmutableEntry<>(identifier, assistance));
            }
        }
        return assistancesById;
    }
}
