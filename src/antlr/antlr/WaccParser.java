package antlr;

// Generated from ./Wacc.g4 by ANTLR 4.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WaccParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, IMPORT=2, PLUS=3, MINUS=4, BEGIN=5, END=6, OPEN_PARENTHESES=7, 
		CLOSE_PARENTHESES=8, OPEN_BRACKETS=9, CLOSE_BRACKETS=10, COMMA=11, IS=12, 
		SKIP=13, EQUALS=14, READ=15, RETURN=16, EXIT=17, PRINT=18, PRINTLN=19, 
		FREE=20, IF=21, THEN=22, ELSE=23, FI=24, QUEST=25, COLON=26, WHILE=27, 
		DO=28, DONE=29, DWHILE=30, DDO=31, DDONE=32, FOR=33, FDO=34, FDONE=35, 
		SEMICOLON=36, NEWPAIR=37, CALL=38, FST=39, SND=40, INT=41, BOOL=42, CHAR=43, 
		STRING=44, PAIR=45, EXCLAMATION=46, LEN=47, ORD=48, TOINT=49, STAR=50, 
		DIVIDE=51, MOD=52, GREATER_THAN=53, GREATER_THAN_EQUAL=54, LESS_THAN=55, 
		LESS_THAN_EQUAL=56, EQUAL=57, NOT_EQUAL=58, AND=59, OR=60, INTEGER_LIT=61, 
		TRUE=62, FALSE=63, SINGLE_QUOTE=64, DOUBLE_QUOTE=65, CHAR_LITERAL=66, 
		STRING_LITERAL=67, NULL=68, HASH=69, IDENT=70, WS=71;
	public static final String[] tokenNames = {
		"<INVALID>", "COMMENT", "'import '", "'+'", "'-'", "'begin'", "'end'", 
		"'('", "')'", "'['", "']'", "','", "'is'", "'skip'", "'='", "'read'", 
		"'return'", "'exit'", "'print'", "'println'", "'free'", "'if'", "'then'", 
		"'else'", "'fi'", "'?'", "':'", "'while'", "'do'", "'done'", "'dwhile'", 
		"'ddo'", "'ddone'", "'for'", "'fdo'", "'fdone'", "';'", "'newpair'", "'call'", 
		"'fst'", "'snd'", "'int'", "'bool'", "'char'", "'string'", "'pair'", "'!'", 
		"'len'", "'ord'", "'toInt'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", 
		"'<='", "'=='", "'!='", "'&&'", "'||'", "INTEGER_LIT", "'true'", "'false'", 
		"'''", "'\"'", "CHAR_LITERAL", "STRING_LITERAL", "'null'", "'#'", "IDENT", 
		"WS"
	};
	public static final int
		RULE_program = 0, RULE_func = 1, RULE_paramList = 2, RULE_param = 3, RULE_stat = 4, 
		RULE_unaryAssignOp = 5, RULE_assignOp = 6, RULE_assignLhs = 7, RULE_assignRhs = 8, 
		RULE_argList = 9, RULE_pairElem = 10, RULE_type = 11, RULE_baseType = 12, 
		RULE_pairType = 13, RULE_pairElemType = 14, RULE_expr = 15, RULE_unaryOper = 16, 
		RULE_ident = 17, RULE_intSign = 18, RULE_boolLiter = 19, RULE_charLiter = 20, 
		RULE_intLiter = 21, RULE_stringLiter = 22, RULE_pairLiter = 23, RULE_arrayLiter = 24, 
		RULE_t_if = 25, RULE_t_then = 26, RULE_t_else = 27, RULE_t_fi = 28, RULE_t_while = 29, 
		RULE_t_do = 30, RULE_t_done = 31, RULE_t_dwhile = 32, RULE_t_ddo = 33, 
		RULE_t_ddone = 34, RULE_t_begin = 35, RULE_t_end = 36, RULE_t_is = 37, 
		RULE_t_for = 38, RULE_t_fdo = 39, RULE_t_fdone = 40, RULE_t_semicolon = 41;
	public static final String[] ruleNames = {
		"program", "func", "paramList", "param", "stat", "unaryAssignOp", "assignOp", 
		"assignLhs", "assignRhs", "argList", "pairElem", "type", "baseType", "pairType", 
		"pairElemType", "expr", "unaryOper", "ident", "intSign", "boolLiter", 
		"charLiter", "intLiter", "stringLiter", "pairLiter", "arrayLiter", "t_if", 
		"t_then", "t_else", "t_fi", "t_while", "t_do", "t_done", "t_dwhile", "t_ddo", 
		"t_ddone", "t_begin", "t_end", "t_is", "t_for", "t_fdo", "t_fdone", "t_semicolon"
	};

	@Override
	public String getGrammarFileName() { return "Wacc.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public WaccParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public T_endContext t_end() {
			return getRuleContext(T_endContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(WaccParser.SEMICOLON, 0); }
		public List<StringLiterContext> stringLiter() {
			return getRuleContexts(StringLiterContext.class);
		}
		public TerminalNode IMPORT() { return getToken(WaccParser.IMPORT, 0); }
		public TerminalNode COMMENT(int i) {
			return getToken(WaccParser.COMMENT, i);
		}
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public T_beginContext t_begin() {
			return getRuleContext(T_beginContext.class,0);
		}
		public StringLiterContext stringLiter(int i) {
			return getRuleContext(StringLiterContext.class,i);
		}
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(WaccParser.COMMENT); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(84); match(COMMENT);
				}
				}
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(98);
			_la = _input.LA(1);
			if (_la==IMPORT) {
				{
				setState(90); match(IMPORT);
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==STRING_LITERAL) {
					{
					{
					setState(91); stringLiter();
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(97); match(SEMICOLON);
				}
			}

			setState(100); t_begin();
			setState(104);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(101); match(COMMENT);
					}
					} 
				}
				setState(106);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(110);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(107); func();
					}
					} 
				}
				setState(112);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(113); match(COMMENT);
				}
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(119); stat(0);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(120); match(COMMENT);
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126); t_end();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(127); match(COMMENT);
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public T_endContext t_end() {
			return getRuleContext(T_endContext.class,0);
		}
		public T_isContext t_is() {
			return getRuleContext(T_isContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133); type(0);
			setState(134); ident();
			setState(135); match(OPEN_PARENTHESES);
			setState(137);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << PAIR))) != 0)) {
				{
				setState(136); paramList();
				}
			}

			setState(139); match(CLOSE_PARENTHESES);
			setState(140); t_is();
			setState(141); stat(0);
			setState(142); t_end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); param();
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(145); match(COMMA);
				setState(146); param();
				}
				}
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152); type(0);
			setState(153); ident();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public int _p;
		public StatContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public StatContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class Stat_Return_exprContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(WaccParser.RETURN, 0); }
		public Stat_Return_exprContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Return_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_Exit_exprContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXIT() { return getToken(WaccParser.EXIT, 0); }
		public Stat_Exit_exprContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Exit_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_type_ident_equals_assignRHSContext extends StatContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public Stat_type_ident_equals_assignRHSContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_type_ident_equals_assignRHS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_Free_exprContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FREE() { return getToken(WaccParser.FREE, 0); }
		public Stat_Free_exprContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Free_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_semicolon_statContext extends StatContext {
		public TerminalNode SEMICOLON() { return getToken(WaccParser.SEMICOLON, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public Stat_semicolon_statContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_semicolon_stat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_Print_exprContext extends StatContext {
		public TerminalNode PRINT() { return getToken(WaccParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Stat_Print_exprContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Print_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_assignLHS_unaryAssignOpContext extends StatContext {
		public UnaryAssignOpContext unaryAssignOp() {
			return getRuleContext(UnaryAssignOpContext.class,0);
		}
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public Stat_assignLHS_unaryAssignOpContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_assignLHS_unaryAssignOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_assignLHS_assignOp_assignRHSContext extends StatContext {
		public UnaryAssignOpContext unaryAssignOp(int i) {
			return getRuleContext(UnaryAssignOpContext.class,i);
		}
		public AssignOpContext assignOp() {
			return getRuleContext(AssignOpContext.class,0);
		}
		public List<UnaryAssignOpContext> unaryAssignOp() {
			return getRuleContexts(UnaryAssignOpContext.class);
		}
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public Stat_assignLHS_assignOp_assignRHSContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_assignLHS_assignOp_assignRHS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_SKIPContext extends StatContext {
		public TerminalNode SKIP() { return getToken(WaccParser.SKIP, 0); }
		public Stat_SKIPContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_SKIP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_Read_assignLHSContext extends StatContext {
		public TerminalNode READ() { return getToken(WaccParser.READ, 0); }
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public Stat_Read_assignLHSContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Read_assignLHS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_Println_exprContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PRINTLN() { return getToken(WaccParser.PRINTLN, 0); }
		public Stat_Println_exprContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_Println_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_if_expr_then_stat_else_stat_fiContext extends StatContext {
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public T_thenContext t_then() {
			return getRuleContext(T_thenContext.class,0);
		}
		public T_ifContext t_if() {
			return getRuleContext(T_ifContext.class,0);
		}
		public T_fiContext t_fi() {
			return getRuleContext(T_fiContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public T_elseContext t_else() {
			return getRuleContext(T_elseContext.class,0);
		}
		public Stat_if_expr_then_stat_else_stat_fiContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_if_expr_then_stat_else_stat_fi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_ddo_stat_dwhile_expr_ddoneContext extends StatContext {
		public T_dwhileContext t_dwhile() {
			return getRuleContext(T_dwhileContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public T_ddoContext t_ddo() {
			return getRuleContext(T_ddoContext.class,0);
		}
		public T_ddoneContext t_ddone() {
			return getRuleContext(T_ddoneContext.class,0);
		}
		public Stat_ddo_stat_dwhile_expr_ddoneContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_ddo_stat_dwhile_expr_ddone(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_for_stat_expr_stat_fdo_stat_fdoneContext extends StatContext {
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public T_forContext t_for() {
			return getRuleContext(T_forContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public T_fdoneContext t_fdone() {
			return getRuleContext(T_fdoneContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public T_fdoContext t_fdo() {
			return getRuleContext(T_fdoContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public List<T_semicolonContext> t_semicolon() {
			return getRuleContexts(T_semicolonContext.class);
		}
		public T_semicolonContext t_semicolon(int i) {
			return getRuleContext(T_semicolonContext.class,i);
		}
		public Stat_for_stat_expr_stat_fdo_stat_fdoneContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_for_stat_expr_stat_fdo_stat_fdone(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_while_expr_do_stat_doneContext extends StatContext {
		public T_doneContext t_done() {
			return getRuleContext(T_doneContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public T_doContext t_do() {
			return getRuleContext(T_doContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public T_whileContext t_while() {
			return getRuleContext(T_whileContext.class,0);
		}
		public Stat_while_expr_do_stat_doneContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_while_expr_do_stat_done(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_begin_stat_endContext extends StatContext {
		public T_endContext t_end() {
			return getRuleContext(T_endContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public T_beginContext t_begin() {
			return getRuleContext(T_beginContext.class,0);
		}
		public Stat_begin_stat_endContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStat_begin_stat_end(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatContext _localctx = new StatContext(_ctx, _parentState, _p);
		StatContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, RULE_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				_localctx = new Stat_SKIPContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(156); match(SKIP);
				}
				break;

			case 2:
				{
				_localctx = new Stat_type_ident_equals_assignRHSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(157); type(0);
				setState(158); ident();
				setState(159); match(EQUALS);
				setState(160); assignRhs();
				}
				break;

			case 3:
				{
				_localctx = new Stat_assignLHS_unaryAssignOpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(162); assignLhs();
				setState(163); unaryAssignOp();
				}
				break;

			case 4:
				{
				_localctx = new Stat_assignLHS_assignOp_assignRHSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(165); assignLhs();
				setState(166); assignOp();
				setState(168);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(167); unaryAssignOp();
					}
					break;
				}
				setState(170); assignRhs();
				setState(172);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(171); unaryAssignOp();
					}
					break;
				}
				}
				break;

			case 5:
				{
				_localctx = new Stat_Read_assignLHSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(174); match(READ);
				setState(175); assignLhs();
				}
				break;

			case 6:
				{
				_localctx = new Stat_Free_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176); match(FREE);
				setState(177); expr(0);
				}
				break;

			case 7:
				{
				_localctx = new Stat_Exit_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(178); match(EXIT);
				setState(179); expr(0);
				}
				break;

			case 8:
				{
				_localctx = new Stat_Print_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(180); match(PRINT);
				setState(181); expr(0);
				}
				break;

			case 9:
				{
				_localctx = new Stat_Println_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(182); match(PRINTLN);
				setState(183); expr(0);
				}
				break;

			case 10:
				{
				_localctx = new Stat_Return_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(184); match(RETURN);
				setState(185); expr(0);
				}
				break;

			case 11:
				{
				_localctx = new Stat_if_expr_then_stat_else_stat_fiContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(186); t_if();
				setState(187); expr(0);
				setState(188); t_then();
				setState(189); stat(0);
				setState(190); t_else();
				setState(191); stat(0);
				setState(192); t_fi();
				}
				break;

			case 12:
				{
				_localctx = new Stat_while_expr_do_stat_doneContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(194); t_while();
				setState(195); expr(0);
				setState(196); t_do();
				setState(197); stat(0);
				setState(198); t_done();
				}
				break;

			case 13:
				{
				_localctx = new Stat_ddo_stat_dwhile_expr_ddoneContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(200); t_ddo();
				setState(201); stat(0);
				setState(202); t_dwhile();
				setState(203); expr(0);
				setState(204); t_ddone();
				}
				break;

			case 14:
				{
				_localctx = new Stat_for_stat_expr_stat_fdo_stat_fdoneContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(206); t_for();
				setState(207); match(OPEN_PARENTHESES);
				setState(208); stat(0);
				setState(209); t_semicolon();
				setState(210); expr(0);
				setState(211); t_semicolon();
				setState(212); stat(0);
				setState(213); match(CLOSE_PARENTHESES);
				setState(214); t_fdo();
				setState(215); stat(0);
				setState(216); t_fdone();
				}
				break;

			case 15:
				{
				_localctx = new Stat_begin_stat_endContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(218); t_begin();
				setState(219); stat(0);
				setState(220); t_end();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(229);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Stat_semicolon_statContext(new StatContext(_parentctx, _parentState, _p));
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(224);
					if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
					setState(225); match(SEMICOLON);
					setState(226); stat(2);
					}
					} 
				}
				setState(231);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryAssignOpContext extends ParserRuleContext {
		public UnaryAssignOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryAssignOp; }
	 
		public UnaryAssignOpContext() { }
		public void copyFrom(UnaryAssignOpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UnaryAssignOp_minusContext extends UnaryAssignOpContext {
		public TerminalNode MINUS(int i) {
			return getToken(WaccParser.MINUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(WaccParser.MINUS); }
		public UnaryAssignOp_minusContext(UnaryAssignOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnaryAssignOp_minus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryAssignOp_plusContext extends UnaryAssignOpContext {
		public List<TerminalNode> PLUS() { return getTokens(WaccParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(WaccParser.PLUS, i);
		}
		public UnaryAssignOp_plusContext(UnaryAssignOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnaryAssignOp_plus(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryAssignOpContext unaryAssignOp() throws RecognitionException {
		UnaryAssignOpContext _localctx = new UnaryAssignOpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_unaryAssignOp);
		try {
			setState(236);
			switch (_input.LA(1)) {
			case PLUS:
				_localctx = new UnaryAssignOp_plusContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(232); match(PLUS);
				setState(233); match(PLUS);
				}
				break;
			case MINUS:
				_localctx = new UnaryAssignOp_minusContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(234); match(MINUS);
				setState(235); match(MINUS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignOpContext extends ParserRuleContext {
		public AssignOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignOp; }
	 
		public AssignOpContext() { }
		public void copyFrom(AssignOpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignOp_plus_equalsContext extends AssignOpContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public AssignOp_plus_equalsContext(AssignOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignOp_plus_equals(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignOp_minus_equalsContext extends AssignOpContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public AssignOp_minus_equalsContext(AssignOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignOp_minus_equals(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignOp_equalsContext extends AssignOpContext {
		public TerminalNode EQUALS() { return getToken(WaccParser.EQUALS, 0); }
		public AssignOp_equalsContext(AssignOpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignOp_equals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignOpContext assignOp() throws RecognitionException {
		AssignOpContext _localctx = new AssignOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignOp);
		try {
			setState(243);
			switch (_input.LA(1)) {
			case EQUALS:
				_localctx = new AssignOp_equalsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(238); match(EQUALS);
				}
				break;
			case PLUS:
				_localctx = new AssignOp_plus_equalsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(239); match(PLUS);
				setState(240); match(EQUALS);
				}
				break;
			case MINUS:
				_localctx = new AssignOp_minus_equalsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(241); match(MINUS);
				setState(242); match(EQUALS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignLhsContext extends ParserRuleContext {
		public AssignLhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignLhs; }
	 
		public AssignLhsContext() { }
		public void copyFrom(AssignLhsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignLHS_identContext extends AssignLhsContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public AssignLHS_identContext(AssignLhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignLHS_ident(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignLHS_exprContext extends AssignLhsContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignLHS_exprContext(AssignLhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignLHS_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignLHS_pairElemContext extends AssignLhsContext {
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public AssignLHS_pairElemContext(AssignLhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignLHS_pairElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignLhsContext assignLhs() throws RecognitionException {
		AssignLhsContext _localctx = new AssignLhsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assignLhs);
		try {
			setState(248);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new AssignLHS_identContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(245); ident();
				}
				break;

			case 2:
				_localctx = new AssignLHS_exprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(246); expr(0);
				}
				break;

			case 3:
				_localctx = new AssignLHS_pairElemContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(247); pairElem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignRhsContext extends ParserRuleContext {
		public AssignRhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignRhs; }
	 
		public AssignRhsContext() { }
		public void copyFrom(AssignRhsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignRHS_exprContext extends AssignRhsContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignRHS_exprContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignRHS_newPairContext extends AssignRhsContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public TerminalNode NEWPAIR() { return getToken(WaccParser.NEWPAIR, 0); }
		public AssignRHS_newPairContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_newPair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignRHS_pairElemContext extends AssignRhsContext {
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public AssignRHS_pairElemContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_pairElem(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignRHS_callContext extends AssignRhsContext {
		public TerminalNode CALL() { return getToken(WaccParser.CALL, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public AssignRHS_callContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_call(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignRHS_arrayLitContext extends AssignRhsContext {
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public AssignRHS_arrayLitContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_arrayLit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignRHS_if_then_elseContext extends AssignRhsContext {
		public AssignRhsContext assignRhs(int i) {
			return getRuleContext(AssignRhsContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode QUEST() { return getToken(WaccParser.QUEST, 0); }
		public TerminalNode COLON() { return getToken(WaccParser.COLON, 0); }
		public List<AssignRhsContext> assignRhs() {
			return getRuleContexts(AssignRhsContext.class);
		}
		public AssignRHS_if_then_elseContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitAssignRHS_if_then_else(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignRhsContext assignRhs() throws RecognitionException {
		AssignRhsContext _localctx = new AssignRhsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assignRhs);
		int _la;
		try {
			setState(274);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new AssignRHS_exprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(250); expr(0);
				}
				break;

			case 2:
				_localctx = new AssignRHS_arrayLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(251); arrayLiter();
				}
				break;

			case 3:
				_localctx = new AssignRHS_newPairContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(252); match(NEWPAIR);
				setState(253); match(OPEN_PARENTHESES);
				setState(254); expr(0);
				setState(255); match(COMMA);
				setState(256); expr(0);
				setState(257); match(CLOSE_PARENTHESES);
				}
				break;

			case 4:
				_localctx = new AssignRHS_pairElemContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(259); pairElem();
				}
				break;

			case 5:
				_localctx = new AssignRHS_callContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(260); match(CALL);
				setState(261); ident();
				setState(262); match(OPEN_PARENTHESES);
				setState(264);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << OPEN_PARENTHESES) | (1L << EXCLAMATION) | (1L << LEN) | (1L << ORD) | (1L << TOINT) | (1L << INTEGER_LIT) | (1L << TRUE) | (1L << FALSE))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (CHAR_LITERAL - 66)) | (1L << (STRING_LITERAL - 66)) | (1L << (NULL - 66)) | (1L << (IDENT - 66)))) != 0)) {
					{
					setState(263); argList();
					}
				}

				setState(266); match(CLOSE_PARENTHESES);
				}
				break;

			case 6:
				_localctx = new AssignRHS_if_then_elseContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(268); expr(0);
				setState(269); match(QUEST);
				setState(270); assignRhs();
				setState(271); match(COLON);
				setState(272); assignRhs();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276); expr(0);
			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(277); match(COMMA);
				setState(278); expr(0);
				}
				}
				setState(283);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemContext extends ParserRuleContext {
		public PairElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElem; }
	 
		public PairElemContext() { }
		public void copyFrom(PairElemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PairElem_SND_exprContext extends PairElemContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SND() { return getToken(WaccParser.SND, 0); }
		public PairElem_SND_exprContext(PairElemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPairElem_SND_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PairElem_FST_exprContext extends PairElemContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FST() { return getToken(WaccParser.FST, 0); }
		public PairElem_FST_exprContext(PairElemContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPairElem_FST_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemContext pairElem() throws RecognitionException {
		PairElemContext _localctx = new PairElemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pairElem);
		try {
			setState(288);
			switch (_input.LA(1)) {
			case FST:
				_localctx = new PairElem_FST_exprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(284); match(FST);
				setState(285); expr(0);
				}
				break;
			case SND:
				_localctx = new PairElem_SND_exprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(286); match(SND);
				setState(287); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public int _p;
		public TypeContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TypeContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class Type_arrContext extends TypeContext {
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public Type_arrContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitType_arr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Type_pairContext extends TypeContext {
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public Type_pairContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitType_pair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Type_baseContext extends TypeContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public Type_baseContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitType_base(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState, _p);
		TypeContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, RULE_type);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
				{
				_localctx = new Type_baseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(291); baseType();
				}
				break;
			case PAIR:
				{
				_localctx = new Type_pairContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(292); pairType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(300);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Type_arrContext(new TypeContext(_parentctx, _parentState, _p));
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(295);
					if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
					setState(296); match(OPEN_BRACKETS);
					setState(297); match(CLOSE_BRACKETS);
					}
					} 
				}
				setState(302);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
	 
		public BaseTypeContext() { }
		public void copyFrom(BaseTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Base_BOOLContext extends BaseTypeContext {
		public TerminalNode BOOL() { return getToken(WaccParser.BOOL, 0); }
		public Base_BOOLContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitBase_BOOL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Base_CHARContext extends BaseTypeContext {
		public TerminalNode CHAR() { return getToken(WaccParser.CHAR, 0); }
		public Base_CHARContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitBase_CHAR(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Base_INTContext extends BaseTypeContext {
		public TerminalNode INT() { return getToken(WaccParser.INT, 0); }
		public Base_INTContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitBase_INT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Base_STRContext extends BaseTypeContext {
		public TerminalNode STRING() { return getToken(WaccParser.STRING, 0); }
		public Base_STRContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitBase_STR(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_baseType);
		try {
			setState(307);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new Base_INTContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(303); match(INT);
				}
				break;
			case BOOL:
				_localctx = new Base_BOOLContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(304); match(BOOL);
				}
				break;
			case CHAR:
				_localctx = new Base_CHARContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(305); match(CHAR);
				}
				break;
			case STRING:
				_localctx = new Base_STRContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(306); match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairTypeContext extends ParserRuleContext {
		public List<PairElemTypeContext> pairElemType() {
			return getRuleContexts(PairElemTypeContext.class);
		}
		public PairElemTypeContext pairElemType(int i) {
			return getRuleContext(PairElemTypeContext.class,i);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WaccParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public PairTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPairType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairTypeContext pairType() throws RecognitionException {
		PairTypeContext _localctx = new PairTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309); match(PAIR);
			setState(310); match(OPEN_PARENTHESES);
			setState(311); pairElemType();
			setState(312); match(COMMA);
			setState(313); pairElemType();
			setState(314); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemTypeContext extends ParserRuleContext {
		public PairElemTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElemType; }
	 
		public PairElemTypeContext() { }
		public void copyFrom(PairElemTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Pair_base_tContext extends PairElemTypeContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public Pair_base_tContext(PairElemTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPair_base_t(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Pair_PAIRContext extends PairElemTypeContext {
		public TerminalNode PAIR() { return getToken(WaccParser.PAIR, 0); }
		public Pair_PAIRContext(PairElemTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPair_PAIR(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Pair_typeContext extends PairElemTypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Pair_typeContext(PairElemTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPair_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemTypeContext pairElemType() throws RecognitionException {
		PairElemTypeContext _localctx = new PairElemTypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_pairElemType);
		try {
			setState(319);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new Pair_base_tContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(316); baseType();
				}
				break;

			case 2:
				_localctx = new Pair_typeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(317); type(0);
				}
				break;

			case 3:
				_localctx = new Pair_PAIRContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(318); match(PAIR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public int _p;
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class Expr_LTContext extends ExprContext {
		public TerminalNode LESS_THAN() { return getToken(WaccParser.LESS_THAN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_LTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_LT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_intContext extends ExprContext {
		public IntLiterContext intLiter() {
			return getRuleContext(IntLiterContext.class,0);
		}
		public Expr_intContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_int(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_GTEQContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GREATER_THAN_EQUAL() { return getToken(WaccParser.GREATER_THAN_EQUAL, 0); }
		public Expr_GTEQContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_GTEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_PLUSContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public Expr_PLUSContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_PLUS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_LTEQContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LESS_THAN_EQUAL() { return getToken(WaccParser.LESS_THAN_EQUAL, 0); }
		public Expr_LTEQContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_LTEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_DIVContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode DIVIDE() { return getToken(WaccParser.DIVIDE, 0); }
		public Expr_DIVContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_DIV(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_unaryContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryOperContext unaryOper() {
			return getRuleContext(UnaryOperContext.class,0);
		}
		public Expr_unaryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_unary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_charContext extends ExprContext {
		public CharLiterContext charLiter() {
			return getRuleContext(CharLiterContext.class,0);
		}
		public Expr_charContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_char(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_ANDContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode AND() { return getToken(WaccParser.AND, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_ANDContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_AND(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_NEQContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode NOT_EQUAL() { return getToken(WaccParser.NOT_EQUAL, 0); }
		public Expr_NEQContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_NEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_identContext extends ExprContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public Expr_identContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_ident(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_MINUSContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_MINUSContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_MINUS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_MODContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MOD() { return getToken(WaccParser.MOD, 0); }
		public Expr_MODContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_MOD(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_arrContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(WaccParser.OPEN_BRACKETS, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CLOSE_BRACKETS() { return getToken(WaccParser.CLOSE_BRACKETS, 0); }
		public Expr_arrContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_arr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_pairlitContext extends ExprContext {
		public PairLiterContext pairLiter() {
			return getRuleContext(PairLiterContext.class,0);
		}
		public Expr_pairlitContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_pairlit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_EQContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(WaccParser.EQUAL, 0); }
		public Expr_EQContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_EQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_ORContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OR() { return getToken(WaccParser.OR, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_ORContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_OR(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_exprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WaccParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WaccParser.OPEN_PARENTHESES, 0); }
		public Expr_exprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_GTContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GREATER_THAN() { return getToken(WaccParser.GREATER_THAN, 0); }
		public Expr_GTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_GT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_strContext extends ExprContext {
		public StringLiterContext stringLiter() {
			return getRuleContext(StringLiterContext.class,0);
		}
		public Expr_strContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_str(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_boolContext extends ExprContext {
		public BoolLiterContext boolLiter() {
			return getRuleContext(BoolLiterContext.class,0);
		}
		public Expr_boolContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_bool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_MULTContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode STAR() { return getToken(WaccParser.STAR, 0); }
		public Expr_MULTContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitExpr_MULT(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState, _p);
		ExprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				_localctx = new Expr_unaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(322); unaryOper();
				setState(323); expr(15);
				}
				break;

			case 2:
				{
				_localctx = new Expr_intContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(325); intLiter();
				}
				break;

			case 3:
				{
				_localctx = new Expr_boolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(326); boolLiter();
				}
				break;

			case 4:
				{
				_localctx = new Expr_charContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(327); charLiter();
				}
				break;

			case 5:
				{
				_localctx = new Expr_strContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(328); stringLiter();
				}
				break;

			case 6:
				{
				_localctx = new Expr_pairlitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(329); pairLiter();
				}
				break;

			case 7:
				{
				_localctx = new Expr_identContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(330); ident();
				}
				break;

			case 8:
				{
				_localctx = new Expr_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(331); match(OPEN_PARENTHESES);
				setState(332); expr(0);
				setState(333); match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(383);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(381);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_MINUSContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(337);
						if (!(14 >= _localctx._p)) throw new FailedPredicateException(this, "14 >= $_p");
						setState(338); match(MINUS);
						setState(339); expr(15);
						}
						break;

					case 2:
						{
						_localctx = new Expr_PLUSContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(340);
						if (!(13 >= _localctx._p)) throw new FailedPredicateException(this, "13 >= $_p");
						setState(341); match(PLUS);
						setState(342); expr(14);
						}
						break;

					case 3:
						{
						_localctx = new Expr_MULTContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(343);
						if (!(12 >= _localctx._p)) throw new FailedPredicateException(this, "12 >= $_p");
						setState(344); match(STAR);
						setState(345); expr(13);
						}
						break;

					case 4:
						{
						_localctx = new Expr_DIVContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(346);
						if (!(11 >= _localctx._p)) throw new FailedPredicateException(this, "11 >= $_p");
						setState(347); match(DIVIDE);
						setState(348); expr(12);
						}
						break;

					case 5:
						{
						_localctx = new Expr_MODContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(349);
						if (!(10 >= _localctx._p)) throw new FailedPredicateException(this, "10 >= $_p");
						setState(350); match(MOD);
						setState(351); expr(11);
						}
						break;

					case 6:
						{
						_localctx = new Expr_GTContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(352);
						if (!(9 >= _localctx._p)) throw new FailedPredicateException(this, "9 >= $_p");
						setState(353); match(GREATER_THAN);
						setState(354); expr(10);
						}
						break;

					case 7:
						{
						_localctx = new Expr_GTEQContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(355);
						if (!(8 >= _localctx._p)) throw new FailedPredicateException(this, "8 >= $_p");
						setState(356); match(GREATER_THAN_EQUAL);
						setState(357); expr(9);
						}
						break;

					case 8:
						{
						_localctx = new Expr_LTContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(358);
						if (!(7 >= _localctx._p)) throw new FailedPredicateException(this, "7 >= $_p");
						setState(359); match(LESS_THAN);
						setState(360); expr(8);
						}
						break;

					case 9:
						{
						_localctx = new Expr_LTEQContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(361);
						if (!(6 >= _localctx._p)) throw new FailedPredicateException(this, "6 >= $_p");
						setState(362); match(LESS_THAN_EQUAL);
						setState(363); expr(7);
						}
						break;

					case 10:
						{
						_localctx = new Expr_EQContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(364);
						if (!(5 >= _localctx._p)) throw new FailedPredicateException(this, "5 >= $_p");
						setState(365); match(EQUAL);
						setState(366); expr(6);
						}
						break;

					case 11:
						{
						_localctx = new Expr_NEQContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(367);
						if (!(4 >= _localctx._p)) throw new FailedPredicateException(this, "4 >= $_p");
						setState(368); match(NOT_EQUAL);
						setState(369); expr(5);
						}
						break;

					case 12:
						{
						_localctx = new Expr_ORContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(370);
						if (!(3 >= _localctx._p)) throw new FailedPredicateException(this, "3 >= $_p");
						setState(371); match(OR);
						setState(372); expr(4);
						}
						break;

					case 13:
						{
						_localctx = new Expr_ANDContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(373);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(374); match(AND);
						setState(375); expr(3);
						}
						break;

					case 14:
						{
						_localctx = new Expr_arrContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(376);
						if (!(16 >= _localctx._p)) throw new FailedPredicateException(this, "16 >= $_p");
						setState(377); match(OPEN_BRACKETS);
						setState(378); expr(0);
						setState(379); match(CLOSE_BRACKETS);
						}
						break;
					}
					} 
				}
				setState(385);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnaryOperContext extends ParserRuleContext {
		public UnaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOper; }
	 
		public UnaryOperContext() { }
		public void copyFrom(UnaryOperContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Unary_minusContext extends UnaryOperContext {
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public Unary_minusContext(UnaryOperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnary_minus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Unary_excContext extends UnaryOperContext {
		public TerminalNode EXCLAMATION() { return getToken(WaccParser.EXCLAMATION, 0); }
		public Unary_excContext(UnaryOperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnary_exc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Unary_ordContext extends UnaryOperContext {
		public TerminalNode ORD() { return getToken(WaccParser.ORD, 0); }
		public Unary_ordContext(UnaryOperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnary_ord(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Unary_lenContext extends UnaryOperContext {
		public TerminalNode LEN() { return getToken(WaccParser.LEN, 0); }
		public Unary_lenContext(UnaryOperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnary_len(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Unary_intContext extends UnaryOperContext {
		public TerminalNode TOINT() { return getToken(WaccParser.TOINT, 0); }
		public Unary_intContext(UnaryOperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitUnary_int(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperContext unaryOper() throws RecognitionException {
		UnaryOperContext _localctx = new UnaryOperContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_unaryOper);
		try {
			setState(391);
			switch (_input.LA(1)) {
			case EXCLAMATION:
				_localctx = new Unary_excContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(386); match(EXCLAMATION);
				}
				break;
			case MINUS:
				_localctx = new Unary_minusContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(387); match(MINUS);
				}
				break;
			case LEN:
				_localctx = new Unary_lenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(388); match(LEN);
				}
				break;
			case ORD:
				_localctx = new Unary_ordContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(389); match(ORD);
				}
				break;
			case TOINT:
				_localctx = new Unary_intContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(390); match(TOINT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(WaccParser.IDENT, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393); match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntSignContext extends ParserRuleContext {
		public TerminalNode MINUS() { return getToken(WaccParser.MINUS, 0); }
		public TerminalNode PLUS() { return getToken(WaccParser.PLUS, 0); }
		public IntSignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intSign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitIntSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntSignContext intSign() throws RecognitionException {
		IntSignContext _localctx = new IntSignContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_intSign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(395);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolLiterContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(WaccParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(WaccParser.FALSE, 0); }
		public BoolLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitBoolLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolLiterContext boolLiter() throws RecognitionException {
		BoolLiterContext _localctx = new BoolLiterContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_boolLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CharLiterContext extends ParserRuleContext {
		public TerminalNode CHAR_LITERAL() { return getToken(WaccParser.CHAR_LITERAL, 0); }
		public CharLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_charLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitCharLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CharLiterContext charLiter() throws RecognitionException {
		CharLiterContext _localctx = new CharLiterContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_charLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399); match(CHAR_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntLiterContext extends ParserRuleContext {
		public TerminalNode INTEGER_LIT() { return getToken(WaccParser.INTEGER_LIT, 0); }
		public IntSignContext intSign() {
			return getRuleContext(IntSignContext.class,0);
		}
		public IntLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitIntLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntLiterContext intLiter() throws RecognitionException {
		IntLiterContext _localctx = new IntLiterContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_intLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(401); intSign();
				}
			}

			setState(404); match(INTEGER_LIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiterContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(WaccParser.STRING_LITERAL, 0); }
		public StringLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitStringLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLiterContext stringLiter() throws RecognitionException {
		StringLiterContext _localctx = new StringLiterContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_stringLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406); match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairLiterContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(WaccParser.NULL, 0); }
		public PairLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitPairLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairLiterContext pairLiter() throws RecognitionException {
		PairLiterContext _localctx = new PairLiterContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_pairLiter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408); match(NULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiterContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKETS(int i) {
			return getToken(WaccParser.OPEN_BRACKETS, i);
		}
		public TerminalNode CLOSE_BRACKETS(int i) {
			return getToken(WaccParser.CLOSE_BRACKETS, i);
		}
		public List<ArrayLiterContext> arrayLiter() {
			return getRuleContexts(ArrayLiterContext.class);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> OPEN_BRACKETS() { return getTokens(WaccParser.OPEN_BRACKETS); }
		public ArrayLiterContext arrayLiter(int i) {
			return getRuleContext(ArrayLiterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(WaccParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> CLOSE_BRACKETS() { return getTokens(WaccParser.CLOSE_BRACKETS); }
		public TerminalNode COMMA(int i) {
			return getToken(WaccParser.COMMA, i);
		}
		public ArrayLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitArrayLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiterContext arrayLiter() throws RecognitionException {
		ArrayLiterContext _localctx = new ArrayLiterContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_arrayLiter);
		int _la;
		try {
			int _alt;
			setState(442);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(410); match(OPEN_BRACKETS);
				setState(419);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << OPEN_PARENTHESES) | (1L << EXCLAMATION) | (1L << LEN) | (1L << ORD) | (1L << TOINT) | (1L << INTEGER_LIT) | (1L << TRUE) | (1L << FALSE))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (CHAR_LITERAL - 66)) | (1L << (STRING_LITERAL - 66)) | (1L << (NULL - 66)) | (1L << (IDENT - 66)))) != 0)) {
					{
					setState(411); expr(0);
					setState(416);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(412); match(COMMA);
						setState(413); expr(0);
						}
						}
						setState(418);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(421); match(CLOSE_BRACKETS);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(423); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(422); match(OPEN_BRACKETS);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(425); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				} while ( _alt!=2 && _alt!=-1 );
				setState(435);
				_la = _input.LA(1);
				if (_la==OPEN_BRACKETS) {
					{
					setState(427); arrayLiter();
					setState(432);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(428); match(COMMA);
						setState(429); arrayLiter();
						}
						}
						setState(434);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(438); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(437); match(CLOSE_BRACKETS);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(440); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
				} while ( _alt!=2 && _alt!=-1 );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_ifContext extends ParserRuleContext {
		public T_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_if; }
	 
		public T_ifContext() { }
		public void copyFrom(T_ifContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_ifContext extends T_ifContext {
		public TerminalNode IF() { return getToken(WaccParser.IF, 0); }
		public Terminal_ifContext(T_ifContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_if(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_ifContext t_if() throws RecognitionException {
		T_ifContext _localctx = new T_ifContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_t_if);
		try {
			_localctx = new Terminal_ifContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(444); match(IF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_thenContext extends ParserRuleContext {
		public T_thenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_then; }
	 
		public T_thenContext() { }
		public void copyFrom(T_thenContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_thenContext extends T_thenContext {
		public TerminalNode THEN() { return getToken(WaccParser.THEN, 0); }
		public Terminal_thenContext(T_thenContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_then(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_thenContext t_then() throws RecognitionException {
		T_thenContext _localctx = new T_thenContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_t_then);
		try {
			_localctx = new Terminal_thenContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(446); match(THEN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_elseContext extends ParserRuleContext {
		public T_elseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_else; }
	 
		public T_elseContext() { }
		public void copyFrom(T_elseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_elseContext extends T_elseContext {
		public TerminalNode ELSE() { return getToken(WaccParser.ELSE, 0); }
		public Terminal_elseContext(T_elseContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_else(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_elseContext t_else() throws RecognitionException {
		T_elseContext _localctx = new T_elseContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_t_else);
		try {
			_localctx = new Terminal_elseContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(448); match(ELSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_fiContext extends ParserRuleContext {
		public T_fiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_fi; }
	 
		public T_fiContext() { }
		public void copyFrom(T_fiContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_fiContext extends T_fiContext {
		public TerminalNode FI() { return getToken(WaccParser.FI, 0); }
		public Terminal_fiContext(T_fiContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_fi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_fiContext t_fi() throws RecognitionException {
		T_fiContext _localctx = new T_fiContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_t_fi);
		try {
			_localctx = new Terminal_fiContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(450); match(FI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_whileContext extends ParserRuleContext {
		public T_whileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_while; }
	 
		public T_whileContext() { }
		public void copyFrom(T_whileContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_whileContext extends T_whileContext {
		public TerminalNode WHILE() { return getToken(WaccParser.WHILE, 0); }
		public Terminal_whileContext(T_whileContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_while(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_whileContext t_while() throws RecognitionException {
		T_whileContext _localctx = new T_whileContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_t_while);
		try {
			_localctx = new Terminal_whileContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(452); match(WHILE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_doContext extends ParserRuleContext {
		public T_doContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_do; }
	 
		public T_doContext() { }
		public void copyFrom(T_doContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_doContext extends T_doContext {
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public Terminal_doContext(T_doContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_do(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_doContext t_do() throws RecognitionException {
		T_doContext _localctx = new T_doContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_t_do);
		try {
			_localctx = new Terminal_doContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(454); match(DO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_doneContext extends ParserRuleContext {
		public T_doneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_done; }
	 
		public T_doneContext() { }
		public void copyFrom(T_doneContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_doneContext extends T_doneContext {
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public Terminal_doneContext(T_doneContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_done(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_doneContext t_done() throws RecognitionException {
		T_doneContext _localctx = new T_doneContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_t_done);
		try {
			_localctx = new Terminal_doneContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(456); match(DONE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_dwhileContext extends ParserRuleContext {
		public T_dwhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_dwhile; }
	 
		public T_dwhileContext() { }
		public void copyFrom(T_dwhileContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_dwhileContext extends T_dwhileContext {
		public TerminalNode WHILE() { return getToken(WaccParser.WHILE, 0); }
		public Terminal_dwhileContext(T_dwhileContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_dwhile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_dwhileContext t_dwhile() throws RecognitionException {
		T_dwhileContext _localctx = new T_dwhileContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_t_dwhile);
		try {
			_localctx = new Terminal_dwhileContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(458); match(WHILE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_ddoContext extends ParserRuleContext {
		public T_ddoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_ddo; }
	 
		public T_ddoContext() { }
		public void copyFrom(T_ddoContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_ddoContext extends T_ddoContext {
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public Terminal_ddoContext(T_ddoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_ddo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_ddoContext t_ddo() throws RecognitionException {
		T_ddoContext _localctx = new T_ddoContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_t_ddo);
		try {
			_localctx = new Terminal_ddoContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(460); match(DO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_ddoneContext extends ParserRuleContext {
		public T_ddoneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_ddone; }
	 
		public T_ddoneContext() { }
		public void copyFrom(T_ddoneContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_ddoneContext extends T_ddoneContext {
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public Terminal_ddoneContext(T_ddoneContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_ddone(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_ddoneContext t_ddone() throws RecognitionException {
		T_ddoneContext _localctx = new T_ddoneContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_t_ddone);
		try {
			_localctx = new Terminal_ddoneContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(462); match(DONE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_beginContext extends ParserRuleContext {
		public T_beginContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_begin; }
	 
		public T_beginContext() { }
		public void copyFrom(T_beginContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_beginContext extends T_beginContext {
		public TerminalNode BEGIN() { return getToken(WaccParser.BEGIN, 0); }
		public Terminal_beginContext(T_beginContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_begin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_beginContext t_begin() throws RecognitionException {
		T_beginContext _localctx = new T_beginContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_t_begin);
		try {
			_localctx = new Terminal_beginContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(464); match(BEGIN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_endContext extends ParserRuleContext {
		public T_endContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_end; }
	 
		public T_endContext() { }
		public void copyFrom(T_endContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_endContext extends T_endContext {
		public TerminalNode END() { return getToken(WaccParser.END, 0); }
		public Terminal_endContext(T_endContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_end(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_endContext t_end() throws RecognitionException {
		T_endContext _localctx = new T_endContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_t_end);
		try {
			_localctx = new Terminal_endContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(466); match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_isContext extends ParserRuleContext {
		public T_isContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_is; }
	 
		public T_isContext() { }
		public void copyFrom(T_isContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_isContext extends T_isContext {
		public TerminalNode IS() { return getToken(WaccParser.IS, 0); }
		public Terminal_isContext(T_isContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_is(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_isContext t_is() throws RecognitionException {
		T_isContext _localctx = new T_isContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_t_is);
		try {
			_localctx = new Terminal_isContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(468); match(IS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_forContext extends ParserRuleContext {
		public T_forContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_for; }
	 
		public T_forContext() { }
		public void copyFrom(T_forContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_forContext extends T_forContext {
		public TerminalNode FOR() { return getToken(WaccParser.FOR, 0); }
		public Terminal_forContext(T_forContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_for(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_forContext t_for() throws RecognitionException {
		T_forContext _localctx = new T_forContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_t_for);
		try {
			_localctx = new Terminal_forContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(470); match(FOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_fdoContext extends ParserRuleContext {
		public T_fdoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_fdo; }
	 
		public T_fdoContext() { }
		public void copyFrom(T_fdoContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_fdoContext extends T_fdoContext {
		public TerminalNode DO() { return getToken(WaccParser.DO, 0); }
		public Terminal_fdoContext(T_fdoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_fdo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_fdoContext t_fdo() throws RecognitionException {
		T_fdoContext _localctx = new T_fdoContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_t_fdo);
		try {
			_localctx = new Terminal_fdoContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(472); match(DO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_fdoneContext extends ParserRuleContext {
		public T_fdoneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_fdone; }
	 
		public T_fdoneContext() { }
		public void copyFrom(T_fdoneContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_fdoneContext extends T_fdoneContext {
		public TerminalNode DONE() { return getToken(WaccParser.DONE, 0); }
		public Terminal_fdoneContext(T_fdoneContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_fdone(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_fdoneContext t_fdone() throws RecognitionException {
		T_fdoneContext _localctx = new T_fdoneContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_t_fdone);
		try {
			_localctx = new Terminal_fdoneContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(474); match(DONE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class T_semicolonContext extends ParserRuleContext {
		public T_semicolonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_semicolon; }
	 
		public T_semicolonContext() { }
		public void copyFrom(T_semicolonContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Terminal_semicolonContext extends T_semicolonContext {
		public TerminalNode SEMICOLON() { return getToken(WaccParser.SEMICOLON, 0); }
		public Terminal_semicolonContext(T_semicolonContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WaccVisitor ) return ((WaccVisitor<? extends T>)visitor).visitTerminal_semicolon(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_semicolonContext t_semicolon() throws RecognitionException {
		T_semicolonContext _localctx = new T_semicolonContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_t_semicolon);
		try {
			_localctx = new Terminal_semicolonContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(476); match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return stat_sempred((StatContext)_localctx, predIndex);

		case 11: return type_sempred((TypeContext)_localctx, predIndex);

		case 15: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return 14 >= _localctx._p;

		case 3: return 13 >= _localctx._p;

		case 4: return 12 >= _localctx._p;

		case 5: return 11 >= _localctx._p;

		case 6: return 10 >= _localctx._p;

		case 7: return 9 >= _localctx._p;

		case 8: return 8 >= _localctx._p;

		case 9: return 7 >= _localctx._p;

		case 10: return 6 >= _localctx._p;

		case 11: return 5 >= _localctx._p;

		case 12: return 4 >= _localctx._p;

		case 13: return 3 >= _localctx._p;

		case 14: return 2 >= _localctx._p;

		case 15: return 16 >= _localctx._p;
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return 2 >= _localctx._p;
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 1 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3I\u01e1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\7\2X\n\2\f\2\16\2[\13\2\3\2\3\2\7\2_\n\2\f\2\16\2b\13\2\3\2\5\2e\n"+
		"\2\3\2\3\2\7\2i\n\2\f\2\16\2l\13\2\3\2\7\2o\n\2\f\2\16\2r\13\2\3\2\7\2"+
		"u\n\2\f\2\16\2x\13\2\3\2\3\2\7\2|\n\2\f\2\16\2\177\13\2\3\2\3\2\7\2\u0083"+
		"\n\2\f\2\16\2\u0086\13\2\3\3\3\3\3\3\3\3\5\3\u008c\n\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\7\4\u0096\n\4\f\4\16\4\u0099\13\4\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00ab\n\6\3\6\3\6\5\6"+
		"\u00af\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00e1"+
		"\n\6\3\6\3\6\3\6\7\6\u00e6\n\6\f\6\16\6\u00e9\13\6\3\7\3\7\3\7\3\7\5\7"+
		"\u00ef\n\7\3\b\3\b\3\b\3\b\3\b\5\b\u00f6\n\b\3\t\3\t\3\t\5\t\u00fb\n\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u010b\n\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0115\n\n\3\13\3\13\3\13\7\13\u011a"+
		"\n\13\f\13\16\13\u011d\13\13\3\f\3\f\3\f\3\f\5\f\u0123\n\f\3\r\3\r\3\r"+
		"\5\r\u0128\n\r\3\r\3\r\3\r\7\r\u012d\n\r\f\r\16\r\u0130\13\r\3\16\3\16"+
		"\3\16\3\16\5\16\u0136\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\5\20\u0142\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\5\21\u0152\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u0180\n\21\f\21\16"+
		"\21\u0183\13\21\3\22\3\22\3\22\3\22\3\22\5\22\u018a\n\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\5\27\u0195\n\27\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\32\3\32\7\32\u01a1\n\32\f\32\16\32\u01a4\13\32\5\32"+
		"\u01a6\n\32\3\32\3\32\6\32\u01aa\n\32\r\32\16\32\u01ab\3\32\3\32\3\32"+
		"\7\32\u01b1\n\32\f\32\16\32\u01b4\13\32\5\32\u01b6\n\32\3\32\6\32\u01b9"+
		"\n\32\r\32\16\32\u01ba\5\32\u01bd\n\32\3\33\3\33\3\34\3\34\3\35\3\35\3"+
		"\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'"+
		"\3(\3(\3)\3)\3*\3*\3+\3+\3+\2,\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>@BDFHJLNPRT\2\4\3\2\5\6\3\2@A\u0206\2Y\3\2\2"+
		"\2\4\u0087\3\2\2\2\6\u0092\3\2\2\2\b\u009a\3\2\2\2\n\u00e0\3\2\2\2\f\u00ee"+
		"\3\2\2\2\16\u00f5\3\2\2\2\20\u00fa\3\2\2\2\22\u0114\3\2\2\2\24\u0116\3"+
		"\2\2\2\26\u0122\3\2\2\2\30\u0127\3\2\2\2\32\u0135\3\2\2\2\34\u0137\3\2"+
		"\2\2\36\u0141\3\2\2\2 \u0151\3\2\2\2\"\u0189\3\2\2\2$\u018b\3\2\2\2&\u018d"+
		"\3\2\2\2(\u018f\3\2\2\2*\u0191\3\2\2\2,\u0194\3\2\2\2.\u0198\3\2\2\2\60"+
		"\u019a\3\2\2\2\62\u01bc\3\2\2\2\64\u01be\3\2\2\2\66\u01c0\3\2\2\28\u01c2"+
		"\3\2\2\2:\u01c4\3\2\2\2<\u01c6\3\2\2\2>\u01c8\3\2\2\2@\u01ca\3\2\2\2B"+
		"\u01cc\3\2\2\2D\u01ce\3\2\2\2F\u01d0\3\2\2\2H\u01d2\3\2\2\2J\u01d4\3\2"+
		"\2\2L\u01d6\3\2\2\2N\u01d8\3\2\2\2P\u01da\3\2\2\2R\u01dc\3\2\2\2T\u01de"+
		"\3\2\2\2VX\7\3\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Zd\3\2\2\2["+
		"Y\3\2\2\2\\`\7\4\2\2]_\5.\30\2^]\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2"+
		"ac\3\2\2\2b`\3\2\2\2ce\7&\2\2d\\\3\2\2\2de\3\2\2\2ef\3\2\2\2fj\5H%\2g"+
		"i\7\3\2\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2kp\3\2\2\2lj\3\2\2\2"+
		"mo\5\4\3\2nm\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2qv\3\2\2\2rp\3\2\2\2"+
		"su\7\3\2\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2"+
		"y}\5\n\6\2z|\7\3\2\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u0080"+
		"\3\2\2\2\177}\3\2\2\2\u0080\u0084\5J&\2\u0081\u0083\7\3\2\2\u0082\u0081"+
		"\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\3\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\5\30\r\2\u0088\u0089\5$\23"+
		"\2\u0089\u008b\7\t\2\2\u008a\u008c\5\6\4\2\u008b\u008a\3\2\2\2\u008b\u008c"+
		"\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\7\n\2\2\u008e\u008f\5L\'\2\u008f"+
		"\u0090\5\n\6\2\u0090\u0091\5J&\2\u0091\5\3\2\2\2\u0092\u0097\5\b\5\2\u0093"+
		"\u0094\7\r\2\2\u0094\u0096\5\b\5\2\u0095\u0093\3\2\2\2\u0096\u0099\3\2"+
		"\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\7\3\2\2\2\u0099\u0097"+
		"\3\2\2\2\u009a\u009b\5\30\r\2\u009b\u009c\5$\23\2\u009c\t\3\2\2\2\u009d"+
		"\u009e\b\6\1\2\u009e\u00e1\7\17\2\2\u009f\u00a0\5\30\r\2\u00a0\u00a1\5"+
		"$\23\2\u00a1\u00a2\7\20\2\2\u00a2\u00a3\5\22\n\2\u00a3\u00e1\3\2\2\2\u00a4"+
		"\u00a5\5\20\t\2\u00a5\u00a6\5\f\7\2\u00a6\u00e1\3\2\2\2\u00a7\u00a8\5"+
		"\20\t\2\u00a8\u00aa\5\16\b\2\u00a9\u00ab\5\f\7\2\u00aa\u00a9\3\2\2\2\u00aa"+
		"\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ae\5\22\n\2\u00ad\u00af\5"+
		"\f\7\2\u00ae\u00ad\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00e1\3\2\2\2\u00b0"+
		"\u00b1\7\21\2\2\u00b1\u00e1\5\20\t\2\u00b2\u00b3\7\26\2\2\u00b3\u00e1"+
		"\5 \21\2\u00b4\u00b5\7\23\2\2\u00b5\u00e1\5 \21\2\u00b6\u00b7\7\24\2\2"+
		"\u00b7\u00e1\5 \21\2\u00b8\u00b9\7\25\2\2\u00b9\u00e1\5 \21\2\u00ba\u00bb"+
		"\7\22\2\2\u00bb\u00e1\5 \21\2\u00bc\u00bd\5\64\33\2\u00bd\u00be\5 \21"+
		"\2\u00be\u00bf\5\66\34\2\u00bf\u00c0\5\n\6\2\u00c0\u00c1\58\35\2\u00c1"+
		"\u00c2\5\n\6\2\u00c2\u00c3\5:\36\2\u00c3\u00e1\3\2\2\2\u00c4\u00c5\5<"+
		"\37\2\u00c5\u00c6\5 \21\2\u00c6\u00c7\5> \2\u00c7\u00c8\5\n\6\2\u00c8"+
		"\u00c9\5@!\2\u00c9\u00e1\3\2\2\2\u00ca\u00cb\5D#\2\u00cb\u00cc\5\n\6\2"+
		"\u00cc\u00cd\5B\"\2\u00cd\u00ce\5 \21\2\u00ce\u00cf\5F$\2\u00cf\u00e1"+
		"\3\2\2\2\u00d0\u00d1\5N(\2\u00d1\u00d2\7\t\2\2\u00d2\u00d3\5\n\6\2\u00d3"+
		"\u00d4\5T+\2\u00d4\u00d5\5 \21\2\u00d5\u00d6\5T+\2\u00d6\u00d7\5\n\6\2"+
		"\u00d7\u00d8\7\n\2\2\u00d8\u00d9\5P)\2\u00d9\u00da\5\n\6\2\u00da\u00db"+
		"\5R*\2\u00db\u00e1\3\2\2\2\u00dc\u00dd\5H%\2\u00dd\u00de\5\n\6\2\u00de"+
		"\u00df\5J&\2\u00df\u00e1\3\2\2\2\u00e0\u009d\3\2\2\2\u00e0\u009f\3\2\2"+
		"\2\u00e0\u00a4\3\2\2\2\u00e0\u00a7\3\2\2\2\u00e0\u00b0\3\2\2\2\u00e0\u00b2"+
		"\3\2\2\2\u00e0\u00b4\3\2\2\2\u00e0\u00b6\3\2\2\2\u00e0\u00b8\3\2\2\2\u00e0"+
		"\u00ba\3\2\2\2\u00e0\u00bc\3\2\2\2\u00e0\u00c4\3\2\2\2\u00e0\u00ca\3\2"+
		"\2\2\u00e0\u00d0\3\2\2\2\u00e0\u00dc\3\2\2\2\u00e1\u00e7\3\2\2\2\u00e2"+
		"\u00e3\6\6\2\3\u00e3\u00e4\7&\2\2\u00e4\u00e6\5\n\6\2\u00e5\u00e2\3\2"+
		"\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"\13\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\7\5\2\2\u00eb\u00ef\7\5\2"+
		"\2\u00ec\u00ed\7\6\2\2\u00ed\u00ef\7\6\2\2\u00ee\u00ea\3\2\2\2\u00ee\u00ec"+
		"\3\2\2\2\u00ef\r\3\2\2\2\u00f0\u00f6\7\20\2\2\u00f1\u00f2\7\5\2\2\u00f2"+
		"\u00f6\7\20\2\2\u00f3\u00f4\7\6\2\2\u00f4\u00f6\7\20\2\2\u00f5\u00f0\3"+
		"\2\2\2\u00f5\u00f1\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6\17\3\2\2\2\u00f7"+
		"\u00fb\5$\23\2\u00f8\u00fb\5 \21\2\u00f9\u00fb\5\26\f\2\u00fa\u00f7\3"+
		"\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fb\21\3\2\2\2\u00fc"+
		"\u0115\5 \21\2\u00fd\u0115\5\62\32\2\u00fe\u00ff\7\'\2\2\u00ff\u0100\7"+
		"\t\2\2\u0100\u0101\5 \21\2\u0101\u0102\7\r\2\2\u0102\u0103\5 \21\2\u0103"+
		"\u0104\7\n\2\2\u0104\u0115\3\2\2\2\u0105\u0115\5\26\f\2\u0106\u0107\7"+
		"(\2\2\u0107\u0108\5$\23\2\u0108\u010a\7\t\2\2\u0109\u010b\5\24\13\2\u010a"+
		"\u0109\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010c\3\2\2\2\u010c\u010d\7\n"+
		"\2\2\u010d\u0115\3\2\2\2\u010e\u010f\5 \21\2\u010f\u0110\7\33\2\2\u0110"+
		"\u0111\5\22\n\2\u0111\u0112\7\34\2\2\u0112\u0113\5\22\n\2\u0113\u0115"+
		"\3\2\2\2\u0114\u00fc\3\2\2\2\u0114\u00fd\3\2\2\2\u0114\u00fe\3\2\2\2\u0114"+
		"\u0105\3\2\2\2\u0114\u0106\3\2\2\2\u0114\u010e\3\2\2\2\u0115\23\3\2\2"+
		"\2\u0116\u011b\5 \21\2\u0117\u0118\7\r\2\2\u0118\u011a\5 \21\2\u0119\u0117"+
		"\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c"+
		"\25\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u011f\7)\2\2\u011f\u0123\5 \21\2"+
		"\u0120\u0121\7*\2\2\u0121\u0123\5 \21\2\u0122\u011e\3\2\2\2\u0122\u0120"+
		"\3\2\2\2\u0123\27\3\2\2\2\u0124\u0125\b\r\1\2\u0125\u0128\5\32\16\2\u0126"+
		"\u0128\5\34\17\2\u0127\u0124\3\2\2\2\u0127\u0126\3\2\2\2\u0128\u012e\3"+
		"\2\2\2\u0129\u012a\6\r\3\3\u012a\u012b\7\13\2\2\u012b\u012d\7\f\2\2\u012c"+
		"\u0129\3\2\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2"+
		"\2\2\u012f\31\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u0136\7+\2\2\u0132\u0136"+
		"\7,\2\2\u0133\u0136\7-\2\2\u0134\u0136\7.\2\2\u0135\u0131\3\2\2\2\u0135"+
		"\u0132\3\2\2\2\u0135\u0133\3\2\2\2\u0135\u0134\3\2\2\2\u0136\33\3\2\2"+
		"\2\u0137\u0138\7/\2\2\u0138\u0139\7\t\2\2\u0139\u013a\5\36\20\2\u013a"+
		"\u013b\7\r\2\2\u013b\u013c\5\36\20\2\u013c\u013d\7\n\2\2\u013d\35\3\2"+
		"\2\2\u013e\u0142\5\32\16\2\u013f\u0142\5\30\r\2\u0140\u0142\7/\2\2\u0141"+
		"\u013e\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0140\3\2\2\2\u0142\37\3\2\2"+
		"\2\u0143\u0144\b\21\1\2\u0144\u0145\5\"\22\2\u0145\u0146\5 \21\2\u0146"+
		"\u0152\3\2\2\2\u0147\u0152\5,\27\2\u0148\u0152\5(\25\2\u0149\u0152\5*"+
		"\26\2\u014a\u0152\5.\30\2\u014b\u0152\5\60\31\2\u014c\u0152\5$\23\2\u014d"+
		"\u014e\7\t\2\2\u014e\u014f\5 \21\2\u014f\u0150\7\n\2\2\u0150\u0152\3\2"+
		"\2\2\u0151\u0143\3\2\2\2\u0151\u0147\3\2\2\2\u0151\u0148\3\2\2\2\u0151"+
		"\u0149\3\2\2\2\u0151\u014a\3\2\2\2\u0151\u014b\3\2\2\2\u0151\u014c\3\2"+
		"\2\2\u0151\u014d\3\2\2\2\u0152\u0181\3\2\2\2\u0153\u0154\6\21\4\3\u0154"+
		"\u0155\7\6\2\2\u0155\u0180\5 \21\2\u0156\u0157\6\21\5\3\u0157\u0158\7"+
		"\5\2\2\u0158\u0180\5 \21\2\u0159\u015a\6\21\6\3\u015a\u015b\7\64\2\2\u015b"+
		"\u0180\5 \21\2\u015c\u015d\6\21\7\3\u015d\u015e\7\65\2\2\u015e\u0180\5"+
		" \21\2\u015f\u0160\6\21\b\3\u0160\u0161\7\66\2\2\u0161\u0180\5 \21\2\u0162"+
		"\u0163\6\21\t\3\u0163\u0164\7\67\2\2\u0164\u0180\5 \21\2\u0165\u0166\6"+
		"\21\n\3\u0166\u0167\78\2\2\u0167\u0180\5 \21\2\u0168\u0169\6\21\13\3\u0169"+
		"\u016a\79\2\2\u016a\u0180\5 \21\2\u016b\u016c\6\21\f\3\u016c\u016d\7:"+
		"\2\2\u016d\u0180\5 \21\2\u016e\u016f\6\21\r\3\u016f\u0170\7;\2\2\u0170"+
		"\u0180\5 \21\2\u0171\u0172\6\21\16\3\u0172\u0173\7<\2\2\u0173\u0180\5"+
		" \21\2\u0174\u0175\6\21\17\3\u0175\u0176\7>\2\2\u0176\u0180\5 \21\2\u0177"+
		"\u0178\6\21\20\3\u0178\u0179\7=\2\2\u0179\u0180\5 \21\2\u017a\u017b\6"+
		"\21\21\3\u017b\u017c\7\13\2\2\u017c\u017d\5 \21\2\u017d\u017e\7\f\2\2"+
		"\u017e\u0180\3\2\2\2\u017f\u0153\3\2\2\2\u017f\u0156\3\2\2\2\u017f\u0159"+
		"\3\2\2\2\u017f\u015c\3\2\2\2\u017f\u015f\3\2\2\2\u017f\u0162\3\2\2\2\u017f"+
		"\u0165\3\2\2\2\u017f\u0168\3\2\2\2\u017f\u016b\3\2\2\2\u017f\u016e\3\2"+
		"\2\2\u017f\u0171\3\2\2\2\u017f\u0174\3\2\2\2\u017f\u0177\3\2\2\2\u017f"+
		"\u017a\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2"+
		"\2\2\u0182!\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u018a\7\60\2\2\u0185\u018a"+
		"\7\6\2\2\u0186\u018a\7\61\2\2\u0187\u018a\7\62\2\2\u0188\u018a\7\63\2"+
		"\2\u0189\u0184\3\2\2\2\u0189\u0185\3\2\2\2\u0189\u0186\3\2\2\2\u0189\u0187"+
		"\3\2\2\2\u0189\u0188\3\2\2\2\u018a#\3\2\2\2\u018b\u018c\7H\2\2\u018c%"+
		"\3\2\2\2\u018d\u018e\t\2\2\2\u018e\'\3\2\2\2\u018f\u0190\t\3\2\2\u0190"+
		")\3\2\2\2\u0191\u0192\7D\2\2\u0192+\3\2\2\2\u0193\u0195\5&\24\2\u0194"+
		"\u0193\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0197\7?"+
		"\2\2\u0197-\3\2\2\2\u0198\u0199\7E\2\2\u0199/\3\2\2\2\u019a\u019b\7F\2"+
		"\2\u019b\61\3\2\2\2\u019c\u01a5\7\13\2\2\u019d\u01a2\5 \21\2\u019e\u019f"+
		"\7\r\2\2\u019f\u01a1\5 \21\2\u01a0\u019e\3\2\2\2\u01a1\u01a4\3\2\2\2\u01a2"+
		"\u01a0\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2"+
		"\2\2\u01a5\u019d\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7"+
		"\u01bd\7\f\2\2\u01a8\u01aa\7\13\2\2\u01a9\u01a8\3\2\2\2\u01aa\u01ab\3"+
		"\2\2\2\u01ab\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01b5\3\2\2\2\u01ad"+
		"\u01b2\5\62\32\2\u01ae\u01af\7\r\2\2\u01af\u01b1\5\62\32\2\u01b0\u01ae"+
		"\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3"+
		"\u01b6\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01ad\3\2\2\2\u01b5\u01b6\3\2"+
		"\2\2\u01b6\u01b8\3\2\2\2\u01b7\u01b9\7\f\2\2\u01b8\u01b7\3\2\2\2\u01b9"+
		"\u01ba\3\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bd\3\2"+
		"\2\2\u01bc\u019c\3\2\2\2\u01bc\u01a9\3\2\2\2\u01bd\63\3\2\2\2\u01be\u01bf"+
		"\7\27\2\2\u01bf\65\3\2\2\2\u01c0\u01c1\7\30\2\2\u01c1\67\3\2\2\2\u01c2"+
		"\u01c3\7\31\2\2\u01c39\3\2\2\2\u01c4\u01c5\7\32\2\2\u01c5;\3\2\2\2\u01c6"+
		"\u01c7\7\35\2\2\u01c7=\3\2\2\2\u01c8\u01c9\7\36\2\2\u01c9?\3\2\2\2\u01ca"+
		"\u01cb\7\37\2\2\u01cbA\3\2\2\2\u01cc\u01cd\7\35\2\2\u01cdC\3\2\2\2\u01ce"+
		"\u01cf\7\36\2\2\u01cfE\3\2\2\2\u01d0\u01d1\7\37\2\2\u01d1G\3\2\2\2\u01d2"+
		"\u01d3\7\7\2\2\u01d3I\3\2\2\2\u01d4\u01d5\7\b\2\2\u01d5K\3\2\2\2\u01d6"+
		"\u01d7\7\16\2\2\u01d7M\3\2\2\2\u01d8\u01d9\7#\2\2\u01d9O\3\2\2\2\u01da"+
		"\u01db\7\36\2\2\u01dbQ\3\2\2\2\u01dc\u01dd\7\37\2\2\u01ddS\3\2\2\2\u01de"+
		"\u01df\7&\2\2\u01dfU\3\2\2\2\'Y`djpv}\u0084\u008b\u0097\u00aa\u00ae\u00e0"+
		"\u00e7\u00ee\u00f5\u00fa\u010a\u0114\u011b\u0122\u0127\u012e\u0135\u0141"+
		"\u0151\u017f\u0181\u0189\u0194\u01a2\u01a5\u01ab\u01b2\u01b5\u01ba\u01bc";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}