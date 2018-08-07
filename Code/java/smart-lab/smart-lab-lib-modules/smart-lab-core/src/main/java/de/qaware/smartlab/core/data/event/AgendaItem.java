package de.qaware.smartlab.core.data.event;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static de.qaware.smartlab.core.miscellaneous.StringUtils.*;
import static java.lang.String.format;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public class AgendaItem implements IAgendaItem {

    private final String content;

    public static AgendaItem of(String content) {
        return new AgendaItem(content);
    }

    @Override
    public String toConfigLangString() {
        return TAB + TAB + format(DOUBLE_QUOTED_TEMPLATE, this.content) + NEW_LINE;
    }
}
