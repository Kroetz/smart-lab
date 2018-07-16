package de.qaware.smartlabassistance.assistance.info.miscellaneous.resolver;

import de.qaware.smartlabassistance.assistance.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class AssistanceInfoResolver extends AbstractResolver<String, IAssistanceInfo> {

    public AssistanceInfoResolver(List<IAssistanceInfo> assistances) {
        super(getAssistancesById(assistances));
    }

    private static Set<Map.Entry<String, IAssistanceInfo>> getAssistancesById(List<IAssistanceInfo> assistances) {
        Set<Map.Entry<String, IAssistanceInfo>> assistancesById = new HashSet<>();
        for(IAssistanceInfo assistance : assistances) {
            Set<String> identifiers = new HashSet<>();
            identifiers.add(assistance.getAssistanceId());
            identifiers.addAll(assistance.getAssistanceIdAliases());
            identifiers.add(assistance.getAssistanceCommand());
            identifiers.addAll(assistance.getAssistanceCommandAliases());
            for(String identifier : identifiers) {
                assistancesById.add(new AbstractMap.SimpleImmutableEntry<>(identifier, assistance));
            }
        }
        return assistancesById;
    }
}
