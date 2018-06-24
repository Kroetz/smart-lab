package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceInfo;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceInfoResolver extends AbstractResolver<String, IAssistanceInfo> {

    public AssistanceInfoResolver(List<IAssistanceInfo> assistances) {
        super(AssistanceInfoResolver.getAssistancesById(assistances));
    }

    private static Map<String, IAssistanceInfo> getAssistancesById(List<IAssistanceInfo> assistances) {
        Map<String, IAssistanceInfo> assistancesById = new HashMap<>();
        for(IAssistanceInfo assistance : assistances) {
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
