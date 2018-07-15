package de.qaware.smartlabcore.data.device.microphone;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MicrophoneAdapterResolver extends AbstractResolver<String, IMicrophoneAdapter> {

    public MicrophoneAdapterResolver(List<IMicrophoneAdapter> microphoneAdapters) {
        super(MicrophoneAdapterResolver.getMicrophoneAdaptersByType(microphoneAdapters));
    }

    private static Set<Map.Entry<String, IMicrophoneAdapter>> getMicrophoneAdaptersByType(List<IMicrophoneAdapter> microphoneAdapters) {
        return microphoneAdapters.stream()
                .collect(Collectors.toMap(IMicrophoneAdapter::getDeviceType, Function.identity()))
                .entrySet();
    }
}
