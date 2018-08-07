package de.qaware.smartlab.assistance.assistances.info.miscellaneous.resolver;

import de.qaware.smartlab.assistance.assistances.info.generic.IAssistanceInfo;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;

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

    @Override
    protected String getErrorMessage(String assistanceId) {
        return format("The assistance \"%s\" is unknown", assistanceId);
    }
}
