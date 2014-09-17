/**
 * Define a grammar called Hello
 */

grammar Grammar;

evaluate             : (ID | COMMENT)* EOF;

// SKIP
WILDCARDS            : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

// NO TOKENS
fragment LETTER      : ('a'..'z' | 'A'..'Z') ;
fragment NUMBER      : '0'..'9' ;
fragment SYMBOL      : '_' | '&' | '$' ;

// TOKENS
DIGIT                : NUMBER+ ;
ID                   : LETTER (LETTER | NUMBER | SYMBOL)* ;

COMMENT              : ('**') [^\n]* ('\n'|EOF) ;
