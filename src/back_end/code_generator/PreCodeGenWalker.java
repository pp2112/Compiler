package code_generator;

import antlr.WaccBaseVisitor;
import antlr.WaccParser;

import generator.ForLoopGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.ParseTree;

import code.Operand;
import code.Operation;
import code.Register;

import semantic_check.ArrayType;
import semantic_check.PairType;
import semantic_check.SymbolTable;
import semantic_check.TreeWalkerUtil;
import semantic_check.Type;

public class PreCodeGenWalker extends WaccBaseVisitor<Integer>{
	
	private int msgCount;
	private int reservedByteListIndex;
	private TreeWalkerUtil treeUtil;
	private List<Integer> reservedByteList;
	private Stack<Integer> reservedByteStack;
	
	public PreCodeGenWalker(){
		this.msgCount = 0;
		this.reservedByteListIndex = 0;
		this.treeUtil = new TreeWalkerUtil(new SymbolTable());
		this.reservedByteList = new ArrayList<Integer>();
		this.reservedByteStack = new Stack<Integer>();
	}
	
	public int getReservedByte(int index){
		return reservedByteList.get(index);
	}
	
	public int getMsgCount(){
		return msgCount;
	}
	
	@Override
	public Integer visitStat_type_ident_equals_assignRHS(WaccParser.Stat_type_ident_equals_assignRHSContext ctx) {
		Type type = treeUtil.getType(ctx.getChild(0));
		if (type.equals(CodeUtil.INT) || type.equals(CodeUtil.STRING) ||
				type instanceof PairType) {
			reservedByteList.set(reservedByteListIndex, reservedByteList.get(reservedByteListIndex) + 4);
		} else if (type.equals(CodeUtil.BOOL) || type.equals(CodeUtil.CHAR)){
			reservedByteList.set(reservedByteListIndex, reservedByteList.get(reservedByteListIndex) + 1);
		} else if (type instanceof ArrayType){
			reservedByteList.set(reservedByteListIndex, reservedByteList.get(reservedByteListIndex) + 4);// + getArrayByte(ctx.getChild(3).getChild(0)));
		}
		return super.visitStat_type_ident_equals_assignRHS(ctx);
	}
	
	private int getArrayByte(ParseTree ctx){
		int bytes = 0; 
		
			for(int i = 1 ; i < ctx.getChildCount() ; i+=2){
				if(ctx.getChild(i) instanceof WaccParser.ArrayLiterContext){
					bytes += 4;
					bytes += getArrayByte(ctx.getChild(i));
				}
			}
				
		return bytes;
	}
	// =========================== Message Counting =========================== //

	@Override
	public Integer visitStat_Print_expr(WaccParser.Stat_Print_exprContext ctx) {
		msgCount++;
		return super.visitStat_Print_expr(ctx);
	}

	@Override
	public Integer visitStat_Println_expr(WaccParser.Stat_Println_exprContext ctx) {
		msgCount++;
		return super.visitStat_Println_expr(ctx);
	}

	@Override
	public Integer visitExpr_EQ(WaccParser.Expr_EQContext ctx) {
		msgCount += (ctx.getChild(0) instanceof WaccParser.Expr_strContext) ? 2 : 0;
		return super.visitExpr_EQ(ctx);
	}

	@Override
	public Integer visitExpr_NEQ(WaccParser.Expr_NEQContext ctx) {
		msgCount += (ctx.getChild(0) instanceof WaccParser.Expr_strContext) ? 2 : 0;
		return super.visitExpr_NEQ(ctx);
	}
	
	// ==================== Reserved Byte Counting ==================== //
	@Override
	public Integer visitTerminal_begin(WaccParser.Terminal_beginContext ctx) {
		if (!(ctx.getParent() instanceof WaccParser.ProgramContext)) {
			reservedByteStack.push(reservedByteListIndex);
		}
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_begin(ctx);
	}

	@Override
	public Integer visitTerminal_end(WaccParser.Terminal_endContext ctx) {
		if (!(ctx.getParent() instanceof WaccParser.ProgramContext)) {
			reservedByteListIndex = reservedByteStack.pop();
		}
		return super.visitTerminal_end(ctx);
	}

	@Override
	public Integer visitTerminal_is(WaccParser.Terminal_isContext ctx) {
		reservedByteStack.push(reservedByteListIndex);
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_is(ctx);
	}

	@Override
	public Integer visitTerminal_then(WaccParser.Terminal_thenContext ctx) {
		reservedByteStack.push(reservedByteListIndex);
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_then(ctx);
	}

	@Override
	public Integer visitTerminal_else(WaccParser.Terminal_elseContext ctx) {
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_else(ctx);
	}

	@Override
	public Integer visitTerminal_fi(WaccParser.Terminal_fiContext ctx) {
		reservedByteListIndex = reservedByteStack.pop();
		return super.visitTerminal_fi(ctx);
	}


	@Override
	public Integer visitTerminal_do(WaccParser.Terminal_doContext ctx) {
		reservedByteStack.push(reservedByteListIndex);
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_do(ctx);
	}

	@Override
	public Integer visitTerminal_done(WaccParser.Terminal_doneContext ctx) {
		reservedByteListIndex = reservedByteStack.pop();
		return super.visitTerminal_done(ctx);
	}
	
	@Override public Integer visitTerminal_for(WaccParser.Terminal_forContext ctx){
		reservedByteStack.push(reservedByteListIndex);
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
		return super.visitTerminal_for(ctx);
	}
	
	@Override public Integer visitTerminal_fdone(WaccParser.Terminal_fdoneContext ctx){
		reservedByteListIndex = reservedByteStack.pop();
		return super.visitTerminal_fdone(ctx);
	}
//=============================== DOWHILEDONE ===========================//
	
	@Override public Integer visitTerminal_ddo(WaccParser.Terminal_ddoContext ctx) { 
		reservedByteStack.push(reservedByteListIndex);
		reservedByteList.add(0);
		reservedByteListIndex = reservedByteList.size() - 1;
			return super.visitTerminal_ddo(ctx); 
		}

	@Override public Integer visitTerminal_dwhile(WaccParser.Terminal_dwhileContext ctx) { 
			return super.visitTerminal_dwhile(ctx); 
		}

	@Override public Integer visitTerminal_ddone(WaccParser.Terminal_ddoneContext ctx) { 
			reservedByteListIndex = reservedByteStack.pop();
			return super.visitTerminal_ddone(ctx); 
		}

	
}