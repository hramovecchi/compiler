grammar Grammar;

evaluate             : (digit | id | ignore | lineComment)* EOF ;

// TERMINALS
NUMBER               : '0'..'9' ;
LETTER               : [A-Za-z] ;
SYMBOL               : [$_&] ;

WHITESPACE           : [ \t\r] ;
NEW_LINE             : '\n' ;

ADD                  : '+' ;
SUB                  : '-' ;
MUL                  : '*' ;
DIV                  : '/' ;
ASSIGN               : ':=';
GT                   : '>';
LT                   : '<';
LE                   : '<=';
GE                   : '>=';
EQUAL                : '==';
NOTEQUAL             : '^=';

LPAREN               : '(';
RPAREN               : ')';
LBRACE               : '{';
RBRACE               : '}';
SEMI                 : ';';
COMMA                : ',';
DOT                  : '.';

// NON-TERMINALS
digit                : NUMBER | digit NUMBER ;
id                   : LETTER |  id LETTER | id NUMBER | id SYMBOL ;
ignore               : WHITESPACE | NEW_LINE | ignore WHITESPACE | ignore NEW_LINE ;

comment              : (NUMBER | LETTER | SYMBOL | WHITESPACE | ADD | SUB | MUL | DIV ) | comment (NUMBER | LETTER | SYMBOL | WHITESPACE | ADD | SUB | MUL | DIV ) ;
lineComment          : MUL MUL comment NEW_LINE | MUL MUL comment EOF ;

