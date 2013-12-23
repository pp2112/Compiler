package generator;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import semantic_check.EnumType;
import semantic_check.Type;

import code.Branch;
import code.Code;
import code.Directive;
import code.Immediate;
import code.Label;
import code.LabelTable;
import code.Operand;
import code.Operation;
import code.Register;
import code_generator.CodeUtil;

public class ForLoopGenerator extends FunctionGenerator{

	private final int declaration = 0;
	private final int condition = 1;
	private final int assignment = 2;
	private final int loopBody = 3;
	
	private List<FunctionGenerator> generators;
	private int branchCount;
	private FunctionGenerator delegate;
	private int current;
	private FunctionGenerator compiledCode;
	
	public ForLoopGenerator(FunctionGenerator previous, int branchCount){
		super(previous);
		this.branchCount = branchCount;
		generators = new ArrayList<FunctionGenerator>();
		generators.add(new FunctionGenerator(previous));
		generators.add(new FunctionGenerator(generators.get(declaration)));
		generators.add(new FunctionGenerator(generators.get(condition)));
		generators.add(new FunctionGenerator(generators.get(assignment)));
		delegate = generators.get(declaration);
		current = 0;
	}
	
	public void next(){
			current++;
			delegate = generators.get(current);
	}
	
	@Override
	public void add(Code code){
		delegate.add(code);
	}
	
	@Override
	public String getLabel(){
		
		return delegate.getLabel();
	}
	
	@Override
	public void setCurrentByte(int currentByte){
		delegate.setCurrentByte(currentByte);
	}
	
	@Override
	public int getCurrentByte(){
		return delegate.getCurrentByte();
	}
	
	@Override
	public void updateCurrentByte(int offset){
		delegate.updateCurrentByte(offset);
	}
	
	@Override
	public int getOffset(String id)
	{
		return delegate.getOffset(id);
	}
	
	@Override
	public int getIdentOffset(String id){
		return delegate.getIdentOffset(id);
	}
	
	@Override
	public int getLastOffset(){
		return delegate.getLastOffset();
	}
	
	@Override
	public Type getType(String id){
		return delegate.getType(id);
	}
	
	@Override
	public EnumType getEnumType(String id){
	  return delegate.getEnumType(id);
	}
	
	@Override
	public FunctionGenerator getPrevious(){
		return delegate.getPrevious();
	}
	
	@Override
	public void updateAllOffset(int offset){
		for(FunctionGenerator generator : generators)
			generator.updateCurrentOffset(offset);
		if (previous != null) {
			previous.updateAllOffset(offset);
		}
	}
	
	@Override
	public void updateCurrentOffset(int offset){
		delegate.updateCurrentOffset(offset);
	}
	
	@Override
	public void addDirective(String directive){
		delegate.addDirective(directive);
	}
	
	@Override
	public void addBranch(int label){
		delegate.addBranch(label);
	}
	
	@Override
	public void addLabel(String id, int offset, Type type){
		delegate.addLabel(id, offset, type);
	}
	
	@Override
	public LabelTable getLabelTable(){
		return delegate.getLabelTable();
	}
	
	@Override
	public void addCode(){
		compiledCode = new FunctionGenerator();
		delegate = compiledCode;
		addChildCode(generators.get(declaration));
		addBranch(branchCount);
		addChildCode(generators.get(condition));
		add(new Operation(Operand.CMP, Register.r0, new Immediate(1)));
		//true
		add(new Operation(Operand.BEQ, new Label("L" + (branchCount+2))));
		//false, exit loop
		add(new Operation(Operand.BNE, new Label("L" + (branchCount+3))));
		addBranch(branchCount+1);
		addChildCode(generators.get(assignment));
		add(new Operation(Operand.B, new Label("L" + (branchCount+0))));
		addBranch(branchCount+2);
		addChildCode(generators.get(loopBody));
		add(new Operation(Operand.B, new Label("L" + (branchCount+1))));
		addBranch(branchCount+3);
		if (maxByte != 0){
			add(new Operation(Operand.ADD, Register.sp, new Register(CodeUtil.sp,maxByte)));
			updateAllOffset(-maxByte);
		}
		delegate.setPrevious(this.previous);
		delegate.addCode();
	}
	
	public void addChildCode(FunctionGenerator func){
		func.setPrevious(compiledCode);
		func.addCode();
	}
	
}
