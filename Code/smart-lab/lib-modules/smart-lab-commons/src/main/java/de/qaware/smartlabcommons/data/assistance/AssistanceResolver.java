package de.qaware.smartlabcommons.data.assistance;

import de.qaware.smartlabcommons.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceResolver extends AbstractResolver<String, IAssistance> {

    public AssistanceResolver(List<IAssistance> assistances) {
        super(AssistanceResolver.getAssistancesById(assistances));
    }

    private static Map<String, IAssistance> getAssistancesById(List<IAssistance> assistances) {
        Map<String, IAssistance> assistancesById = new HashMap<>();
        for(IAssistance assistance : assistances) {
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
