package de.qaware.smartlabcommons.data.assistance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceResolver implements IAssistanceResolver {

    private final Map<String, IAssistance> assistancesById;

    public AssistanceResolver(List<IAssistance> assistances) {
        assistancesById = new HashMap<>();
        for(IAssistance assistance : assistances) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(assistance.getAssistanceId());
            identifiers.addAll(assistance.getAssistanceAliases());
            for(String identifier : identifiers) {
                assistancesById.put(identifier, assistance);
            }
        }
    }

    public Optional<IAssistance> resolveAssistanceId(String assistanceId) {
        return Optional.ofNullable(assistancesById.get(assistanceId));
    }
}
