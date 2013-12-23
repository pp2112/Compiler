package semantic_check;

import antlr.WaccParser;

import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTree;


public class TreeWalkerUtil {
	
	private final SingleType ERROR  = new SingleType(EnumType.ERROR);
	private final SingleType NULL   = new SingleType(EnumType.NULL);
	private final SingleType BOOL   = new SingleType(EnumType.BOOL);
	private final SingleType INT    = new SingleType(EnumType.INT);
	private final SingleType CHAR   = new SingleType(EnumType.CHAR);
	private final SingleType STRING = new SingleType(EnumType.STRING);
	
	private SymbolTable current = new SymbolTable();
	private HashMap<ParseTree,SymbolTable> funcBody = new HashMap<ParseTree,SymbolTable>();
	
	public TreeWalkerUtil(SymbolTable currentSymbolTable){
		this.current = currentSymbolTable;
	}
	
	public void checkFunction(ParseTree ctx){
		ParseTree funcStat;
		SymbolTable params;
		int numParams = 0;
		String funcName;
		if (ctx.getChild(3) instanceof WaccParser.ParamListContext) {
			funcStat = ctx.getChild(6);
			numParams = ((ctx.getChild(3).getChildCount()) / 2) + 1;
			params = new SymbolTable(current);
			funcName = getFuncName(ctx.getChild(3), ctx.getChild(1).getText(), ctx.getChild(3).getChildCount());
		} else {
			funcStat = ctx.getChild(5);
			params = null;
			funcName = ctx.getChild(1).getText();
		}
		FunctionType t = new FunctionType(getType(ctx
				.getChild(0).getChild(0)), numParams,	params);
		
		if (current.existsIdent(ctx.getChild(1).getText())) {
			System.out.println("Function defined before!");
		}
		// add in the function name and its return type

		current.add(funcName, t);
		if (ctx.getChild(3) instanceof WaccParser.ParamListContext) {
			// add in the parameters, if no parameters, do nothing
			addParamToList(ctx.getChild(3), t);
			current = params;
		}
		if (checkReturnTypeOfFunc(ctx.getChild(0), funcStat)) {
			if (ctx.getChild(3) instanceof WaccParser.ParamListContext) {
				current = (current.getPrevTable());
			}
		} else {
			System.out.println("Function Return Type Mismatch!");
		}

	}
	
	private String getFuncName(ParseTree params, String func, int childCount) {
		String funcName = func;
		for (int i = 0; i < childCount; i++) {
			
			if (params.getChild(i) instanceof WaccParser.ParamContext) {
				funcName += "_";
				ParseTree paramChild = params.getChild(i).getChild(0);
				Type funcType = getType(paramChild);
				funcName += funcType.getEnumType().toString().toLowerCase();
				while(paramChild.getChildCount() > 1){
					funcName += "arr";
					paramChild = paramChild.getChild(0);
				}
			}
		}
		//System.out.println(funcName);
		return funcName;
	}

	public boolean checkReturnTypeOfFunc(ParseTree child, ParseTree child2) {
		
		Type funcReturnType = getType(child);
		Type paramReturnType = null;

		if (child2.getChild(0) == null) {
			System.out.print("No Return Statement!");
		} else {
			if (child2.getChild(0) != null && matchKeyword(child2.getChild(0), "RETURN")) {
				paramReturnType = getType(child2.getChild(1));
			} else if (child2.getChild(2).getChild(0) != null
					&& matchKeyword(child2.getChild(2).getChild(0), "RETURN")) {
				isStatValid(child2.getChild(0));
				paramReturnType = getType(child2.getChild(2).getChild(1));
			} else {
				System.out.print("No Return Statement!");
			}
		}
		//System.out.println(paramReturnType);
		return funcReturnType.equals(paramReturnType);
	}

	public void addParamToList(ParseTree params, FunctionType t) {
		int childCount = params.getChildCount();
		for (int i = 0; i < childCount; i++) {
			if (params.getChild(i) instanceof WaccParser.ParamContext) {
				t.putParam(params.getChild(i).getChild(1).getText(), getTypeOfType(params.getChild(i).getChild(0)));
			}
		}
	}
	/*
	public boolean checkFuncBody(){
		boolean result = true;
		for (ParseTree p : funcBody.keySet()){
			current = funcBody.get(p);
			result &= isStatValid(p);
			current = current.getPrevTable();
		}
		return result;
	}
	*/
	public SymbolTable getSymbolTable(){
		return current;
	}

