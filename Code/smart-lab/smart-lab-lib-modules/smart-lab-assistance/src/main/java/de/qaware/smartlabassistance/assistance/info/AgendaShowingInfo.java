package de.qaware.smartlabassistance.assistance.info;

import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
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
public class AgendaShowingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "agenda showing";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "agenda-showing",
            "agendaShowing").collect(Collectors.toSet());

    public AgendaShowingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    // TODO: Which annotation can be removed?
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Slf4j
    public static class Configuration implements IAssistanceConfiguration {

        private String deviceId;
    }
}
