parser grammar LangParser;

options {
    tokenVocab = LangLexer;
}

prog
    : stmt (stmt)* EOF
    ;

stmtList
    : (stmt)*
    ;

stmt
    : assignStmt ((EOL | SEMICOLON)+ | EOF)
    | ifStmt ((EOL | SEMICOLON)+ | EOF)
    | whileStmt ((EOL | SEMICOLON)+ | EOF)
    | printStmt ((EOL | SEMICOLON)+ | EOF)
    ;

expr
    : IDENTIFIER                          # ExprIdentifier
    | DECIMAL_LITERAL                     # ExprDecimalLiteral
    | expr op=(EQUALS | NOT_EQUALS) expr  # ExprCompare
    | expr op=(MUL | DIV) expr            # ExprMulDiv
    | expr op=(PLUS | MINUS) expr         # ExprAddSub
    | L_PAREN expr R_PAREN                # ExprParentheses
    ;

block
    : L_CURLY EOL stmtList R_CURLY 
    ;

ifStmt
    : IF L_PAREN cond=expr R_PAREN block                                # IfStmtIf
    | IF L_PAREN cond=expr R_PAREN ifBlock=block ELSE elseBlock=block # IfStmtIfElse
    ;

assignStmt
    : IDENTIFIER ASSIGN expr
    ;

whileStmt
    : WHILE L_PAREN cond=expr R_PAREN block
    ;

printStmt
    : PRINT L_PAREN expr R_PAREN
    ;
