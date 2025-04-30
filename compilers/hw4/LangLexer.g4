lexer grammar LangLexer;

// Keywords
IF:    'if';
ELSE:  'else';
WHILE: 'while';
PRINT: 'print';

// Relation operators
EQUALS:     '==';
NOT_EQUALS: '!=';

// Punctuation
L_PAREN:   '(';
R_PAREN:   ')';
L_CURLY:   '{';
R_CURLY:   '}';
ASSIGN:    '=';
SEMICOLON: ';';

// Number literals
DECIMAL_LITERAL: [+-]?[1-9][0-9]* | '0';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Arithmetic operators
PLUS:  '+';
MINUS: '-';
MUL:   '*';
DIV:   '/';

// Whitespaces
WS:  [ \t]+  -> channel(HIDDEN);
EOL: [\r\n]+;
