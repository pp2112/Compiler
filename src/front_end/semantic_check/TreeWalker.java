package semantic_check;

import antlr.WaccBaseVisitor;
import antlr.WaccParser;
import antlr.WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext;

import org.antlr.v4.runtime.tree.ParseTree;


public class TreeWalker extends WaccBaseVisitor<Type> {

	TreeWalkerUtil util = new TreeWalkerUtil(new SymbolTable());
	SymbolTable symbolTable = new SymbolTable();
	
	@Override public Type visitProgram(WaccParser.ProgramContext ctx) { return super.visitProgram(ctx); }
	
	@Override public Type visitFunc(WaccParser.FuncContext ctx) {
		util.checkFunction(ctx);
		return super.visitFunc(ctx);
	}

	@Override public Type visitParamList(WaccParser.ParamListContext ctx) { return super.visitParamList(ctx); }
	
	@Override public Type visitParam(WaccParser.ParamContext ctx) { return super.visitParam(ctx); }
	
	// ======================== STATMENTS ====================================//
	
	@Override
	public Type visitStat_begin_stat_end(WaccParser.Stat_begin_stat_endContext ctx) {
		checkStat(ctx);
		return super.visitStat_begin_stat_end(ctx);
	}
	
	@Override
	public Type visitStat_Read_assignLHS(WaccParser.Stat_Read_assignLHSContext ctx) {
		checkStat(ctx);
		return super.visitStat_Read_assignLHS(ctx);
	}

	@Override
	public Type visitStat_assignLHS_assignOp_assignRHS(
			WaccParser.Stat_assignLHS_assignOp_assignRHSContext ctx) {
		checkStat(ctx);
		return super.visitStat_assignLHS_assignOp_assignRHS(ctx);
	}
	
	
	
	@Override
	public Type visitStat_for_stat_expr_stat_fdo_stat_fdone(
			Stat_for_stat_expr_stat_fdo_stat_fdoneContext ctx) {
		checkStat(ctx);
		return super.visitStat_for_stat_expr_stat_fdo_stat_fdone(ctx);
	}
	
	
	@Override
	public Type visitStat_if_expr_then_stat_else_stat_fi(
			WaccParser.Stat_if_expr_then_stat_else_stat_fiContext ctx) {
		
		checkStat(ctx);
		return super.visitStat_if_expr_then_stat_else_stat_fi(ctx);
	}

	@Override
	public Type visitStat_Free_expr(WaccParser.Stat_Free_exprContext ctx) {
		checkStat(ctx);
		return super.visitStat_Free_expr(ctx);
	}

	@Override
	public Type visitStat_semicolon_stat(WaccParser.Stat_semicolon_statContext ctx) {
		checkStat(ctx);
		return super.visitStat_semicolon_stat(ctx);
	}

	@Override
	public Type visitStat_while_expr_do_stat_done(
			WaccParser.Stat_while_expr_do_stat_doneContext ctx) {
		checkStat(ctx);
		return super.visitStat_while_expr_do_stat_done(ctx);
	}

	@Override
	public Type visitStat_Print_expr(WaccParser.Stat_Print_exprContext ctx) {
		checkStat(ctx);
		return super.visitStat_Print_expr(ctx);
	}
	
	@Override
	public Type visitStat_type_ident_equals_assignRHS(
			WaccParser.Stat_type_ident_equals_assignRHSContext ctx) {
		checkStat(ctx);
		return super.visitStat_type_ident_equals_assignRHS(ctx);
	}
	
	@Override
	public Type visitStat_Exit_expr(WaccParser.Stat_Exit_exprContext ctx) {
		checkStat(ctx);
		return super.visitStat_Exit_expr(ctx);
	}
	
	@Override
	public Type visitStat_Println_expr(WaccParser.Stat_Println_exprContext ctx) {
		checkStat(ctx);
		return super.visitStat_Println_expr(ctx);
	}
	
	@Override
	public Type visitStat_SKIP(WaccParser.Stat_SKIPContext ctx) {
		checkStat(ctx);
		return super.visitStat_SKIP(ctx);
	}
	
