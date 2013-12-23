package code_generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import semantic_check.ArrayType;
import semantic_check.EnumType;
import semantic_check.PairType;
import semantic_check.SingleType;
import semantic_check.SymbolTable;
import semantic_check.TreeWalkerUtil;
import semantic_check.Type;
import antlr.WaccBaseVisitor;
import antlr.WaccParser;

public class PreCodeGenWalker extends WaccBaseVisitor<Integer> {

	private final SingleType INT = new SingleType(EnumType.INT);
	private final SingleType STRING = new SingleType(EnumType.STRING);

	private List<Integer> reservedByteList;
	private int index;
	private int msgCount;
	private int numPairs;
	private int numArrays;
	private Stack<Integer> reservedByteStack = new Stack<Integer>();
	private TreeWalkerUtil treeUtil = new TreeWalkerUtil(new SymbolTable());
	private HashMap<String, Integer> pairVarMapping;

	public PreCodeGenWalker(SymbolTable symbolTable) {
		this.index = 0;
		this.msgCount = 0;
		this.numPairs = 0;
		this.numArrays = 0;
		this.reservedByteList = new ArrayList<Integer>();
		this.pairVarMapping = new HashMap<String, Integer>();
	}

	@Override
	public Integer visitStat_type_ident_equals_assignRHS(WaccParser.Stat_type_ident_equals_assignRHSContext ctx) {
		Type type = treeUtil.getType(ctx.getChild(0));
		if (type.equals(INT) || type.equals(STRING) || type instanceof PairType || type instanceof ArrayType) {
			reservedByteList.set(index, reservedByteList.get(index) + 4);
		} else {
			reservedByteList.set(index, reservedByteList.get(index) + 1);
		}
		return super.visitStat_type_ident_equals_assignRHS(ctx);
	}

	public int getReservedByte(int index) {
		return reservedByteList.get(index);
	}

	public int getSize() {
		return reservedByteList.size();
	}

	public int getMsgCount() {
		return msgCount;
	}

	// ========================= BEGINEND / ISEND ========================= //
	@Override
	public Integer visitTerminal_begin(WaccParser.Terminal_beginContext ctx) {
		if (!(ctx.getParent() instanceof WaccParser.ProgramContext)) {
			reservedByteStack.push(index);
		}
		reservedByteList.add(0);
		index = reservedByteList.size() - 1;
		return super.visitTerminal_begin(ctx);
	}

	@Override
	public Integer visitTerminal_end(WaccParser.Terminal_endContext ctx) {
		if (!(ctx.getParent() instanceof WaccParser.ProgramContext)) {
			index = reservedByteStack.pop();
		}
		return super.visitTerminal_end(ctx);
	}

	@Override
	public Integer visitTerminal_is(WaccParser.Terminal_isContext ctx) {
		reservedByteStack.push(index);
		reservedByteList.add(0);
		index = reservedByteList.size() - 1;
		return super.visitTerminal_is(ctx);
	}

	// =========================== IFTHENELSE =========================== //
	@Override
	public Integer visitTerminal_if(WaccParser.Terminal_ifContext ctx) {
		return super.visitTerminal_if(ctx);
	}

	@Override
	public Integer visitTerminal_then(WaccParser.Terminal_thenContext ctx) {
		reservedByteStack.push(index);
		reservedByteList.add(0);
		index = reservedByteList.size() - 1;
		return super.visitTerminal_then(ctx);
	}

	@Override
	public Integer visitTerminal_else(WaccParser.Terminal_elseContext ctx) {
		reservedByteList.add(0);
		index = reservedByteList.size() - 1;
		return super.visitTerminal_else(ctx);
	}

	@Override
	public Integer visitTerminal_fi(WaccParser.Terminal_fiContext ctx) {
		index = reservedByteStack.pop();
		return super.visitTerminal_fi(ctx);
	}

	// =========================== WHILE =========================== //
	@Override
	public Integer visitTerminal_while(WaccParser.Terminal_whileContext ctx) {
		return super.visitTerminal_while(ctx);
	}

	@Override
	public Integer visitTerminal_do(WaccParser.Terminal_doContext ctx) {
		reservedByteStack.push(index);
		reservedByteList.add(0);
		index = reservedByteList.size() - 1;
		return super.visitTerminal_do(ctx);
	}

	@Override
	public Integer visitTerminal_done(WaccParser.Terminal_doneContext ctx) {
		index = reservedByteStack.pop();
		return super.visitTerminal_done(ctx);
	}

	// =========================== PAIRS =========================== //

	@Override
	public Integer visitAssignRHS_newPair(WaccParser.AssignRHS_newPairContext ctx) {
		numPairs++;
		return super.visitAssignRHS_newPair(ctx);
	}

	// =========================== Arrays =========================== //

	@Override
	public Integer visitArrayLiter(WaccParser.ArrayLiterContext ctx) {
		numArrays++;
		return super.visitArrayLiter(ctx);
	}

	public int getNumPairs() {
		return numPairs;
	}

	public int getNumArrays() {
		return numArrays;
	}

	public void addMapping(String ident, int pairOffset) {
		this.pairVarMapping.put(ident, pairOffset);
	}

	public int getVarMapping(String text) {
	 if(this.pairVarMapping.containsKey(text)){
	  return this.pairVarMapping.get(text);
	 }
	  return 0;
	}
}
