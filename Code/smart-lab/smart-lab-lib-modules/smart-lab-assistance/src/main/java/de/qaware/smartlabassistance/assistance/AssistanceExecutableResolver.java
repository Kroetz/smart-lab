package de.qaware.smartlabassistance.assistance;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceExecutableResolver extends AbstractResolver<String, IAssistanceExecutable> {

    public AssistanceExecutableResolver(List<IAssistanceExecutable> assistances) {
        super(AssistanceExecutableResolver.getAssistancesById(assistances));
    }

    private static Map<String, IAssistanceExecutable> getAssistancesById(List<IAssistanceExecutable> assistances) {
        Map<String, IAssistanceExecutable> assistancesById = new HashMap<>();
        for(IAssistanceExecutable assistance : assistances) {
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

