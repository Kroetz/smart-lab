package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlabcore.data.action.speechtotext.ISpeechToTextService;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class SpeechToTextServiceResolver extends AbstractResolver<String, ISpeechToTextService> {

    public SpeechToTextServiceResolver(Optional<List<ISpeechToTextService>> speechToTextServices) {
        super(getSpeechToTextServicesById(speechToTextServices));
    }

    private static Set<Map.Entry<String, ISpeechToTextService>> getSpeechToTextServicesById(Optional<List<ISpeechToTextService>> speechToTextServices) {
        return speechToTextServices
                .map(services -> services
                        .stream()
                        .collect(toMap(ISpeechToTextService::getServiceId, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String speechToTextService) {
        return format("The speech-to-text service \"%s\" is unknown", speechToTextService);
    }
}
