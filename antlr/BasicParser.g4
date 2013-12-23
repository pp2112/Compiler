parser grammar BasicParser;
import BasicLexer ;
program : (COMMENT)* (IMPORT (stringLiter)* SEMICOLON)? t_begin (COMMENT)* func* (COMMENT)* stat (COMMENT)* t_end (COMMENT)*;

func : type ident OPEN_PARENTHESES (paramList)? CLOSE_PARENTHESES t_is stat t_end ;

paramList : param (COMMA param)* ;

param : type ident ;

stat : SKIP                                       #stat_SKIP
     | type ident EQUALS assignRhs                #stat_type_ident_equals_assignRHS
     | assignLhs unaryAssignOp                    #stat_assignLHS_unaryAssignOp
     | assignLhs assignOp (unaryAssignOp)? assignRhs (unaryAssignOp)?
                                                  #stat_assignLHS_assignOp_assignRHS
     | READ assignLhs                             #stat_Read_assignLHS
     | FREE expr                                  #stat_Free_expr
     | EXIT expr                                  #stat_Exit_expr
     | PRINT expr                                 #stat_Print_expr
     | PRINTLN expr                               #stat_Println_expr
     | RETURN expr                                #stat_Return_expr
     | t_if expr t_then stat t_else stat t_fi	    #stat_if_expr_then_stat_else_stat_fi
     | t_while expr t_do stat t_done              #stat_while_expr_do_stat_done
     | t_ddo stat t_dwhile expr t_ddone              #stat_ddo_stat_dwhile_expr_ddone
     | t_for OPEN_PARENTHESES stat t_semicolon expr t_semicolon stat CLOSE_PARENTHESES t_fdo stat t_fdone
                                                  #stat_for_stat_expr_stat_fdo_stat_fdone
     | t_begin stat t_end                         #stat_begin_stat_end
     | stat SEMICOLON stat                        #stat_semicolon_stat
     ;

unaryAssignOp : PLUS PLUS    #unaryAssignOp_plus
              | MINUS MINUS  #unaryAssignOp_minus
              ;

assignOp : EQUALS         #assignOp_equals
         | PLUS EQUALS    #assignOp_plus_equals
         | MINUS EQUALS   #assignOp_minus_equals
         ;

assignLhs : ident         #assignLHS_ident
          | expr          #assignLHS_expr
          | pairElem      #assignLHS_pairElem
          ;

assignRhs : expr                                                        #assignRHS_expr
          | arrayLiter                                                  #assignRHS_arrayLit
          | NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES	#assignRHS_newPair
          | pairElem                                                    #assignRHS_pairElem
          | CALL ident OPEN_PARENTHESES (argList)? CLOSE_PARENTHESES    #assignRHS_call
          | expr QUEST assignRhs COLON assignRhs                        #assignRHS_if_then_else
          ;

argList : expr (COMMA expr)*;

pairElem : FST expr   #pairElem_FST_expr
         | SND expr   #pairElem_SND_expr
    	 ;

type : baseType                             #type_base
     | type OPEN_BRACKETS CLOSE_BRACKETS    #type_arr
     | pairType                             #type_pair
     ;

baseType : INT          #base_INT
         | BOOL         #base_BOOL
         | CHAR         #base_CHAR
         | STRING       #base_STR
         ;     	

pairType : PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES;

pairElemType : baseType     #pair_base_t
             | type         #pair_type
             | PAIR         #pair_PAIR
             ;

expr : intLiter                                 #expr_int
     | boolLiter                                #expr_bool
     | charLiter                                #expr_char
     | stringLiter                              #expr_str
     | pairLiter                                #expr_pairlit
     | ident                                    #expr_ident
     | expr OPEN_BRACKETS expr CLOSE_BRACKETS   #expr_arr
     | unaryOper expr                           #expr_unary
     | expr MINUS expr                          #expr_MINUS
     | expr PLUS expr                           #expr_PLUS
     | expr STAR expr                           #expr_MULT
     | expr DIVIDE expr                         #expr_DIV
     | expr MOD expr                            #expr_MOD
     | expr GREATER_THAN expr                   #expr_GT
     | expr GREATER_THAN_EQUAL expr             #expr_GTEQ
     | expr LESS_THAN expr                      #expr_LT
     | expr LESS_THAN_EQUAL expr                #expr_LTEQ
     | expr EQUAL expr                          #expr_EQ
     | expr NOT_EQUAL expr                      #expr_NEQ
     | expr OR expr                             #expr_OR
     | expr AND expr                            #expr_AND
     | OPEN_PARENTHESES expr CLOSE_PARENTHESES  #expr_expr
     ;

unaryOper : EXCLAMATION   #unary_exc
          | MINUS         #unary_minus
          | LEN           #unary_len
          | ORD           #unary_ord
          | TOINT         #unary_int
          ;

ident       : IDENT ;
intSign     : PLUS | MINUS ;
boolLiter   : TRUE | FALSE ;
charLiter   : CHAR_LITERAL ;
intLiter    : intSign? INTEGER_LIT;
stringLiter : STRING_LITERAL ;
pairLiter   : NULL ;
arrayLiter  : OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS
	     |OPEN_BRACKETS+ (arrayLiter (COMMA arrayLiter)*)? CLOSE_BRACKETS+;

t_if   : IF	     #terminal_if;
t_then : THEN	   #terminal_then;
t_else : ELSE	   #terminal_else;
t_fi   : FI	     #terminal_fi;
t_while: WHILE	 #terminal_while;
t_do   : DO	     #terminal_do;
t_done : DONE	   #terminal_done;
t_dwhile: WHILE	 #terminal_dwhile;
t_ddo   : DO	     #terminal_ddo;
t_ddone : DONE	   #terminal_ddone;
t_begin: BEGIN   #terminal_begin;
t_end  : END     #terminal_end;
t_is   : IS      #terminal_is;
t_for  : FOR     #terminal_for;
t_fdo  : DO     #terminal_fdo;
t_fdone: DONE   #terminal_fdone;
t_semicolon: SEMICOLON   #terminal_semicolon;

// EOF indicates that the program must consume to the end of the input.

// Exit Codes from 0 - 255;