	@Override
	public Type visitStat_Return_expr(WaccParser.Stat_Return_exprContext ctx) {
		checkStat(ctx);
		return super.visitStat_Return_expr(ctx);
	}
	
	// =============================== ASSIGNLHS ========================================== //
	
	@Override public Type visitAssignLHS_pairElem(WaccParser.AssignLHS_pairElemContext ctx) { return super.visitAssignLHS_pairElem(ctx); }
	
	@Override public Type visitAssignRHS_newPair(WaccParser.AssignRHS_newPairContext ctx) { return super.visitAssignRHS_newPair(ctx); }
	
	@Override public Type visitAssignLHS_ident(WaccParser.AssignLHS_identContext ctx) { return super.visitAssignLHS_ident(ctx); }
	
	@Override public Type visitAssignLHS_expr(WaccParser.AssignLHS_exprContext ctx) { return super.visitAssignLHS_expr(ctx); }
	
	// =============================== ASSIGNRHS ========================================== // 
	
	@Override
	public Type visitAssignRHS_expr(WaccParser.AssignRHS_exprContext ctx) {
		return super.visitAssignRHS_expr(ctx);
	}
	
	@Override
	public Type visitAssignRHS_pairElem(WaccParser.AssignRHS_pairElemContext ctx) {
		return super.visitAssignRHS_pairElem(ctx);
	}
	
	@Override
	public Type visitAssignRHS_call(WaccParser.AssignRHS_callContext ctx) {
		return super.visitAssignRHS_call(ctx);
	}
	
	@Override
	public Type visitAssignRHS_arrayLit(WaccParser.AssignRHS_arrayLitContext ctx) {
		return super.visitAssignRHS_arrayLit(ctx);
	}

	
	// ================================= EXPR ============================================= //
	
	@Override public Type visitExpr_int(WaccParser.Expr_intContext ctx) { return new SingleType(EnumType.INT); }
	
	@Override public Type visitExpr_char(WaccParser.Expr_charContext ctx) { return new SingleType(EnumType.CHAR); }
	
	@Override
	public Type visitExpr_str(WaccParser.Expr_strContext ctx) { return new SingleType(EnumType.STRING); }
	
	@Override public Type visitExpr_bool(WaccParser.Expr_boolContext ctx) { return new SingleType(EnumType.BOOL);}
	
	@Override
	public Type visitExpr_PLUS(WaccParser.Expr_PLUSContext ctx) {
		if (!(util.matchType(ctx.getChild(0), new SingleType(EnumType.INT)) &&
			util.matchType(ctx.getChild(2), new SingleType(EnumType.INT)))){}
		return super.visitExpr_PLUS(ctx);
	}
	
	@Override
	public Type visitExpr_MINUS(WaccParser.Expr_MINUSContext ctx) {
		if (!(util.matchType(ctx.getChild(0), new SingleType(EnumType.INT)) &&
			util.matchType(ctx.getChild(2), new SingleType(EnumType.INT)))){}
		return super.visitExpr_MINUS(ctx);
	}
	
	@Override
	public Type visitExpr_MULT(WaccParser.Expr_MULTContext ctx) {
		if (!(util.matchType(ctx.getChild(0), new SingleType(EnumType.INT)) &&
			util.matchType(ctx.getChild(2), new SingleType(EnumType.INT)))){}
		return super.visitExpr_MULT(ctx);
	}
	
	@Override
	public Type visitExpr_DIV(WaccParser.Expr_DIVContext ctx) {
		if (!(util.matchType(ctx.getChild(0), new SingleType(EnumType.INT)) &&
			util.matchType(ctx.getChild(2), new SingleType(EnumType.INT)))){}
		return super.visitExpr_DIV(ctx);
	}
	
	@Override
	public Type visitExpr_ident(WaccParser.Expr_identContext ctx) {
		return symbolTable.getType(ctx.getText());
	}
	
