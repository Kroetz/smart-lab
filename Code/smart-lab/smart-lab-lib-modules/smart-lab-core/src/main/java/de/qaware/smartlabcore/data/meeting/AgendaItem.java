package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.DOUBLE_QUOTED_TEMPLATE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.NEW_LINE;
import static de.qaware.smartlabcore.miscellaneous.StringUtils.TAB;
import static java.lang.String.format;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaItem implements IAgendaItem {

    // TODO: Still needed?
    // Setting this field manually is needed due to a Jackson bug with Java Optionals (see https://stackoverflow.com/questions/49071166/jackson-java-util-optional-serialization-does-not-include-type-id)
    @JsonProperty
    private String type = this.getClass().getName();

    private String content;

    @Override
    public String toConfigLangString() {
        return TAB + TAB + format(DOUBLE_QUOTED_TEMPLATE, this.content) + NEW_LINE;
    }
}
