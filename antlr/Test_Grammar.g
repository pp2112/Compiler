grammar Test_Grammar;

program : 'begin' func* stat 'end' ;

func : type ident '(' (paramList)? ')' 'is' stat 'end' ;

paramList : param (',' param)* ;

param : type ident ;

stat :  SKIP 
  	| type ident EQUALS assignRhs 
	| assignLhs EQUALS assignRhs
	| READ assignLhs
	| FREE expr
	| RETURNS expr
	| EXIT expr
	| PRINT expr
	| PRINTLN expr
	| IF expr THEN stat ELSE stat FI
	| WHILE expr DO stat DONE
	| BEGIN stat END
	| stat SEMICOLON stat
	;

assignLhs : ident
	    | arrayElem
	    | pairElem
	    ;

assignRhs : expr
	    | arrayLiter
 	    | NEWPAIR OPEN_PARENTHESES expr SEMICOLON expr CLOSE_PARENTHESES
	    | pairElem
	    | CALL ident OPEN_PARENTHESES (argList)? CLOSE_PARENTHESES
	    ;

argList : expr (COMMA expr)*;

pairElem : FST expr
	   |SND expr
	   ;

type :	baseType
	| arrayType
	| pairType
	;

baseType :  INT
            | BOOL
	    | CHAR
	    | STRING
	    ;     	

arrayType : (UNDERSCORE) type OPEN_BRACKETS CLOSE_BRACKETS ;

pairType :  PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES;

pairElemType : baseType
	       | arrayType
	       | PAIR
	       ;

expr :	intLiter
	| boolLiter
	| charLiter
	| strLiter
	| pairLiter
	| ident
	| arrayElem
	| unaryOper expr
	| expr binaryOper expr
	| OPEN_PARENTHESES expr CLOSE_PARENTHESES
	;

unaryOper : EXCLAMATION
	     | MINUS
	     | LEN
	     | ORD
	     | TOINT
	     ;

binaryOper : STAR
	      | BACKSLASH
	      | MOD
	      | PLUS
	      | MINUS
	      | GREATER_THAN
	      | GREATER_THAN_EQUAL
	      | LESS_THAN
	      | LESS_THAN_EQUAL
	      | EQUAL
	      | NOT_EQUAL
	      | AND
	      | OR
	      ;

ident : (UNDERSCORE
	| SMALL_ALPHA
	| LARGE_ALPHA)
	(UNDERSCORE
	| SMALL_ALPHA
	| LARGE_ALPHA
	| NUMBERS)*
	;

arrayElem : (UNDERSCORE) expr OPEN_BRACKETS expr CLOSE_BRACKETS ;
intLiter  : intSign? digit+;
digit	  : NUMBERS ;
intSign   : PLUS | MINUS ;
boolLiter : TRUE | FALSE ;
charLiter : SINGLE_QUOTE character SINGLE_QUOTE ;
strLiter  : DOUBLE_QUOTE character* DOUBLE_QUOTE ;
character  : SMALL_ALPHA | LARGE_ALPHA ;
escapedChar :  ZERO | B | T | N | F | R 
		| DOUBLE_QUOTE | SINGLE_QUOTE | BACKSLASH ;
arrayLiter : OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS ;
pairLiter : NULL ;
comment : HASH (SMALL_ALPHA | LARGE_ALPHA | NUMBERS)*;


// EOF indicates that the program must consume to the end of the input.
prog: (expr)*  EOF ;


// Exit Codes from 0 - 255;