	public boolean isStatValid(ParseTree tree) {
		// SKIP
		if (tree instanceof WaccParser.Stat_SKIPContext)
			return true;

		/*
		 * Assignment statements: Target type: program variable, array element, pair
		 * element expression, array literal, function call, pair constructor, pair
		 * element
		 */
		WaccParser.StatContext stat = (WaccParser.StatContext)tree;
		// type ident EQUALS assignRhs
		if (tree instanceof WaccParser.Stat_type_ident_equals_assignRHSContext) {
			if (current.existsIdent(tree.getChild(1).getText())){
				return errorMessage(tree," Double Declaration of Indentifiers!", false);
			} else {
				current.add(tree.getChild(1).getText(), getTypeOfType(tree.getChild(0)));
			}
			
			if (getTypeOfType(tree.getChild(0)) instanceof PairType
					&& matchType(tree.getChild(3), NULL)) {
				return true;
			}
			

			return errorMessage(tree,
								" Type Mismatch \nExpected type is " +
								getType(tree.getChild(1)) +
								" but actual type is " +
								getType(tree.getChild(3)) + "\nError at line: " + stat.start.getLine(),
								matchType(tree.getChild(1), tree.getChild(3)));
		}

		
		
		if(tree instanceof WaccParser.Stat_assignLHS_unaryAssignOpContext){
			return errorMessage(tree,
					"Type Mismatch \nExpected type is INT, but actual type is " +
					getType(tree.getChild(0)) + "\nError at line: " + stat.start.getLine(),
					matchType(tree.getChild(0), new SingleType(EnumType.INT)));
		}
		
		// assignLhs assignOp assignRhs
		if (tree instanceof WaccParser.Stat_assignLHS_assignOp_assignRHSContext) {
			return errorMessage(tree,
								"Type Mismatch \nExpected type is " +
								getType(tree.getChild(0)) +
								" but actual type is " +
								getType(tree.getChild(2)) + "\nError at line: " + stat.start.getLine(),
								matchType(tree.getChild(0), tree.getChild(2)));
		}
		
		/*
		 * Read statements: Value can only be a program variable, an array element or
		 * a pair element
		 */
		// READ assignLhs
		if (tree instanceof WaccParser.Stat_Read_assignLHSContext){
			return errorMessage(tree, " Type Mismatch" + "\nError at line: " + stat.start.getLine(), !matchType(tree.getChild(1), ERROR));
		}
			

		/*
		 * Memory free statements: Type must be 'pair'
		 */
		// FREE expr
		if (tree instanceof WaccParser.Stat_Free_exprContext) {
			//System.out.println("Error at line: " + stat.start.getLine());
			return errorMessage(tree,
								" Expected type is PAIR but actual type is " +
								getType(tree.getChild(1))+ "\nError at line: " + stat.start.getLine(),
								getType(tree.getChild(1)) instanceof PairType);
		}
		
		// RETURN expr
		if (tree instanceof WaccParser.Stat_Return_exprContext){
			if (tree.getParent() instanceof WaccParser.ProgramContext){
				System.out.print("return cannot be called at current scope");
				return false;
			}
			return errorMessage(tree, "Type mismatch"+ "\nError at line: " + stat.start.getLine(), !matchType(tree.getChild(1), ERROR));
		}
		
		// EXIT expr
		if (tree instanceof WaccParser.Stat_Exit_exprContext)
			return !matchType(tree.getChild(1), ERROR);

		// PRINT expr
		if (tree instanceof WaccParser.Stat_Print_exprContext)
			return errorMessage(tree,
					"Type mismatch" + "\nError at line: " + stat.start.getLine(),
					!matchType(tree.getChild(1), ERROR));
	
		// PRINTLN expr
		if (tree instanceof WaccParser.Stat_Println_exprContext)
			return errorMessage(tree,
					"Type mismatch" + "\nError at line: " + stat.start.getLine(),
					!matchType(tree.getChild(1), ERROR));
		
		// IF expr THEN stat ELSE stat FI
		if (tree instanceof WaccParser.Stat_if_expr_then_stat_else_stat_fiContext) {
			
			if (!matchType(tree.getChild(1), BOOL))// expr must be of type 'bool'
				return errorMessage(tree,
						"Type mismatch. " + 
				tree.getChild(1).getText() +
				" has actual type " +
				getTypeOfExpr(tree.getChild(1)) +
				" but expected type is BOOL" + "\nError at line: " + stat.start.getLine(), false);
			SymbolTable other = new SymbolTable(current);		// then-branch, making a new scope for checking the then branch
			current = other;
			if (!isStatValid(tree.getChild(3)))
				return false;
			current = current.getPrevTable();					// coming out from the then-branch-scope
			
			other = new SymbolTable(current);					// else-branch, making a new scope for checking the then branch
			current = other;
			if (!isStatValid(tree.getChild(5)))
				return false;
			current = current.getPrevTable();					// coming out from the else-branch-scope
			return true;
			
		}

		// WHILE expr DO stat DONE
		if (tree instanceof WaccParser.Stat_while_expr_do_stat_doneContext) {
			if (!matchType(tree.getChild(1), BOOL))	// expr must be of type 'bool'
				return errorMessage(tree,
						"Type mismatch. " + 
				tree.getChild(1).getText() +
				" has actual type " +
				getTypeOfExpr(tree.getChild(1)) +
				" but expected type is BOOL" + "\nError at line: " + stat.start.getLine(), false);
			SymbolTable other = new SymbolTable(current);	// executing in a new scope
			current = other;
			if (!isStatValid(tree.getChild(3)))
				return false;
			current = current.getPrevTable();				// leaving the scope
			return true;
		}
		
		// if stat expr stat fdo stat fdone
		if (tree instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext) {
			SymbolTable other = new SymbolTable(current);	// executing in a new scope
			current = other;
			if (!isStatValid(tree.getChild(2)) || !isStatValid(tree.getChild(6)))
				return false;
			if (!matchType(tree.getChild(4), BOOL))	// expr must be of type 'bool'
				return errorMessage(tree,
						"Type mismatch. " + 
				tree.getChild(4).getText() +
				" has actual type " +
				getTypeOfExpr(tree.getChild(4)) +
				" but expected type is BOOL" + "\nError at line: " + stat.start.getLine(), false);
			current = current.getPrevTable();				// leaving the scope
			return true;
		}

		// BEGIN stat END
		if (tree instanceof WaccParser.Stat_begin_stat_endContext) {
			
			SymbolTable other = new SymbolTable(current);	// entering a new scope
			current = other;
			if (!isStatValid(tree.getChild(1)))
				return false;
			current = current.getPrevTable();				// exiting the scope
			return true;
		}

		// stat SEMICOLON stat
		if (tree instanceof WaccParser.Stat_semicolon_statContext) {
			if (!isStatValid(tree.getChild(0)))
				return false;
			if (!isStatValid(tree.getChild(2)))
				return false;
			return true;
		}
		
		// Do while loop
		if (tree instanceof WaccParser.Stat_ddo_stat_dwhile_expr_ddoneContext){
			if (!isStatValid(tree.getChild(1)))
				return false;
			return errorMessage(tree.getChild(3),
													"Type mismatch. " + tree.getChild(3).getText() + "has actual type " +
															getTypeOfExpr(tree.getChild(3)) + " but expected type is BOOL" + 
															"\nError at line: " + stat.start.getLine(),
															matchType(tree.getChild(3), BOOL));
		}
		
		// for do loop
		if (tree instanceof WaccParser.Stat_for_stat_expr_stat_fdo_stat_fdoneContext){
			if (!isStatValid(tree.getChild(2)) ||
					!isStatValid(tree.getChild(6)) ||
					!isStatValid(tree.getChild(9)))
				return false;
			return errorMessage(tree.getChild(4),
					"Type mismatch. " + tree.getChild(4).getText() + "has actual type " +
							getTypeOfExpr(tree.getChild(4)) + " but expected type is BOOL" + 
							"\nError at line: " + stat.start.getLine(),
							matchType(tree.getChild(4), BOOL));
		}
		
		// side-effecting expressions
		if (tree instanceof WaccParser.Stat_assignLHS_unaryAssignOpContext){
			return errorMessage(tree.getChild(0),
													"Type mismatch. " + tree.getChild(4).getText() + "has actual type " +
													getTypeOfExpr(tree.getChild(4)) + " but expected type is BOOL" + 
													"\nError at line: " + stat.start.getLine(),
													matchType(tree.getChild(0), INT));
		}

		// ELSE
		return false;
	}
	
