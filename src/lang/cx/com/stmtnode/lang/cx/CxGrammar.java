package com.stmtnode.lang.cx;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareNode;
import com.stmtnode.lang.cx.head.FunctionNode;
import com.stmtnode.lang.cx.head.HeadNode;
import com.stmtnode.lang.cx.head.IncludeLibraryNode;
import com.stmtnode.lang.cx.head.IncludeSourceNode;
import com.stmtnode.lang.cx.head.PathNode;
import com.stmtnode.lang.cx.head.UnitNode;
import com.stmtnode.lang.cx.stmt.BlockNode;
import com.stmtnode.lang.cx.stmt.DeclareArrayNode;
import com.stmtnode.lang.cx.stmt.DeclareValueNode;
import com.stmtnode.lang.cx.stmt.ExpNode;
import com.stmtnode.lang.cx.stmt.StmtNode;
import com.stmtnode.lang.cx.type.CharTypeNode;
import com.stmtnode.lang.cx.type.IntTypeNode;
import com.stmtnode.lang.cx.type.PointerTypeNode;
import com.stmtnode.lang.cx.type.StructTypeNode;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.AddressNode;
import com.stmtnode.lang.cx.value.AssignNode;
import com.stmtnode.lang.cx.value.CallNode;
import com.stmtnode.lang.cx.value.CastNode;
import com.stmtnode.lang.cx.value.GetNode;
import com.stmtnode.lang.cx.value.IdentifierNode;
import com.stmtnode.lang.cx.value.NotNode;
import com.stmtnode.lang.cx.value.NumberNode;
import com.stmtnode.lang.cx.value.SizeofNode;
import com.stmtnode.lang.cx.value.StringNode;
import com.stmtnode.lang.cx.value.ValueNode;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitNode parseUnit() throws SyntaxException {
		List<HeadNode> includes = new ArrayList<>();
		while (!eof() && can('#')) {
			includes.add(parsePrecompiler());
		}
		List<HeadNode> nodes = new ArrayList<>();
		while (!eof()) {
			nodes.add(parseUnitItem());
		}
		return new UnitNode(includes, nodes);
	}

	protected HeadNode parsePrecompiler() throws SyntaxException {
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

	protected HeadNode parsePreprocessorInclude() throws SyntaxException {
		read("include", "expected include");
		if (can('<')) {
			PathNode path = parsePath("expected include path");
			read('>', "expected close include");
			return new IncludeLibraryNode(path);
		} else if (isString()) {
			Token path = readString("expected include path");
			return new IncludeSourceNode(path);
		} else {
			throw error("expected include name");
		}
	}

	protected HeadNode parsePreprocessorDefine() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected HeadNode parsePreprocessorTypedef() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected PathNode parsePath(String message) throws SyntaxException {
		List<Token> tokens = new ArrayList<>();
		tokens.add(readIdentifier(message));
		while (is('/') || is('.') || isIdentifier()) {
			if (is('/')) {
				tokens.add(next());
			} else if (is('.')) {
				tokens.add(next());
			} else {
				tokens.add(readIdentifier(message));
			}
		}
		return new PathNode(tokens);
	}

	protected HeadNode parseUnitItem() throws SyntaxException {
		if (is("func")) {
			return parseFunction();
		} else if (is("let")) {
			return parseDeclareStatic();
		} else {
			throw error("expected unit item");
		}
	}

	protected HeadNode parseDeclareStatic() {
		// TODO Auto-generated method stub
		return null;
	}

	protected StmtNode parseDeclare() throws SyntaxException {
		Token token = read("let", "expected let keyword");
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of variable");
		if (can('[')) {
			ValueNode count = parseNumber();
			read(']', "expected close array count");
			return new DeclareArrayNode(token, type, name, count);
		}
		ValueNode value = null;
		if (can('=')) {
			value = parseValue();
		}
		return new DeclareValueNode(token, type, name, value);
	}

	protected FunctionNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of function");
		List<ArgumentDeclareNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseFunctionArgument);
		BlockNode block = parseBlock();
		return new FunctionNode(token, type, name, arguments, block);
	}

	protected ArgumentDeclareNode parseFunctionArgument() throws SyntaxException {
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of argument");
		return new ArgumentDeclareNode(name, type);
	}

	protected TypeNode parseType() throws SyntaxException {
		TypeNode type = parseTypeLiteral();
		while (can('*')) {
			type = new PointerTypeNode(type);
		}
		return type;
	}

	protected TypeNode parseTypeLiteral() throws SyntaxException {
		if (can("int")) {
			return new IntTypeNode();
		} else if (can("char")) {
			return new CharTypeNode();
		} else if (can("struct")) {
			Token name = readIdentifier("expected name of type");
			return new StructTypeNode(name);
		} else {
			throw error("expected type");
		}
	}

	protected BlockNode parseBlock() throws SyntaxException {
		return new BlockNode(readNodes('{', "expected open block", '}', "expected close block", this::parseCommand));
	}

	protected StmtNode parseCommand() throws SyntaxException {
		if (is("if")) {
			return parseIf();
		} else if (is("let")) {
			return parseDeclare();
		} else {
			return parseExpression();
		}
	}

	protected StmtNode parseIf() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected StmtNode parseExpression() throws SyntaxException {
		ExpNode node = new ExpNode(parseValue());
		return node;
	}

	protected ValueNode parseValue() throws SyntaxException {
		return parseTernary();
	}

	protected ValueNode parseTernary() throws SyntaxException {
		return parseOr();
	}

	protected ValueNode parseOr() throws SyntaxException {
		return parseAnd();
	}

	protected ValueNode parseAnd() throws SyntaxException {
		return parseCompare();
	}

	protected ValueNode parseCompare() throws SyntaxException {
		return parseSum();
	}

	protected ValueNode parseSum() throws SyntaxException {
		return parseMul();
	}

	protected ValueNode parseMul() throws SyntaxException {
		return parseUnary();
	}

	protected ValueNode parseUnary() throws SyntaxException {
		if (is('!')) {
			return parseNot();
		} else if (is('&')) {
			return parseAddress();
		}
		ValueNode left = parseLiteral();
		return left;
	}

	protected ValueNode parseNot() throws SyntaxException {
		Token token = read('!', "expected not keyword");
		ValueNode left = parseLiteral();
		return new NotNode(token, left);
	}

	protected ValueNode parseAddress() throws SyntaxException {
		Token token = read('&', "expected & symbol");
		ValueNode left = parseUnary();
		return new AddressNode(token, left);
	}

	protected ValueNode parseLiteral() throws SyntaxException {
		if (is('(')) {
			return parseExp();
		} else if (is("sizeof")) {
			return parseSizeof();
		} else if (isString()) {
			return parseString();
		} else if (isNumber()) {
			return parseNumber();
		} else if (isIdentifier()) {
			return parseId();
		} else {
			throw error("expected expression");
		}
	}

	protected ValueNode parseSizeof() throws SyntaxException {
		Token token = read("sizeof", "expected sizeof keyword");
		read('(', "expected open sizeof");
		TypeNode type = parseType();
		read(')', "expected open sizeof");
		return new SizeofNode(token, type);
	}

	protected ValueNode parseString() throws SyntaxException {
		return new StringNode(readString("expected string token"));
	}

	protected NumberNode parseNumber() throws SyntaxException {
		return new NumberNode(readNumber("expected number token"));
	}

	protected ValueNode parseExp() throws SyntaxException {
		read('(', "expected open expression");
		ValueNode value = parseValue();
		read(')', "expected close expression");
		return parseGetCall(value);
	}

	protected ValueNode parseId() throws SyntaxException {
		Token token = readIdentifier("expected identifier");
		ValueNode left = new IdentifierNode(token);
		return parseGetCall(left);
	}

	protected ValueNode parseGetCall(ValueNode left) throws SyntaxException {
		while (is('(') || is('.') || is('=')) {
			if (is('(')) {
				List<ValueNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseValue);
				left = new CallNode(left.token, left, arguments);
			} else if (can('.')) {
				if (can("cast")) {
					read('(', "expected open cast");
					TypeNode type = parseType();
					read(')', "expected open cast");
					left = new CastNode(left.token, left, type);
				} else {
					Token name = readIdentifier("expected name of field or function");
					left = new GetNode(name, left, name);
				}
			} else if (can('=')) {
				ValueNode value = parseValue();
				left = new AssignNode(left.token, left, value);
			}
		}
		return left;
	}

}
