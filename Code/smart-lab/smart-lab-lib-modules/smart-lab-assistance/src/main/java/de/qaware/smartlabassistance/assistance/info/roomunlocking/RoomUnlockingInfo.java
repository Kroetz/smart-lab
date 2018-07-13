package de.qaware.smartlabassistance.assistance.info.roomunlocking;

import de.qaware.smartlabassistance.assistance.info.generic.AbstractAssistanceInfo;
import de.qaware.smartlabcore.data.assistance.IAssistanceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.NEW_LINE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.PARENTHESES_TEMPLATE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.TAB;
import static java.lang.String.*;

@Component
@Slf4j
public class RoomUnlockingInfo extends AbstractAssistanceInfo {

    public static final String ASSISTANCE_ID = "roomUnlocking";
    // TODO: Simpler with Java 9 (see https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
    public static final Set<String> ASSISTANCE_ALIASES = Stream.of(
            "room-unlocking",
            "room unlocking").collect(Collectors.toSet());

    public RoomUnlockingInfo() {
        super(ASSISTANCE_ID, ASSISTANCE_ALIASES);
    }

    @Override
    public IAssistanceConfiguration createConfiguration(Map<String, String> configProperties) {
        return new Configuration(configProperties);
    }

    // TODO: Which annotation can be removed?
    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public class Configuration extends AbstractAssistanceInfo.AbstractConfiguration {

        private Configuration(Map<String, String> configProperties) {
            // TODO: process config properties
        }

        @Override
        public String getAssistanceId() {
            return RoomUnlockingInfo.this.getAssistanceId();
        }

        @Override
        public String toConfigLangString() {
            // TODO
            return TAB + TAB + getAssistanceId() + format(PARENTHESES_TEMPLATE, "") + NEW_LINE;
        }
    }
}
