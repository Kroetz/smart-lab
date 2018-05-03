package de.qaware.smartlabcommons.data.assistance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AssistanceResolver implements IAssistanceResolver {

    private final Map<String, IAssistance> assistancesById;

    public AssistanceResolver(List<IAssistance> assistances) {
        assistancesById = assistances.stream()
                .collect(Collectors.toMap(IAssistance::getAssistanceId, Function.identity()));
    }

    public Optional<IAssistance> resolveAssistanceId(String assistanceId) {
        return Optional.ofNullable(assistancesById.get(assistanceId));
    }
}