	public Type getType(ParseTree tree) {
		
		// assignLhs
		if (tree instanceof WaccParser.AssignLhsContext)
			return getTypeOfAssignLhs(tree);
		// assignRhs
		if (tree instanceof WaccParser.AssignRhsContext)
			return getTypeOfAssignRhs(tree);
		// pairElem
		if (tree instanceof WaccParser.PairElemContext)
			return getTypeOfPairElem(tree);
		// type
		if (tree instanceof WaccParser.TypeContext)
			return getTypeOfType(tree);
		// baseType
		if (tree instanceof WaccParser.BaseTypeContext)
			return getTypeOfBaseType(tree);
		// pairType
		if (tree instanceof WaccParser.PairTypeContext)
			return getTypeOfPairTypeType(tree);
		// expr
		if (tree instanceof WaccParser.ExprContext)
			return getTypeOfExpr(tree);
		// ident
		if (tree instanceof WaccParser.IdentContext)
			return getTypeOfIdent(tree);

		return ERROR;
	}

	public Type getTypeOfType(ParseTree tree) {

		// baseType
		if (tree.getChild(0) instanceof WaccParser.BaseTypeContext)
			return getTypeOfBaseType(tree.getChild(0));

		// pairType
		if (tree.getChild(0) instanceof WaccParser.PairTypeContext)
			return getTypeOfPairTypeType(tree.getChild(0));

		// type OPEN_BRACKETS CLOSE_BRACKETS
		if (tree.getChild(0) instanceof WaccParser.TypeContext) {
			return new ArrayType(getTypeOfType(tree.getChild(0)).getEnumType());
		}

		// ELSE
		return ERROR;
	}
	
