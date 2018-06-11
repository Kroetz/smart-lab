package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceControllableResolver extends AbstractResolver<String, IAssistanceControllable> {

    public AssistanceControllableResolver(List<IAssistanceControllable> assistances) {
        super(AssistanceControllableResolver.getAssistancesById(assistances));
    }

    private static Map<String, IAssistanceControllable> getAssistancesById(List<IAssistanceControllable> assistances) {
        Map<String, IAssistanceControllable> assistancesById = new HashMap<>();
        for(IAssistanceControllable assistance : assistances) {
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

