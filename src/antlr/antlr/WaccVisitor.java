package antlr;

// Generated from ./Wacc.g4 by ANTLR 4.1
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WaccParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WaccVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WaccParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(@NotNull WaccParser.ArgListContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pairElem_SND_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElem_SND_expr(@NotNull WaccParser.PairElem_SND_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_type_ident_equals_assignRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_type_ident_equals_assignRHS(@NotNull WaccParser.Stat_type_ident_equals_assignRHSContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_dwhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_dwhile(@NotNull WaccParser.Terminal_dwhileContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#base_CHAR}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_CHAR(@NotNull WaccParser.Base_CHARContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignLHS_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLHS_ident(@NotNull WaccParser.AssignLHS_identContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_while(@NotNull WaccParser.Terminal_whileContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_end(@NotNull WaccParser.Terminal_endContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_fi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_fi(@NotNull WaccParser.Terminal_fiContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_OR}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_OR(@NotNull WaccParser.Expr_ORContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pairLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairLiter(@NotNull WaccParser.PairLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Read_assignLHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Read_assignLHS(@NotNull WaccParser.Stat_Read_assignLHSContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_if_expr_then_stat_else_stat_fi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_if_expr_then_stat_else_stat_fi(@NotNull WaccParser.Stat_if_expr_then_stat_else_stat_fiContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_ddo_stat_dwhile_expr_ddone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_ddo_stat_dwhile_expr_ddone(@NotNull WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_str(@NotNull WaccParser.Expr_strContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_LTEQ}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_LTEQ(@NotNull WaccParser.Expr_LTEQContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Free_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Free_expr(@NotNull WaccParser.Stat_Free_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pair_PAIR}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_PAIR(@NotNull WaccParser.Pair_PAIRContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unary_ord}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_ord(@NotNull WaccParser.Unary_ordContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignOp_equals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignOp_equals(@NotNull WaccParser.AssignOp_equalsContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_expr(@NotNull WaccParser.AssignRHS_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_MINUS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_MINUS(@NotNull WaccParser.Expr_MINUSContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_arr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_arr(@NotNull WaccParser.Expr_arrContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pairType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairType(@NotNull WaccParser.PairTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_pairlit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_pairlit(@NotNull WaccParser.Expr_pairlitContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_EQ}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_EQ(@NotNull WaccParser.Expr_EQContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_if_then_else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_if_then_else(@NotNull WaccParser.AssignRHS_if_then_elseContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_begin_stat_end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_begin_stat_end(@NotNull WaccParser.Stat_begin_stat_endContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unary_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_int(@NotNull WaccParser.Unary_intContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Return_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Return_expr(@NotNull WaccParser.Stat_Return_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_int(@NotNull WaccParser.Expr_intContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#base_STR}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_STR(@NotNull WaccParser.Base_STRContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(@NotNull WaccParser.ParamContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unary_len}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_len(@NotNull WaccParser.Unary_lenContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_assignLHS_unaryAssignOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_assignLHS_unaryAssignOp(@NotNull WaccParser.Stat_assignLHS_unaryAssignOpContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#boolLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiter(@NotNull WaccParser.BoolLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_MOD}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_MOD(@NotNull WaccParser.Expr_MODContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignLHS_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLHS_expr(@NotNull WaccParser.AssignLHS_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#type_pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_pair(@NotNull WaccParser.Type_pairContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_do}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_do(@NotNull WaccParser.Terminal_doContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_while_expr_do_stat_done}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_while_expr_do_stat_done(@NotNull WaccParser.Stat_while_expr_do_stat_doneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_LT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_LT(@NotNull WaccParser.Expr_LTContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Exit_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Exit_expr(@NotNull WaccParser.Stat_Exit_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignOp_minus_equals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignOp_minus_equals(@NotNull WaccParser.AssignOp_minus_equalsContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_unary(@NotNull WaccParser.Expr_unaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pair_base_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_base_t(@NotNull WaccParser.Pair_base_tContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_fdone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_fdone(@NotNull WaccParser.Terminal_fdoneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Print_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Print_expr(@NotNull WaccParser.Stat_Print_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_AND}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_AND(@NotNull WaccParser.Expr_ANDContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_Println_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_Println_expr(@NotNull WaccParser.Stat_Println_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_GT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_GT(@NotNull WaccParser.Expr_GTContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stringLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiter(@NotNull WaccParser.StringLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_MULT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_MULT(@NotNull WaccParser.Expr_MULTContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#intSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntSign(@NotNull WaccParser.IntSignContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignOp_plus_equals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignOp_plus_equals(@NotNull WaccParser.AssignOp_plus_equalsContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_DIV}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_DIV(@NotNull WaccParser.Expr_DIVContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_ddo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_ddo(@NotNull WaccParser.Terminal_ddoContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull WaccParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignLHS_pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLHS_pairElem(@NotNull WaccParser.AssignLHS_pairElemContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_char}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_char(@NotNull WaccParser.Expr_charContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_ident(@NotNull WaccParser.Expr_identContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unaryAssignOp_minus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryAssignOp_minus(@NotNull WaccParser.UnaryAssignOp_minusContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(@NotNull WaccParser.FuncContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_fdo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_fdo(@NotNull WaccParser.Terminal_fdoContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_for_stat_expr_stat_fdo_stat_fdone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_for_stat_expr_stat_fdo_stat_fdone(@NotNull WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#intLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiter(@NotNull WaccParser.IntLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#base_INT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_INT(@NotNull WaccParser.Base_INTContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_PLUS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_PLUS(@NotNull WaccParser.Expr_PLUSContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_NEQ}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_NEQ(@NotNull WaccParser.Expr_NEQContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#base_BOOL}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_BOOL(@NotNull WaccParser.Base_BOOLContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_pairElem(@NotNull WaccParser.AssignRHS_pairElemContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull WaccParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_is}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_is(@NotNull WaccParser.Terminal_isContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#arrayLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiter(@NotNull WaccParser.ArrayLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_if(@NotNull WaccParser.Terminal_ifContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#type_base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_base(@NotNull WaccParser.Type_baseContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_GTEQ}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_GTEQ(@NotNull WaccParser.Expr_GTEQContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_call(@NotNull WaccParser.AssignRHS_callContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_semicolon_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_semicolon_stat(@NotNull WaccParser.Stat_semicolon_statContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_done}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_done(@NotNull WaccParser.Terminal_doneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_assignLHS_assignOp_assignRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_assignLHS_assignOp_assignRHS(@NotNull WaccParser.Stat_assignLHS_assignOp_assignRHSContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_newPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_newPair(@NotNull WaccParser.AssignRHS_newPairContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#type_arr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_arr(@NotNull WaccParser.Type_arrContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(@NotNull WaccParser.IdentContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#charLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharLiter(@NotNull WaccParser.CharLiterContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_ddone}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_ddone(@NotNull WaccParser.Terminal_ddoneContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_bool(@NotNull WaccParser.Expr_boolContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_then}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_then(@NotNull WaccParser.Terminal_thenContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unaryAssignOp_plus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryAssignOp_plus(@NotNull WaccParser.UnaryAssignOp_plusContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pair_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_type(@NotNull WaccParser.Pair_typeContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_semicolon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_semicolon(@NotNull WaccParser.Terminal_semicolonContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_for(@NotNull WaccParser.Terminal_forContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unary_minus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_minus(@NotNull WaccParser.Unary_minusContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_else(@NotNull WaccParser.Terminal_elseContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#terminal_begin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal_begin(@NotNull WaccParser.Terminal_beginContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#stat_SKIP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_SKIP(@NotNull WaccParser.Stat_SKIPContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#assignRHS_arrayLit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRHS_arrayLit(@NotNull WaccParser.AssignRHS_arrayLitContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#expr_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_expr(@NotNull WaccParser.Expr_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#unary_exc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_exc(@NotNull WaccParser.Unary_excContext ctx);

	/**
	 * Visit a parse tree produced by {@link WaccParser#pairElem_FST_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElem_FST_expr(@NotNull WaccParser.PairElem_FST_exprContext ctx);
}