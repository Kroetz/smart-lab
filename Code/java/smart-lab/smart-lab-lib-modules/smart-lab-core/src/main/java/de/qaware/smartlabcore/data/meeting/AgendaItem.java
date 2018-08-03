package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.meeting.dto.AgendaItemDto;
import lombok.*;

import static de.qaware.smartlabcore.miscellaneous.StringUtils.*;
import static java.lang.String.format;

@Getter
@ToString
@EqualsAndHashCode
public class AgendaItem implements IAgendaItem {

    private static final String CONTENT_FIELD_NAME = "content";
    private final String content;

    private AgendaItem(String content) {
        this.content = content;
    }

    @JsonCreator
    public static AgendaItem of(@JsonProperty(CONTENT_FIELD_NAME) String content) {
        return new AgendaItem(content);
    }

    @Override
    public String toConfigLangString() {
        return TAB + TAB + format(DOUBLE_QUOTED_TEMPLATE, this.content) + NEW_LINE;
    }

    @Override
    public AgendaItemDto toDto() {
        return AgendaItemDto.builder()
                .content(this.content)
                .build();
    }
}
