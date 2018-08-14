package de.qaware.smartlab.core.constant;

import de.qaware.smartlab.core.parser.antlr.generated.EventConfigurationLanguageLexer;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Vocabulary;

import static de.qaware.smartlab.core.util.StringUtils.trimEnclosing;

@Slf4j
public abstract class EventConfigurationLanguage {

    private static final Vocabulary VOCABULARY = EventConfigurationLanguageLexer.VOCABULARY;
    public static final String SIGNAL_SEQUENCE =
            trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.SIGNAL_SEQUENCE));
    // TODO: Find a better way to get the string literals of composed lexer rules directly from the generated ANTLR classes instead of composing them manually here.
    public static final String CONFIG_TAG_BEGIN =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.CONFIG_TAG_BEGIN_IDENTIFIER));
    public static final String CONFIG_TAG_END =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.CONFIG_TAG_END_IDENTIFIER));
    public static final String WORKGROUP_TAG =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.WORKGROUP_TAG_IDENTIFIER));
    public static final String AGENDA_TAG_BEGIN =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.AGENDA_TAG_BEGIN_IDENTIFIER));
    public static final String AGENDA_TAG_END =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.AGENDA_TAG_END_IDENTIFIER));
    public static final String ASSISTANCES_TAG_BEGIN =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.ASSISTANCES_TAG_BEGIN_IDENTIFIER));
    public static final String ASSISTANCES_TAG_END =
            SIGNAL_SEQUENCE + trimEnclosing(VOCABULARY.getLiteralName(EventConfigurationLanguageLexer.ASSISTANCES_TAG_END_IDENTIFIER));
}