	@Override
	public Type visitExpr_LTEQ(WaccParser.Expr_LTEQContext ctx) {
		return super.visitExpr_LTEQ(ctx);
	}
	
	@Override
	public Type visitExpr_NEQ(WaccParser.Expr_NEQContext ctx) {
		return super.visitExpr_NEQ(ctx);
	}
	
	@Override
	public Type visitExpr_AND(WaccParser.Expr_ANDContext ctx) {
		return super.visitExpr_AND(ctx);
	}
	
	@Override
	public Type visitExpr_expr(WaccParser.Expr_exprContext ctx) {
		return super.visitExpr_expr(ctx);
	}

	@Override
	public Type visitExpr_GT(WaccParser.Expr_GTContext ctx) {
		return super.visitExpr_GT(ctx);
	}
	
	@Override
	public Type visitExpr_OR(WaccParser.Expr_ORContext ctx) {
		return super.visitExpr_OR(ctx);
	}

	@Override
	public Type visitExpr_arr(WaccParser.Expr_arrContext ctx) {
		return super.visitExpr_arr(ctx);
	}
	
	@Override
	public Type visitExpr_pairlit(WaccParser.Expr_pairlitContext ctx) {
		
		return super.visitExpr_pairlit(ctx);
	}

	@Override
	public Type visitExpr_EQ(WaccParser.Expr_EQContext ctx) {
		
		return super.visitExpr_EQ(ctx);
	}
	
	@Override
	public Type visitExpr_GTEQ(WaccParser.Expr_GTEQContext ctx) {
		return super.visitExpr_GTEQ(ctx);
	}

	@Override
	public Type visitExpr_LT(WaccParser.Expr_LTContext ctx) {
		return super.visitExpr_LT(ctx);
	}
	
	@Override
	public Type visitExpr_unary(WaccParser.Expr_unaryContext ctx) {
		return super.visitExpr_unary(ctx);
	}
	
	// ================================ BASE TYPE ==================================== //
	
	@Override
	public Type visitBase_CHAR(WaccParser.Base_CHARContext ctx) {
		return super.visitBase_CHAR(ctx);
	}
	
	@Override
	public Type visitBase_INT(WaccParser.Base_INTContext ctx) {
		return super.visitBase_INT(ctx);
	}
	
	@Override
	public Type visitBase_STR(WaccParser.Base_STRContext ctx) {
		return super.visitBase_STR(ctx);
	}
	
	@Override
	public Type visitBase_BOOL(WaccParser.Base_BOOLContext ctx) {
		return super.visitBase_BOOL(ctx);
	}
	
	// ==================================== LITERALS ====================================== //
	
	@Override
	public Type visitStringLiter(WaccParser.StringLiterContext ctx) {
		return new SingleType(EnumType.STRING);
	}
	
	@Override
	public Type visitIntLiter(WaccParser.IntLiterContext ctx) {
		return new SingleType(EnumType.INT);
	}
	
	@Override
	public Type visitCharLiter(WaccParser.CharLiterContext ctx) {
		return new SingleType(EnumType.CHAR);
	}
	
	@Override
	public Type visitBoolLiter(WaccParser.BoolLiterContext ctx) {
		return new SingleType(EnumType.BOOL);
	}
	
	// =================================== UNARY EXPR ===================================== //
	
	@Override
	public Type visitUnary_int(WaccParser.Unary_intContext ctx) {
		return super.visitUnary_int(ctx);
	}
	
	@Override
	public Type visitUnary_len(WaccParser.Unary_lenContext ctx) {
		return super.visitUnary_len(ctx);
	}
	
	@Override
	public Type visitUnary_ord(WaccParser.Unary_ordContext ctx) {
		return super.visitUnary_ord(ctx);
	}
	
	@Override
	public Type visitUnary_exc(WaccParser.Unary_excContext ctx) {
		return super.visitUnary_exc(ctx);
	}
	
	@Override
	public Type visitUnary_minus(WaccParser.Unary_minusContext ctx) {
		return super.visitUnary_minus(ctx);
	}
	
