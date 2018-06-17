package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class MinuteTakingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "minute taking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "minute-taking",
            "minuteTaking").collect(Collectors.toSet());

    public MinuteTakingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    // TODO: Which annotation can be removed?
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Slf4j
    public static class Configuration implements IAssistanceConfiguration {

        private DeviceId deviceId;
    }
}
