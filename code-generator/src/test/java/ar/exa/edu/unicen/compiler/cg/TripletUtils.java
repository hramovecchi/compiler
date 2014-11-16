package ar.exa.edu.unicen.compiler.cg;

import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import static ar.exa.edu.unicen.compiler.lexical.analyzer.Token.*;
import static ar.exa.edu.unicen.compiler.syntactic.Branch.*;

public class TripletUtils {

	private static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();
	private static final String auxVar16BitsFormat = "@aux";
	private static final String breakLine = "\n";
	private static final String tab = "\t";

	public static String getCodeForTriplet(Triplet triplet, int position) {
		// TODO Map the triplet with the string that represents it
		String code = "";
		String operator = triplet.getOperator().toString();
		switch (operator) {

		case "ADD": {
			code += tab + "MOV ax," + translateOperand(triplet.getOperand1())
					+ breakLine;
			code += tab + "ADD ax," + translateOperand(triplet.getOperand2())
					+ breakLine;
			code += tab + "MOV " + auxVar16BitsFormat + position + ",ax"
					+ breakLine;
			break;
		}
		case "SUB": {
			code += tab + "MOV ax," + translateOperand(triplet.getOperand1())
					+ breakLine;
			code += tab + "SUB ax," + translateOperand(triplet.getOperand2())
					+ breakLine;
			code += tab + "MOV " + auxVar16BitsFormat + position + ",ax"
					+ breakLine;
			break;
		}
		case "MUL": {
			code += tab + "MOV ax," + translateOperand(triplet.getOperand1())
					+ breakLine;
			code += tab + "MUL ax," + translateOperand(triplet.getOperand2())
					+ breakLine;
			code += tab + "MOV " + auxVar16BitsFormat + position + ",ax"
					+ breakLine;
			break;
		}
		case "DIV": {
			code += tab + "MOV ax," + translateOperand(triplet.getOperand1())
					+ breakLine;
			code += tab + "MOV edx," + "0" + breakLine;
			code += tab + "MOV bx," + translateOperand(triplet.getOperand2())
					+ breakLine;
			code += tab + "IDIV bx" + breakLine;
			code += tab + "MOV " + auxVar16BitsFormat + position + ",ax"
					+ breakLine;
			break;
		}
		case "ASSIGN": {
			// TODO need to check the type of the operand to create the variable
			code += tab + "MOV ax," + translateOperand(triplet.getOperand2())
					+ breakLine;
			code += tab + "MOV " + translateOperand(triplet.getOperand1())
					+ ",ax" + breakLine;
			break;
		}

		case "EQ": // "="
		case "NE": // "^="
		case "LT": // "<"
		case "GT": // ">"
		case "LE": // "<="
		case "GE": { // ">="
			code += tab + "MOV ax," + translateOperand(triplet.getOperand1())
					+ breakLine;
			code += tab + "CMP ax," + translateOperand(triplet.getOperand2())
					+ breakLine;
			break;
		}
		case "BF": {
			break;
		}
		case "BI": {
			break;
		}
		}
		return "";
	}

	private static String translateOperand(Object operand) {
		if (operand instanceof Integer) {
			// TODO need to diference between 16 bits variable or 32 bits
			// variable
			return auxVar16BitsFormat + operand;
		} else if (SYMBOL_TABLE.isVariableInteger(operand.toString())) {// isnumeric
			return "_" + operand;
		} else if (SYMBOL_TABLE.isVariableDouble(operand.toString())) {// isnumeric
			return "_" + operand;
		} else if (SYMBOL_TABLE.isConstInteger(operand.toString())) {// isnumeric
			return "_" + operand;
		} else if (SYMBOL_TABLE.isConstDouble(operand.toString())) {// isnumeric
			return "_" + operand;
		}
		// String constant used to print messages by console.
		return operand.toString();
	}

	// private static String jumpCondition(String condition){
	// if ("EQ".equals(condition)){
	// je
	// } else if ("NE".equals(condition)){
	// jne
	// } else if ("LT".equals(condition)){
	// jl
	// } else if ("GT".equals(condition)){
	// jg
	// } else if ("LE".equals(condition)){
	// jle
	// } else if ("GE".equals(condition)){
	// jge
	// }
	// return "";
	// }

}
