package de.qaware.smartlab.actuator.adapter.adapters.speechtotext;

import de.qaware.smartlab.core.data.action.speechtotext.ISpeechToTextAdapter;
import de.qaware.smartlab.core.data.generic.AbstractResolver;
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
public class SpeechToTextAdapterResolver extends AbstractResolver<String, ISpeechToTextAdapter> {

    public SpeechToTextAdapterResolver(Optional<List<ISpeechToTextAdapter>> speechToTextAdapters) {
        super(getSpeechToTextAdaptersById(speechToTextAdapters));
    }

    private static Set<Map.Entry<String, ISpeechToTextAdapter>> getSpeechToTextAdaptersById(Optional<List<ISpeechToTextAdapter>> speechToTextAdapters) {
        return speechToTextAdapters
                .map(adapters -> adapters
                        .stream()
                        .collect(toMap(ISpeechToTextAdapter::getDeviceType, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String speechToTextServiceName) {
        return format("The speech-to-text service \"%s\" is unknown", speechToTextServiceName);
    }
}