	// =================================== TERMINAL ======================================= //
	
	@Override
	public Type visitTerminal_then(WaccParser.Terminal_thenContext ctx){
		symbolTable = new SymbolTable(symbolTable);
		return super.visitTerminal_then(ctx);
	}
	
	@Override
	public Type visitTerminal_else(WaccParser.Terminal_elseContext ctx){
		symbolTable = symbolTable.getPrevTable();
		symbolTable = new SymbolTable(symbolTable);
		return super.visitTerminal_else(ctx);
	}
	
	@Override
	public Type visitTerminal_fi(WaccParser.Terminal_fiContext ctx){
		symbolTable = symbolTable.getPrevTable();
		return super.visitTerminal_fi(ctx);
	}
	
	@Override
	public Type visitTerminal_do(WaccParser.Terminal_doContext ctx){
		symbolTable = new SymbolTable(symbolTable);
		return super.visitTerminal_do(ctx);
	}
	
	@Override
	public Type visitTerminal_done(WaccParser.Terminal_doneContext ctx){
		symbolTable = symbolTable.getPrevTable();
		return super.visitTerminal_done(ctx);
	}
	
	@Override
	public Type visitTerminal_is(WaccParser.Terminal_isContext ctx){
		symbolTable = new SymbolTable(symbolTable);
		return super.visitTerminal_is(ctx);
	}
	
	@Override
	public Type visitTerminal_begin(WaccParser.Terminal_beginContext ctx){
		symbolTable = new SymbolTable(symbolTable);
		return super.visitTerminal_begin(ctx);
	}
	
	@Override
	public Type visitTerminal_end(WaccParser.Terminal_endContext ctx){
		symbolTable = symbolTable.getPrevTable();
		return super.visitTerminal_end(ctx);
	}
	
	// ==================================================================================== //
	
	@Override
	public Type visitArgList(WaccParser.ArgListContext ctx) {
		return super.visitArgList(ctx);
	}
	
	@Override
	public Type visitPairElem_FST_expr(WaccParser.PairElem_FST_exprContext ctx) {
		return super.visitPairElem_FST_expr(ctx);
	}
	
	@Override
	public Type visitPairElem_SND_expr(WaccParser.PairElem_SND_exprContext ctx) {
		return super.visitPairElem_SND_expr(ctx);
	}

	@Override
	public Type visitPairLiter(WaccParser.PairLiterContext ctx) {
		return super.visitPairLiter(ctx);
	}

	@Override
	public Type visitPair_PAIR(WaccParser.Pair_PAIRContext ctx) {
		return super.visitPair_PAIR(ctx);
	}

	@Override
	public Type visitPairType(WaccParser.PairTypeContext ctx) {
		return super.visitPairType(ctx);
	}
	
	@Override
	public Type visitPair_base_t(WaccParser.Pair_base_tContext ctx) {
		return super.visitPair_base_t(ctx);
	}

	@Override
	public Type visitPair_type(WaccParser.Pair_typeContext ctx) {
		return super.visitPair_type(ctx);
	}

	@Override
	public Type visitArrayLiter(WaccParser.ArrayLiterContext ctx) {
		
		return super.visitArrayLiter(ctx);
	}

	@Override
	public Type visitType_base(WaccParser.Type_baseContext ctx) {
		
		return super.visitType_base(ctx);
	}

	@Override
	public Type visitType_arr(WaccParser.Type_arrContext ctx) {
		return super.visitType_arr(ctx);
	}
	
	@Override
	public Type visitType_pair(WaccParser.Type_pairContext ctx) {
		return super.visitType_pair(ctx);
	}

	@Override
	public Type visitIdent(WaccParser.IdentContext ctx) {
		return super.visitIdent(ctx);
	}

	public void checkStat(ParseTree ctx){
		if (ctx.getParent() instanceof WaccParser.ProgramContext){
			//util.checkFuncBody();
			if (!(util.isStatValid(ctx))){
				System.out.println("File cannot be compiled.");
				System.exit(200);
			}
		}
	}
}