package de.qaware.smartlab.actuator.adapter.adapters.microphone;

import de.qaware.smartlab.core.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class MicrophoneAdapterResolver extends AbstractResolver<String, IMicrophoneAdapter> {

    public MicrophoneAdapterResolver(List<IMicrophoneAdapter> microphoneAdapters) {
        super(getMicrophoneAdaptersByType(microphoneAdapters));
    }

    private static Set<Map.Entry<String, IMicrophoneAdapter>> getMicrophoneAdaptersByType(List<IMicrophoneAdapter> microphoneAdapters) {
        return microphoneAdapters.stream()
                .collect(toMap(IMicrophoneAdapter::getDeviceType, identity()))
                .entrySet();
    }

    @Override
    protected String getErrorMessage(String microphoneType) {
        return format("The microphone type \"%s\" is unknown", microphoneType);
    }
}