	public Type getTypeOfAssignLhs(ParseTree tree) {

		// ident
		if (tree.getChild(0) instanceof WaccParser.IdentContext)
			return getTypeOfIdent(tree.getChild(0));

		// expr
		if (tree.getChild(0) instanceof WaccParser.ExprContext)
			return getTypeOfExpr(tree.getChild(0));

		// pairElem
		if (tree.getChild(0) instanceof WaccParser.PairElemContext) {
			if (tree.getChild(0) instanceof WaccParser.PairElem_SND_exprContext) {
				if (current.getType(tree.getChild(0).getChild(1).getText()) instanceof PairType) {
					PairType temp = (PairType) current.getType(tree.getChild(0).getChild(1)
							.getText());
					return (Type) temp.getSecond();
				}
			}
			if (tree.getChild(0) instanceof WaccParser.PairElem_FST_exprContext) {
				if (current.getType(tree.getChild(0).getChild(1).getText()) instanceof PairType) {
					PairType temp = (PairType) current.getType(tree.getChild(0).getChild(1)
							.getText());
					return (Type) temp.getFirst();
				}
			}
			return getTypeOfPairElem(tree.getChild(0));
		}
		// ELSE
		return ERROR;
	}

	public Type getTypeOfAssignRhs(ParseTree tree) {

		// expr
		if (tree.getChild(0) instanceof WaccParser.ExprContext)
			return getTypeOfExpr(tree.getChild(0));

		// arrayLiter
		if (tree.getChild(0) instanceof WaccParser.ArrayLiterContext)
			return new ArrayType(getTypeOfArrayLiter(tree.getChild(0)));

		// NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
		if (tree instanceof WaccParser.AssignRHS_newPairContext) {
			return new PairType(getTypeOfExpr(tree.getChild(2)), getTypeOfExpr(tree.getChild(4)));
		}

		// pairElem
		if (tree.getChild(0) instanceof WaccParser.PairElemContext) {
			if (matchKeyword(tree.getChild(0).getChild(0), "SND")) {
				if (current.getType(tree.getChild(0).getChild(1).getText()) instanceof PairType) {
					PairType temp = (PairType) current.getType(tree.getChild(0).getChild(1).getText());
					return (Type) temp.getSecond();
				}
			}
			if (tree.getChild(0).getChild(0).getText().equals("fst")) {
				if (current.getType(tree.getChild(0).getChild(1).getText()) instanceof PairType) {
					PairType temp = (PairType) current.getType(tree.getChild(0).getChild(1).getText());
					return (Type) temp.getFirst();
				}
			}
			return getTypeOfPairElem(tree.getChild(0));
		}

		// CALL ident OPEN_PARENTHESES (argList)? CLOSE_PARENTHESES
		if (matchKeyword(tree.getChild(0), "CALL")) {
			int numArgs = 0;
			String funcName = tree.getChild(1).getText();
			if (tree.getChild(3) instanceof WaccParser.ArgListContext){
				numArgs = tree.getChild(3).getChildCount();
				
				for(int i = 0; i < numArgs; i+=2){
					ParseTree paramType = tree.getChild(3).getChild(i);
					funcName += "_";
					funcName += getType(paramType).getEnumType().toString().toLowerCase();
					while (getType(paramType.getChild(0)) instanceof ArrayType){
						funcName += "arr";
						paramType = paramType.getChild(0);
					}
				}
			}
			//System.out.println(funcName);
			if (current.getType(funcName) instanceof FunctionType) {
				FunctionType ft = (FunctionType) current.getType(funcName);
				
				if (ft.getNumParams() != 0 && (numArgs / 2 + 1) != ft.getNumParams()) {
					System.out.print("Number of Parameters mismatch!");
				} else {
					int paramCounter = 0;
					Type[] params = ft.getParams();
					// check the types of parameters
					for (int i = 0; i < numArgs; i++) {
						if (tree.getChild(3).getChild(i) instanceof WaccParser.ExprContext) {
							if (!matchType(tree.getChild(3).getChild(i), params[paramCounter])) {
								System.out.print("Parameter type mimsmatch!");
							}
							paramCounter++;
						}
					}
				}
				return ft.getReturnType();
			} else {
				System.out.print("Call Function type mismatch!");
				return ERROR;
			}

		}
		// ELSE
		return ERROR;
	}

