lexer grammar BasicLexer;

COMMENT : '#' ~[\r\n]* [\r\n] -> skip ;
IMPORT  : 'import ' ;

PLUS:  '+' ;
MINUS: '-' ;

BEGIN:	'begin' ;
END:	'end' ;

OPEN_PARENTHESES : '(' ;
CLOSE_PARENTHESES : ')' ;
OPEN_BRACKETS : '[' ;
CLOSE_BRACKETS : ']' ;

COMMA : ',' ;
IS : 'is' ;
SKIP : 'skip';
EQUALS : '=' ;

READ : 'read' ;
RETURN : 'return' ;
EXIT : 'exit' ;
PRINT : 'print' ;
PRINTLN : 'println' ;
FREE : 'free' ;

IF : 'if' ;
THEN : 'then' ;
ELSE : 'else' ;
FI : 'fi' ;
QUEST:'?';
COLON:':';

WHILE : 'while' ;
DO : 'do' ;
DONE : 'done' ;

DWHILE : 'dwhile' ;
DDO : 'ddo' ;
DDONE : 'ddone' ;

FOR : 'for' ;
FDO : 'fdo' ;
FDONE : 'fdone' ;

SEMICOLON : ';' ;

NEWPAIR : 'newpair' ;
CALL : 'call' ;
FST : 'fst' ;
SND : 'snd' ;

INT : 'int' ;
BOOL : 'bool' ;
CHAR : 'char' ;
STRING : 'string' ;
PAIR : 'pair' ;

EXCLAMATION : '!' ;
LEN : 'len' ;
ORD : 'ord' ;
TOINT : 'toInt' ;

STAR : '*' ;
DIVIDE : '/' ;
MOD : '%' ;
GREATER_THAN : '>' ;
GREATER_THAN_EQUAL : '>=' ;
LESS_THAN : '<' ;
LESS_THAN_EQUAL : '<=' ;
EQUAL : '==' ;
NOT_EQUAL : '!=' ;
AND : '&&' ;
OR : '||' ;

fragment UNDERSCORE : '_' ;

fragment DIGIT : '0'..'9';
INTEGER_LIT : DIGIT+ ;
TRUE : 'true' ;
FALSE : 'false' ;
SINGLE_QUOTE : '\'' ;
DOUBLE_QUOTE : '"' ;
fragment ESCAPED_BACKSLASH : '\\' '\\' ;
fragment END_OF_STRING : '\\' '0' ;
fragment B : '\\' 'b' ;
fragment T : '\\' 't' ;
fragment N : '\\' 'n' ;
fragment F : '\\' 'f' ;
fragment R : '\\' 'r' ;
fragment ESCAPE_CHARACTER : (END_OF_STRING | B | T | N | F | R
			    |( '\\' SINGLE_QUOTE ) | ( '\\' DOUBLE_QUOTE ) 
			    |(ESCAPED_BACKSLASH)) ;

fragment CHAR_INPUT : ~('\'' | '"' | '\\') | (ESCAPE_CHARACTER) ;
CHAR_LITERAL : SINGLE_QUOTE CHAR_INPUT SINGLE_QUOTE ;
STRING_LITERAL  : DOUBLE_QUOTE CHAR_INPUT* DOUBLE_QUOTE ;

NULL : 'null' ;
HASH : '#' ;


fragment SMALL_ALPHA : 'a'..'z' ;
fragment LARGE_ALPHA : 'A'..'Z' ;
IDENT : (UNDERSCORE|SMALL_ALPHA|LARGE_ALPHA)(UNDERSCORE|SMALL_ALPHA|LARGE_ALPHA|DIGIT)*;
WS : (' '|'\t'|'\n'|'\r') -> skip ;


