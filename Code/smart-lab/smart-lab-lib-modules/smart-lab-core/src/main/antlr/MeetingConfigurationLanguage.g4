grammar MeetingConfigurationLanguage;

// ----- Parser rules -----

meetingConfiguration
    : CONFIG_TAG_BEGIN statement* CONFIG_TAG_END EOF
    ;

statement
    : assignment
    | section
    ;

assignment
    : WORKGROUP_TAG EQUALS workgroupId=STRING #WorkgroupAssignment
//  | ...   (Add other assignments here)
    ;

section
    : agendaSection
    | assistancesSection
    ;

agendaSection
    : AGENDA_TAG_BEGIN agendaItems AGENDA_TAG_END
    ;

agendaItems
    : agendaItemList+=agendaItem*
    ;

agendaItem
    : content=STRING
    ;

assistancesSection
    : ASSISTANCES_TAG_BEGIN assistances ASSISTANCES_TAG_END
    ;

assistances
    : assistanceList+=assistance*
    ;

assistance
    : assistanceId=IDENTIFIER LPAREN assistanceArgs RPAREN
    ;

assistanceArgs
    : assistanceArgList+=assistanceArg (COMMA assistanceArgList+=assistanceArg)* # PopulatedAssistanceArgs
    |                                                                            # EmptyAssistanceArgs
    ;

assistanceArg
    : argKey=IDENTIFIER COLON argValue=STRING
    ;

// ----- Lexer rules -----

IDENTIFIER:                         [a-z]+[a-zA-Z0-9]* ;
INTEGER:                            [0-9]+ ;
STRING
    : QUOTES (~'"')* QUOTES
    {setText(getText().substring(1, getText().length()-1));}    // Removes the leading and trailing quotes from the string
    ;
CONFIG_TAG_BEGIN:                   SIGNAL_SEQUENCE CONFIG_TAG_BEGIN_IDENTIFIER ;
CONFIG_TAG_BEGIN_IDENTIFIER:        'smart-lab-config-begin' ;
CONFIG_TAG_END:                     SIGNAL_SEQUENCE CONFIG_TAG_END_IDENTIFIER ;
CONFIG_TAG_END_IDENTIFIER:          'smart-lab-config-end' ;
WORKGROUP_TAG:                      SIGNAL_SEQUENCE WORKGROUP_TAG_IDENTIFIER ;
WORKGROUP_TAG_IDENTIFIER:           'workgroup' ;
AGENDA_TAG_BEGIN:                   SIGNAL_SEQUENCE AGENDA_TAG_BEGIN_IDENTIFIER ;
AGENDA_TAG_BEGIN_IDENTIFIER:        'agenda-begin' ;
AGENDA_TAG_END:                     SIGNAL_SEQUENCE AGENDA_TAG_END_IDENTIFIER ;
AGENDA_TAG_END_IDENTIFIER:          'agenda-end' ;
ASSISTANCES_TAG_BEGIN:              SIGNAL_SEQUENCE ASSISTANCES_TAG_BEGIN_IDENTIFIER ;
ASSISTANCES_TAG_BEGIN_IDENTIFIER:   'assistances-begin' ;
ASSISTANCES_TAG_END:                SIGNAL_SEQUENCE ASSISTANCES_TAG_END_IDENTIFIER ;
ASSISTANCES_TAG_END_IDENTIFIER:     'assistances-end' ;
SIGNAL_SEQUENCE:                    '@' ;
LPAREN:                             '(' ;
RPAREN:                             ')' ;
LBRACE:                             '{' ;
RBRACE:                             '}' ;
LBRACK:                             '[' ;
RBRACK:                             ']' ;
COMMA:                              ',' ;
DOT:                                '.' ;
COLON:                              ':' ;
SEMICOLON:                          ';' ;
EQUALS:                             '=' ;
QUOTES:                             '"' ;
WHITESPACE
    : [ \t\r\n\u000C]+ -> skip
    ;
COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;
LINE_COMMENT
    : '//' ~[\r\n]* -> channel(HIDDEN)
    ;