	public Type getTypeOfExpr(ParseTree tree) {
		// intLiter
		if (tree instanceof WaccParser.Expr_intContext)
			return INT;
		
		// boolLiter
		if (tree instanceof WaccParser.Expr_boolContext)
			return BOOL;
		
		//TODO: ADDED THIS
		if (tree instanceof WaccParser.ArrayLiterContext){
		 return new ArrayType(getTypeOfExpr(tree.getChild(1)).getEnumType());
		}

		// charLiter
		if (tree instanceof WaccParser.Expr_charContext)
			return CHAR;

		// stringLiter
		if (tree instanceof WaccParser.Expr_strContext)
			return STRING;

		// pairLiter
		if (tree instanceof WaccParser.Expr_pairlitContext)
			return NULL;

		// ident
		if (tree.getChild(0) instanceof WaccParser.IdentContext)
			return getTypeOfIdent(tree.getChild(0));

		// expr OPEN_BRACKETS expr CLOSE_BRACKETS
		if (matchKeyword(tree.getChild(1), "[")) {
			Type type = getTypeOfExpr(tree.getChild(0));
			if (type.getEnumType().equals(EnumType.STRING))
				return CHAR;
			return new SingleType(getTypeOfExpr(tree.getChild(0)).getEnumType());
		}

		// unaryOper expr
		if (tree instanceof WaccParser.Expr_unaryContext) {
			Type type = getTypeOfExpr(tree.getChild(1));

			// "!"
			if (tree.getChild(0) instanceof WaccParser.Unary_excContext)
				if (type.equals(BOOL)) return type;

			// "-"
			if (tree.getChild(0) instanceof WaccParser.Unary_minusContext)
				if (type.equals(INT)) return type;

			// "len"
			if (tree.getChild(0) instanceof WaccParser.Unary_lenContext)
				if (type.equals(STRING)) return type;

			// "ord"
			if (tree.getChild(0) instanceof WaccParser.Unary_ordContext)
				if (type.equals(INT)) return CHAR;

			// "toInt"
			if (tree.getChild(0) instanceof WaccParser.Unary_intContext)
				if (type.equals(CHAR)) return INT;

			// ELSE
			return ERROR;
		}

		// OPEN_PARENTHESES expr CLOSE_PARENTHESES
		if (tree instanceof WaccParser.Expr_exprContext)
			return getTypeOfExpr(tree.getChild(1));

		// expr binaryOper expr		
		Type type1 = getTypeOfExpr(tree.getChild(0));
		Type type2 = getTypeOfExpr(tree.getChild(2));
		
		if (tree instanceof WaccParser.Expr_PLUSContext ||
				tree instanceof WaccParser.Expr_MINUSContext ||
				tree instanceof WaccParser.Expr_MULTContext ||
				tree instanceof WaccParser.Expr_DIVContext ||
				tree instanceof WaccParser.Expr_MODContext ){
			if (type1.equals(type2) && type1.equals(INT))
				return INT;
		}
		
		if (tree instanceof WaccParser.Expr_ANDContext ||
				tree instanceof WaccParser.Expr_ORContext){
			if (type1.equals(type2) && type2.equals(BOOL))
				return BOOL;
		}
		
		if (tree instanceof WaccParser.Expr_EQContext||
				tree instanceof WaccParser.Expr_NEQContext){
			if (type1.equals(type2))
				if (type1.equals(INT) || type1.equals(BOOL) || type1.equals(STRING) || type1.equals(CHAR))
					return BOOL;
			if (type1 instanceof PairType && type2.equals(NULL))
				return BOOL;
		}
		
		if (tree instanceof WaccParser.Expr_LTContext ||
				tree instanceof WaccParser.Expr_LTEQContext ||
				tree instanceof WaccParser.Expr_GTContext ||
				tree instanceof WaccParser.Expr_GTEQContext){
			if (type1.equals(type2) && (type1.equals(CHAR) || type1.equals(INT)))
				return BOOL;
		}	
		// ELSE
		return ERROR;
	}

