grammar MeetingConfigurationLanguage;

// ----- Parser rules -----

meetingConfiguration
    : statement*
    ;

statement
    : assignment
    | section
    ;

assignment
    : SPECIAL_CHAR WORKGROUP_IDENTIFIER EQUALS workgroupId=STRING #WorkgroupAssignment
//  | ...   (Add other assignments here)
    ;

section
    : agendaSection
    | assistancesSection
    ;

agendaSection
    : AGENDA_TAG agendaItems AGENDA_TAG
    ;

agendaItems
    : agendaItemList+=agendaItem*
    ;

agendaItem
    : content=STRING
    ;

assistancesSection
    : ASSISTANCES_TAG assistances ASSISTANCES_TAG
    ;

assistances
    : assistanceList+=assistance (assistanceList+=assistance)* # PopulatedAssistances
    |                                                          # EmptyAssistances
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

IDENTIFIER:         [a-z]+[a-zA-Z0-9]* ;
INTEGER:            [0-9]+ ;
STRING
    :   QUOTES (~'"')* QUOTES
    {setText(getText().substring(1, getText().length()-1));}    // Removes the leading and trailing quotes from the string
    ;
WORKGROUP_IDENTIFIER:   'workgroup' ;
AGENDA_TAG:             SPECIAL_CHAR 'agenda' ;
ASSISTANCES_TAG:        SPECIAL_CHAR 'assistances' ;
SPECIAL_CHAR:           AT ;
LPAREN:                 '(' ;
RPAREN:                 ')' ;
LBRACE:                 '{' ;
RBRACE:                 '}' ;
LBRACK:                 '[' ;
RBRACK:                 ']' ;
COMMA:                  ',' ;
DOT:                    '.' ;
COLON:                  ':' ;
SEMICOLON:              ';' ;
EQUALS:                 '=' ;
AT:                     '@' ;
QUOTES:                 '"' ;
WHITESPACE
    :   [ \t\r\n\u000C]+ -> skip
    ;
COMMENT
    :   '/*' .*? '*/' -> channel(HIDDEN)
    ;
LINE_COMMENT
    :   '//' ~[\r\n]* -> channel(HIDDEN)
    ;