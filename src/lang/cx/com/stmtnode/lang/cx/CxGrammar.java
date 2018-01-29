package com.stmtnode.lang.cx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareCxCodeNode;
import com.stmtnode.lang.cx.head.FunctionCxCodeNode;
import com.stmtnode.lang.cx.stmt.BlockCxCodeNode;
import com.stmtnode.lang.cx.stmt.StmtCxCodeNode;
import com.stmtnode.lang.cx.type.IntTypeCxCodeNode;
import com.stmtnode.lang.cx.type.TypeCxCodeNode;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitCxCodeNode parseUnit() throws ParseException {
		List<UnitCxCodeNode> nodes = new ArrayList<>();
		while (!eof()) {
			if (can('#')) {
				nodes.add(parsePrecompiler());
			} else {
				nodes.add(parseUnitItem());
			}
		}
		return new UnitCxCodeNode(nodes);
	}

	protected UnitCxCodeNode parsePrecompiler() throws ParseException {
		if (is("include")) {
			return parsePreprocessorInclude();
		} else if (is("define")) {
			return parsePreprocessorDefine();
		} else if (is("typedef")) {
			return parsePreprocessorTypedef();
		} else {
			throw error("expected pre-processor");
		}
	}

	protected UnitCxCodeNode parsePreprocessorInclude() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorDefine() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorTypedef() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parseUnitItem() throws SyntaxException {
		if (is("func")) {
			return parseFunction();
		} else if (is("let")) {
			return parseDeclare();
		} else {
			throw error("expected unit item");
		}
	}

	private UnitCxCodeNode parseDeclare() {
		// TODO Auto-generated method stub
		return null;
	}

	private FunctionCxCodeNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		TypeCxCodeNode type = parseType();
		Token name = readIdentifier("expected name of function");
		List<ArgumentDeclareCxCodeNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseFunctionArgument);
		BlockCxCodeNode block = parseBlock();
		return new FunctionCxCodeNode(token, type, name, arguments, block);
	}

	private ArgumentDeclareCxCodeNode parseFunctionArgument() throws SyntaxException {
		TypeCxCodeNode type = parseType();
		Token name = readIdentifier("expected name of argument");
		return new ArgumentDeclareCxCodeNode(name, type);
	}

	private TypeCxCodeNode parseType() throws SyntaxException {
		if (can("int")) {
			return new IntTypeCxCodeNode();
		} else {
			throw error("expected type");
		}
	}

	private BlockCxCodeNode parseBlock() throws SyntaxException {
		return new BlockCxCodeNode(readNodes('{', "expected open block", '}', "expected close block", this::parseCommand));
	}

	private StmtCxCodeNode parseCommand() {
		return null;
	}

}