	public Type getTypeOfBaseType(ParseTree tree) {

		if (tree instanceof WaccParser.Base_INTContext)
			return new SingleType(EnumType.INT);
		if (tree instanceof WaccParser.Base_BOOLContext)
			return new SingleType(EnumType.BOOL);
		if (tree instanceof WaccParser.Base_CHARContext)
			return new SingleType(EnumType.CHAR);
		if (tree instanceof WaccParser.Base_STRContext)
			return new SingleType(EnumType.STRING);

		// ELSE
		return ERROR;
	}

	public Type getTypeOfPairTypeType(ParseTree tree) {
		// PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType
		// CLOSE_PARENTHESES;
		if (tree instanceof WaccParser.PairTypeContext)
			return new PairType(getTypeOfPairElem(tree.getChild(2)),getTypeOfPairElem(tree.getChild(4)));

		return ERROR;
	}

	public Type getTypeOfArrayLiter(ParseTree tree) {
		// OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS ;
		boolean match = true;
		int i = 1;

		//System.out.println(tree.getText());
		
		if (matchKeyword(tree.getChild(1), "]"))
			return new SingleType(EnumType.EMPTYARRAY);

		Type type = getTypeOfExpr(tree.getChild(1));

		if (matchKeyword(tree.getChild(2), "]"))
			return new ArrayType(type);

		do {
			i += 2;
			match &= matchType(type, getTypeOfExpr(tree.getChild(i)));
		} while (match && !matchKeyword(tree.getChild(i + 1), "]"));

		if (match)
			return new ArrayType(type);
		else
			return ERROR;

	}

	public Type getTypeOfPairElem(ParseTree tree) {

		// baseType
		if (tree instanceof WaccParser.Pair_base_tContext)
			return getTypeOfBaseType(tree.getChild(0));

		// type
		if (tree instanceof WaccParser.Pair_typeContext)
			return getTypeOfType(tree.getChild(0));

		// PAIR
		if (tree instanceof WaccParser.Pair_PAIRContext)
			return new SingleType(EnumType.PAIR);

		// ELSE
		return ERROR;
	}

	public Type getTypeOfIdent(ParseTree tree) {
		return current.getType(tree.getText());
	}

	public boolean matchKeyword(ParseTree tree, String str) {
		return (tree.getText().toUpperCase().equals(str));
	}

	public boolean matchType(Type fst, Type snd) {
		return fst.equals(snd);
	}
	
	public boolean matchType(ParseTree tree, Type type){
		return matchType(getType(tree), type);
	}

	public boolean matchType(ParseTree tree1, ParseTree tree2) {
		return getType(tree1).equals(getType(tree2));
	}
	
	public boolean matchType(Type type, ParseTree tree){
		return matchType(tree, type);
	}

	private boolean errorMessage(ParseTree tree, String string, Boolean bool){
		if (!bool)
			System.out.println(tree.getText() + " is not valid: " + string);
		return bool;
	}
	
	
}
