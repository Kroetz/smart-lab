package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceTriggerableResolver extends AbstractResolver<String, IAssistanceTriggerable> {

    public AssistanceTriggerableResolver(List<IAssistanceTriggerable> assistances) {
        super(AssistanceTriggerableResolver.getAssistancesById(assistances));
    }

    private static Map<String, IAssistanceTriggerable> getAssistancesById(List<IAssistanceTriggerable> assistances) {
        Map<String, IAssistanceTriggerable> assistancesById = new HashMap<>();
        for(IAssistanceTriggerable assistance : assistances) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(assistance.getAssistanceId());
            identifiers.addAll(assistance.getAssistanceAliases());
            for(String identifier : identifiers) {
                assistancesById.put(identifier, assistance);
            }
        }
        return assistancesById;
    }
}
