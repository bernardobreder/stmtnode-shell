package com.stmtnode.lang.cx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareCxCodeNode;
import com.stmtnode.lang.cx.head.FunctionCxCodeNode;
import com.stmtnode.lang.cx.stmt.BlockCxCodeNode;
import com.stmtnode.lang.cx.stmt.ExpCxCodeNode;
import com.stmtnode.lang.cx.stmt.StmtCxCodeNode;
import com.stmtnode.lang.cx.type.IntTypeCxCodeNode;
import com.stmtnode.lang.cx.type.TypeCxCodeNode;
import com.stmtnode.lang.cx.value.CallCxCodeNode;
import com.stmtnode.lang.cx.value.IdentifierCxCodeNode;
import com.stmtnode.lang.cx.value.StringCxCodeNode;
import com.stmtnode.lang.cx.value.ValueCxCodeNode;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitCxCodeNode parseUnit() throws ParseException {
		List<CxCodeNode> nodes = new ArrayList<>();
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

	protected UnitCxCodeNode parsePreprocessorInclude() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorDefine() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorTypedef() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected CxCodeNode parseUnitItem() throws SyntaxException {
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

	private StmtCxCodeNode parseCommand() throws SyntaxException {
		if (is("if")) {
			return parseIf();
		} else {
			return parseExpression();
		}
	}

	private StmtCxCodeNode parseIf() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	private StmtCxCodeNode parseExpression() throws SyntaxException {
		ExpCxCodeNode node = new ExpCxCodeNode(parseValue());
		read(';', "expected ';' symbol");
		return node;
	}

	private ValueCxCodeNode parseValue() throws SyntaxException {
		return parseTernary();
	}

	private ValueCxCodeNode parseTernary() throws SyntaxException {
		return parseOr();
	}

	private ValueCxCodeNode parseOr() throws SyntaxException {
		return parseAnd();
	}

	private ValueCxCodeNode parseAnd() throws SyntaxException {
		return parseCompare();
	}

	private ValueCxCodeNode parseCompare() throws SyntaxException {
		return parseSum();
	}

	private ValueCxCodeNode parseSum() throws SyntaxException {
		return parseMul();
	}

	private ValueCxCodeNode parseMul() throws SyntaxException {
		return parseUnary();
	}

	private ValueCxCodeNode parseUnary() throws SyntaxException {
		return parseLiteral();
	}

	private ValueCxCodeNode parseLiteral() throws SyntaxException {
		if (is('(')) {
			return parseExp();
		} else if (isString()) {
			return parseString();
		} else if (isIdentifier()) {
			return parseId();
		} else {
			throw error("expected expression");
		}
	}

	private ValueCxCodeNode parseString() throws SyntaxException {
		return new StringCxCodeNode(readString("expected string token"));
	}

	private ValueCxCodeNode parseExp() throws SyntaxException {
		read('(', "expected open expression");
		ValueCxCodeNode value = parseValue();
		read(')', "expected close expression");
		return value;
	}

	private ValueCxCodeNode parseId() throws SyntaxException {
		Token token = readIdentifier("expected identifier");
		ValueCxCodeNode left = new IdentifierCxCodeNode(token);
		while (is('(')) {
			List<ValueCxCodeNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseValue);
			left = new CallCxCodeNode(left, arguments);
		}
		return left;
	}

}
